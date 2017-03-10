package com.youanmi.scrm.omp.constants;


import com.youanmi.scrm.commons.util.cache.ApplicationConfCache;

/**
 * Created by Administrator on 2016/12/10.
 */
public interface OmpConstants {
    /**
     * 定义公共的常量。
     *
     * @author 刘红艳
     *
     */
    interface CommonModule {

        String DATA_SEPARATOR = ",";

    }

    /**
     * 用于发送验证码
     * @author zhangteng on 2017-02-16
     *
     */
    interface Redis {
        /**
         * redis的key本系统默认的前缀
         */
        String OMP_REDIS_PREFIX = ApplicationConfCache.getValue("common.sys.properties",
                "redis.prefix") + "SCRM_OMP_";

        /**
         * redis的token前缀
         */
        String SMP_TOKEN_PREFIX = OMP_REDIS_PREFIX + "TK_";

        /**
         * redis的记住密码token前缀
         */
        String SMP_REMEMBER_PREFIX = OMP_REDIS_PREFIX + "RMBTK_";

        /**
         * 验证码的key ,hash结构
         */
        String CAPTCHA_HASH = OMP_REDIS_PREFIX + "CAPTCHA_HASH";

        /**
         * 密码错误前缀
         */
        String PASSWORD_ERROR_PREFIX = OMP_REDIS_PREFIX + "PWD_ERR_";

        /**
         * 需要绑定手机号redis前缀
         */
        String NEED_BIND_PHONE_PREFIX = OMP_REDIS_PREFIX + "NEED_BIND_PHONE_";

        /**
         * 需要绑定手机号redis前缀
         */
        String BIND_PHONE_PREFIX = OMP_REDIS_PREFIX + "NEED_PHONE_";

        /**
         * 单用户登陆token前缀
         */
        String SINGLE_USER_ONLINE_TOKEN_PREFIX = OMP_REDIS_PREFIX + "SUOL_";

        /**
         * 密码错误次数redis存活秒数,10分钟
         */
        Integer REDIS_PASSWORD_ERROR_NUM_SECOND = 600;

        /**
         * 登录成功后,需要绑定手机号token生效时间,5分钟
         */
        Integer NEED_BIND_PHONE_SECONDS = 300;

        /**
         * 绑定手机号验证成功token生效时间,5分钟
         */
        Integer BIND_PHONE_SECONDS = 300;

        /**
         * 记住密码秒数,7天
         */
        Integer REMEMBER_USER_TOKEN_SECONDS = 604800;


        /**
         * 短信
         */
        interface Sms {
            /**
             * 实时验证码
             */
            String CODE_HASH = "code_hash";

            /**
             * 最新验证码的发送时间
             */
            String CODE_TIME = "code_time";
        }

        String WIZARD_PREFIX =  OMP_REDIS_PREFIX  + "WIZARD_PREFIX_";

    }
}
