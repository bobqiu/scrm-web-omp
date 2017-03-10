package com.youanmi.scrm.omp.service.common;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.youanmi.commons.base.exception.ViewExternalDisplayException;
import com.youanmi.commons.redis.RedisClient;
import com.youanmi.scrm.commons.constants.result.ResultCode;
import com.youanmi.scrm.commons.sms.conf.SmsConfBuilder;
import com.youanmi.scrm.commons.sms.constants.SmsConstants;
import com.youanmi.scrm.commons.sms.sender.SmsSender;
import com.youanmi.scrm.commons.sms.vo.request.SmsInfoVO;
import com.youanmi.scrm.commons.util.code.CodeUtils;
import com.youanmi.scrm.commons.util.string.AssertUtils;
import com.youanmi.scrm.omp.constants.OmpConstants;


@Service("ompSmsService")
public class OmpSmsService {
	private static Logger LOG = LoggerFactory.getLogger(OmpSmsService.class);
	
    @Resource
    private RedisClient<?> redisClient;
    
    /**
     * 发送验证码
     * @param phone
     * @param template
     */
    public void sendVerifyCode(String phone, String template) {
        SmsInfoVO infoVO = new SmsInfoVO();
        infoVO.setMobile(phone);
        infoVO.setContent(String.format(template, getVerifyCode(phone)));
        //发送短信
        try {
            SmsSender.sendSms(infoVO);
        } catch (Exception e) {
        	LOG.info(e.getMessage(), e);
            throw new ViewExternalDisplayException(ResultCode.User.GET_CODE_FAIL);
        }
    }
    
    /**
     * 获取验证码
     * @param phone 手机号
     */
    private String getVerifyCode(String phone) {
        String code = redisClient.hget(OmpConstants.Redis.Sms.CODE_HASH, phone);
        String newCode = CodeUtils.getVerifyCode(SmsConstants.Common.CODE_LENGTH);
        if (AssertUtils.isNull(code)) {
            //记录最新验证码
            redisClient.hset(OmpConstants.Redis.Sms.CODE_HASH, phone, newCode);
            //记录验证码生成时间
            redisClient.hset(OmpConstants.Redis.Sms.CODE_TIME, phone + ":" + newCode,
                    String.valueOf(System.currentTimeMillis()));
            return newCode;
        } else {
            String sendTime = redisClient.hget(OmpConstants.Redis.Sms.CODE_TIME, phone + ":" + code);
            //如果超过了有效期,生成新的验证码
            if (AssertUtils.isNull(sendTime) ||
                    System.currentTimeMillis() > (Long.valueOf(sendTime) + SmsConstants.Common.EFFECTIVE_TIME)) {
                redisClient.hset(OmpConstants.Redis.Sms.CODE_HASH, phone, newCode);
                //记录验证码生成时间
                redisClient.hset(OmpConstants.Redis.Sms.CODE_TIME, phone + ":" + newCode,
                        String.valueOf(System.currentTimeMillis()));
                return newCode;
            } else {
                return code;
            }
        }
    }
    
    /**
     * 用于校验验证码是否正确
     * @param phone
     * @param verifyCode
     * @return
     */
    public Boolean isVerifyCodeTrue(String phone, String verifyCode) {
        //获取短信开关
        String sendCode = SmsConfBuilder.getProperties(SmsConstants.Common.AUTH_CODE_TYPE);
        //模拟发短信情况,则与默认验证码比较
        if (!SmsConstants.Common.AUTH_CODE_TYPE_1.equals(sendCode)) {
            return SmsConstants.Common.FAKE_CODE.equals(verifyCode);
        }
        String code = redisClient.hget(OmpConstants.Redis.Sms.CODE_HASH, phone);
        if (AssertUtils.notNull(verifyCode) && AssertUtils.notNull(code)
                && verifyCode.equals(code)) {
            //校验正确,删掉验证码
            String sendTime = redisClient.hget(OmpConstants.Redis.Sms.CODE_TIME, phone + ":" + code);
            //如果当前时间已经超过了有效期，返回失败
            if (AssertUtils.isNull(sendTime) ||
                    System.currentTimeMillis() > (Long.valueOf(sendTime) + SmsConstants.Common.EFFECTIVE_TIME)) {
                return false;
            }
            redisClient.hdel(OmpConstants.Redis.Sms.CODE_HASH, phone);
            redisClient.hdel(OmpConstants.Redis.Sms.CODE_TIME, phone + ":" + code);
            return true;
        }
        return false;
    }

}
