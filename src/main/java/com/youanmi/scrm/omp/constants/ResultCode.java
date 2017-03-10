/*
 * 文 件  名：ResultCode.java
 * 版      权： Copyright 2012-2014 WanDong Tech. Co. Ltd. All Rights Reserved. 
 * 描      述： 详细描述
 * 修  改 人：龙汀<Administrator>
 * 修改时间：2014年8月29日
 * 修改内容：新增
 */
package com.youanmi.scrm.omp.constants;

/**
 * 返回详情码
 * <p>
 * 列举系统所有返回错误码
 * <p>
 * SUCCESS = "00000"; SYSTEM_ERROR = "10001";// 系统错误
 * 
 * <pre>
 * </pre>
 * 
 * @author 龙汀
 */
public interface ResultCode {
    /**
     * 操作成功
     */
    String SUCCESS = "00000";

    /**
     * // 用户不能修改自己本身权限
     */
    String CAN_NOT_UPDATE_SELF = "10007";

    /**
     * // 系统错误
     */
    String SYSTEM_ERROR = "10001";

    /**
     * // 远程服务错误
     */
    String REMOTE_SYSTEM_ERROR = "10002";

    /**
     * // 任务过多，系统繁忙
     */
    String OPERATE_FREQUENTLY = "10003";

    /**
     * // 请求超时
     */
    String REQUEST_TIMEOUT = "10004";

    /**
     * // 非法请求
     */
    String ILLEGALITY_REQUEST = "10005";

    /**
     * // 请求的HTTP // METHOD不支持，请请参考API文档是否选择了正确的POST/GET方式
     */
    String INVALID_REQUEST = "10006";

    /**
     * // 数据已存在，不能重复添加
     */
    String DATA_EXIST = "10008";

    /**
     * // 模板已存在，不能重复添加
     */
    String APP_STYLE_EXIST = "10009";

    /**
     * // 颜色码和样式码已存在，不能重复添加
     */
    String APP_STYLE_COLOR_EXIST = "10010";

    /**
     * // 保修范围已存在，不能重复添加
     */
    String RENEWAL_ADD_NAME_EXIST = "10011";

    /**
     * // 保修部件与保修范围不能同名
     */
    String NAME_PARTNAME_NOT_SAME = "10012";

    /**
     * // 保修部件与保修范围不能同名
     */
    String NAME_PROMOTION_NOT_SAME = "10013";

    /**
     * // 运营平台用到--提示没有权限
     */
    String NO_OPER_MISSION = "10014";

    /**
     * // 不允许对该数据操作
     */
    String CAN_NOT_OPER_DATA = "20108";

    /**
     * // 没有找到对应的资源
     */
    String RESOURCE_NOT_FOUND = "20109";

    /**
     * // 参数错误
     */
    String PARAMETER_ERROR = "20001";

    /**
     * // 参数{0}类型不正确
     */
    String PARAMETER_TYPE_FAILED = "20002";

    /**
     * // 参数{0}值超出范围
     */
    String PARAMETER_OVERFLOW = "20003";

    /**
     * // 参数{0}值超出长度
     */
    String PARAMETER_LENGTH_BEYOND = "20004";

    /**
     * // 参数{0}值格式不正确
     */
    String PARAMETER_FORMAT_FAILED = "20005";

    /**
     * // 参数{0}不能为空
     */
    String PARAMETER_NOT_NULL = "20006";

    /**
     * // json转换失败
     */
    String JSON_FORMAT_NULL = "20007";

    /**
     * // IO关闭异常
     */
    String IO_CLOSE_ERROR = "20008";

    /**
     * // 用户不存在
     */
    String USER_NOT_FOUND = "20009";

    /**
     * // 密码错误
     */
    String PASSWORD_WRONG = "20010";

    /**
     * // session失效
     */
    String SESSION_INVALID = "20011";

    /**
     * // 未知错误
     */
    String UNKNOWN_ERROR = "29999";

    /**
     * // 该节点下含有子节点
     */
    String HAVE_CHILD_NODE = "20012";

    /**
     * // 不能操作超级管理员
     */
    String CAN_NOT_OPERATE_SUPER = "20013";

    /**
     * // 用户未登录
     */
    String USER_NOT_LOGIN = "20014";

    /** 认证失败 */
    String LOGIN_AUTHORIZATION_FAILD = "20015";

    /** 账户不可用 */
    String LOGIN_ACCT_DISABLED = "20016";

    /** 账户已存在 */
    String USER_EXIST = "20017";

    /** 解压APK失败 */
    String APK_UIZIP_ERROR = "20018";

    /** 制作APK失败 */
    String APK_MADE_ERROR = "20019";

    /** 文件操作异常 */
    String FILE_OPERATIONS_ERROR = "20020";

    /** 不允许对该账户操作 */
    String NO_ACCESS_USER = "20021";

    /** 注册店铺不存在 */
    String SHOP_NOT_EXIST = "20022";

    /**
     * // 图片格式错误
     */
    String IMG_FORMART_ERROR = "20023";

    /**
     * // 图片不存在
     */
    String IMG_NOT_FOUND = "20024";

    /**
     * // 上传文件失败
     */
    String IMG_UPLOAD_FAILD = "20025";

    /**
     * // 获取APK图标失败
     */
    String APK_ICON_GET_FAILD = "20026";

    /** APP写入失败 */
    String APK_NAME_MODIFY_FAILD = "20027";

    /** 文件大小过大 */
    String FILE_TOO_LARGE = "20028";

    /** 文件尺寸错误 */
    String IMG_SIZE_ERROR = "20029";

    /** APP定制信息锁定期，不允许操作 */
    String APP_IS_LOCK = "20030";

    /** APP定制次数已达到最大次数不允许操作 */
    String APP_MAX_UPDATE = "20031";

    /**
     * 20124=对不起,系统升级中,暂时无法打包app
     */
    String APP_CAN_NOT_PACK = "20124";

    /**
     * 20127=对不起,系统升级中,暂时不允许提交打包申请
     */
    String APP_CAN_NOT_ADD_PACK_APPLY = "20127";

    /**
     * 20128=请先生成APP后再新增店员
     */
    String NO_APP_CANNOT_ADD_SHOP_USER = "20128";

    /** 应用名已存在 */
    String APP_NAME_EXIST = "20032";

    /** 上传图片个数已达到最大 */
    String IMG_MAX_AMOUNT = "20033";

    /** 门店名称已存在 */
    String SHOPNAME_EXIST = "20034";

    /** 图片裁剪失败 */
    String IMG_CUT_FAILD = "20035";

    /** 数据过大，请精确查找条件 */
    String TOO_MUCH_DATA = "20036";

    /**
     * 生成海报失败:20037
     */
    String GENERAL_POSTER_FALI = "20037";

    /** 手机号在系统中不存在 */
    String PHONE_NOT_FOUND = "20104";

    /** 短信验证码错误 */
    String VERIFY_CODE_ERROR = "20103";

    /** 图形验证码不正确 */
    // hy 2015年11月20日 11:49:07
    String IMG_VERIFY_CODE_ERROR = "20226";

    /**
     * {0}不存在:20227
     */
    String RESOURCE_NOT_EXIST = "20227";

    /**
     * 此手机价格没有相应的套餐:20228
     */
    String PHONE_PRICE_HAS_NO_PACKAGE = "20228";
    
    /**
     * 操作失败,订单已变更:20229
     */
    String ORDER_IS_CHANGE = "20229";

    /** Token过期 */
    String TOKEN_TIME_LIMIT = "20102";

    /** Token无效 */
    String TOKEN_INVALID = "20101";

    /** 重复提交 */
    String REPEAT_REQUEST = "20105";

    /** 父用户不存在 */
    String PARENT_ID_NULL = "20102";

    /**
     * 产品库存不足
     */
    String PRODUCT_STOCK_NOTENOUGH = "20106";

    /**
     * 字段过长
     */
    String DATA_OVER_LENGTH = "20110";

    /**
     * 推送时间小于当前时间
     */
    String PUSH_TIME_LG_CURTIME = "20111";

    /**
     * 发布个数大于{0}
     */
    String PUBLISH_NUMBER_TO_MUCH = "20112";

    /**
     * 记录已删除
     */
    String DATA_ONLY_DELETE = "20113";

    /**
     * 发布中状态不允许删除数据
     */
    String DATA_PUBLISH_STATUS_CANNOT_REMOVE = "20114";

    /**
     * 标题不能空
     */
    String TITLE_CAN_NOT_BE_NULL = "20115";

    /**
     * 标题太长
     */
    String TITLE_LENGTH_TO_LONG = "20116";

    /**
     * 广告对象不能为空
     */
    String BANNER_TARGET_CAN_NOT_BE_NULL = "20117";

    /**
     * 图片不能为空
     */
    String IMAGE_CAN_NOT_BE_NULL = "20118";

    /**
     * 内容不能为空
     */
    String CONTENT_CAN_NOT_BE_NULL = "20119";

    /**
     * 内容长度太长
     */
    String CONTENT_LENGTH_TO_LONG = "20120";

    /**
     * 广告对象错误
     */
    String BANNER_TARGET_ERROR = "20121";

    /**
     * 记录不存在
     */
    String DATA_NOT_EXIST = "20122";

    /**
     * 不能添加自己为好友 tgj 20151229
     */
    String FRIENDS_NOT_ADD_ME = "20126";
    /******************** 路由模块相关操作 202开头的别乱用了 ***************************/
    /** 路由SN号不存在 */
    String ROUTE_SN_NOT_EXIST = "20201";
    /** 路由SN号已经已经被绑定 */
    String ROUTE_SN_BND_ALERADLY = "20213";

    /** 路由SN绑定失败 */
    String ROUTE_SN_BAND_FAILER = "20203";

    /** 注册商户到18失败 */
    String REGISTER_ACCOUNT_TO18_FAILER = "20204";

    /** 注册店员到18失败 */
    String REGISTER_SHOPUSER_TO18_FAILER = "20205";

    /** 路由收益查询失败 */
    String ROUTE_INCOME_QUERY_FAILER = "20206";

    /** 路由店员收益查询失败 */
    String ROUTE_CLERKINCOME_QUERY_FAILER = "20207";

    /** 路由装机收益查询失败 */
    String ROUTE_SETTLEINFO_QUERY_FAILER = "20208";

    /** 清楚手机imei数据失败 */
    String CLAR_MOBILE_DATA_FAILER = "20209";

    /** 路由SN解绑失败 */
    String ROUTE_SN_UNBAND_FAILER = "20210";

    /** 删除店员从18失败 */
    String DEL_SHOPUSER_FROM18_FAILER = "20211";

    /** 从18获取数据失败失败 */
    String GET_DATA_FROM18_FAILER = "20212";

    /******************** end ***************************/
    /** 版本名存在 */
    String VERSION_NAME_EXIST = "20202";

    /** 发布中的商品banner已到上限（6个），不可再发布 */
    String BANNER_MAX_OPEN = "20301";

    /** 没有对应的续保类型 */
    String NO_EXIST_NENEW = "20401";

    /** 资源已经创建 */
    String RESOURCE_IS_EXIT = "20107";

    /** 资源已经不存在 */
    String RESOURCE_IS_NOTEXIT = "20109";

    /** 字段过长 */
    String RESOURCE_TOO_LONG = "20110";

    /** 名称已存在 */
    String NAME_EXIST = "20111";

    /******************** 支付错误 301开头的别乱用了 ***************************/
    /** 已支付 */
    String VERIFY_PAY_STATUS_ERROR = "30100";

    /** 支付密码错误 */
    String VERIFY_PAY_PAW_ERROR = "30101";

    /** 支付密码已设置 */
    String VERIFY_PAY_PAW_REPEAT_ERROR = "30108";

    /** 未设置支付密码 */
    String VERIFY_PAY_PAW_SET_ERROR = "30111";

    /** 支付密码错误次数限制 */
    String VERIFY_PAY_PAW_COUNT_ERROR = "30102";

    /** 获取银行卡信息出错 */
    String VERIFY_PAY_CARD_INFO_ERROR = "30103";

    /** 重复添加 */
    String VERIFY_PAY_CARD_REPEAT_ERROR = "30104";

    /** 验证码超时 */
    String VERIFY_CODE_TIME_OUT = "30105";

    /** 找不到对应的订单流水号 */
    String VERIFY_CODE_PAY_NOT_NUMBER_NO = "30106";

    /** 余额不足 */
    String VERIFY_CODE_PAY_USER_BALANCE = "30109";

    /** 参数错误 **/
    String VERIFY_CODE_PAY_WITHDRAWALS_ERROR = "30113";

    /** 订单接必须大于0 **/
    String VERIFY_CODE_PAY_ORDER_AMOUNT = "30112";

    /** 参数错误 **/
    String VERIFY_CODE_PAY_BALANCE_BALANCE = "30110";

    /** 冻结资金余额不足 */
    String VERIFY_CODE_PAY_UNFREEZE_FUNDS = "30114";

    /** 信用卡不支持 **/
    String VERIFY_CODE_PAY_CREDIT = "30115";

    /** 提现金额不能小于100元 **/
    String VERIFY_CODE_PAY_WITHDRAW = "30116";

    /** 提现失败 */
    String VERIFY_CODE_PAY_WITHDRAW_ERROR = "30117";

    /** 该订单已被处理 */
    String VERIFY_CODE_PAY_WITHDRAW_ERROR1 = "30118";

    /** 该银行卡暂不支持绑定，请使用其他银行卡 */
    String VERIFY_CODE_PAY_NOT_SUPPORT_ERROR = "30119";
    /** 此银行卡已绑定 */
    String VERIFY_CODE_PAY_CARD_REPEAT_BIND = "30120";

    /** 重复退款 */
    String VERIFY_CODE_REPEAT_REFUND = "30121";
    /** {0}退款失败，错误码[{1}] ，如参数｛0｝=微信，错误码={NOTENOUGH}*/
    String VERIFY_CODE_REFUND_FAIL = "30198";
    /** 退款失败 */
    String VERIFY_CODE_REFUND_ERROR = "30199";
    /******************** 支付错误 301开头的别乱用了 ***************************/
    // 商品模块-----------------------------------
    /**
     * 商品购买类型不正确
     */
    String ORDER_PRODUCT_TYPE_ERROR = "30000";
    
    /**
     * 商品购买价格不正确
     */
    String ORDER_PRODUCT_PRICE_ERROR = "30001";

    /**
     * 订单签名错误
     */
    String ORDER_PRODUCT_SIGN_ERROR = "30002";

    /**
     * 订单发送提货码失败
     */
    String ORDER_SENDCODE_MESSAGE_ERROR = "30003";

    /**
     * // 订单支付修改状态错误
     */
    String ORDER_PAY_MODIFY_STATUS_ERROR = "30004";

    /**
     * // 发货状态错误
     */
    String SEND_MODIFY_STATUS_ERROR = "30008";

    /**
     * // 订单支付修改状态错误
     */
    String ORDER_PRODUCT_NOT_PAKAGE = "30005";

    /**
     * // 订单操作状态错误
     */

    String ORDER_OP_STATUS_ERROR = "30006";

    /**
     * 
     */
    // 购物车商品类型错误
    String SHOPPING_PRODUCT_TYPE_ERROR = "30007";
    /**
     * 店铺业务员编号不存在，业务员编号错误
     */
    String SHOP_SERIALNUM_NOT_EXIST = "30009";
    /**
     * 订单已存在，不能重复添加
     */
    String ORDER_EXIST = "30010";
    /**
     * 产品已经下 架
     */
    String PRODUCT_DOWN_SHELVES = "30011";
    
    /**
     * 申请单已处理
     */
    String ORDER_APPLY_HAS_PROCES = "30015";

    /**
     * // 该用户没有对应权限
     */
    String USER_NO_PERMISSION = "3008";

    /**
     * 
     */
    // 订单退款支付失败
    String ORDER_REFUND_PAY_FAIL = "31009";

    /**
     * // 订单退款支付失败
     */
    String ORDER_REFUND_PRICE_ERROR = "31010";

    /**
     * // 订单删除失败
     */
    String ORDER_REFUND_DEL_ERROR = "31011";

    /**
     * // 取货货状态错误
     */
    String ORDER_DEVERYLIY_STATUS_ERROR = "31015";

    /**
     * 
     */
    // 取货码错误
    String ORDER_DEVERYLIY_CODE_ERROR = "31014";

    /**
     * // 取货码错误
     */
    String ORDER_REFUND_TYPE_ERROR = "31017";

    /**
     * 非法参数
     */
    String ILLEGAL_PARAM = "30012";

    /**
     * 非商铺用户
     */
    String ILLEGAL_SHOP_USER = "30013";

    /**
     * 环信操作报错
     */
    String HX_OPT_ERROR = "30016";

    /**
     * 店铺账号不可用
     */
    String SHOP_NOT_USE = "30017";

    /**
     * 用户没有权限
     */
    String SHIRO_USER_NOT_PERMISSION = "30018";

    /**
     * USER_IN_CHECK
     */
    String USER_IN_CHECK = "30019";

    /**
     * // 手机号码和账户不匹配
     */
    String USER_MOBILE_ERROR = "30020";

    /**
     * 商户被删除
     */
    String SHOP_IS_DEL = "30021";

    /**
     * 商户审核不通过
     */
    String SHOP_CHECK_NOT_PASS = "30022";
    
    /**
     * 商户审核
     */
    String SHOP_CHECK = "40001";

    /**
     * 商户被禁用
     */
    String SHOP_IS_FORBID = "30023";

    /**
     * 商户没有定制套餐，或者套餐过期，或者套餐被禁用
     */
    String SHOP_MEAL_DISABLE = "33024";

    /**
     * 商户没有购买套餐
     */
    String SHOP_NOT_FOUND = "33025";

    /**
     * 商户套餐过期
     */
    String SHOP_MELA_TIMEOUT = "33026";

    /**
     * 该手机号码已经是商户账号
     */
    String SHOP_USERNAME = "33027";

    /**
     * FLOW_MFCT_SN_EMPTY
     */
    String FLOW_MFCT_SN_EMPTY = "40000";

    /**
     * FLOW_RECHARGE_EXCEPTION
     */
    String FLOW_RECHARGE_EXCEPTION = "33028";

    /** 流量充值失败[{0}:{1}]，0=厂商Sn，1=对方接口响应码 */
    String FLOW_RECHARGE_FAILD = "33029";

    /** 客服电话已存在 */
    String SHOP_SERVER_PHONE_EXIST = "33030";

    /**
     * 注册18路由失败
     */
    String register_18_faild = "33040";

    /**
     * 支付参数参数错误提示
     */
    String alipay_param_error = "33041";

    /**
     * 角色名称已经存在
     * 
     * @author 张秋平 2015-12-10
     */
    String role_name_repeat = "33042";

    /**
     * 至少选择一个权限
     * 
     * @author 张秋平 2015-12-10
     */
    String permission_at_least_one = "40015";

    /**
     * 店长已经被定义，请不要重复定义店长
     * 
     * @author 张秋平 2015-12-10
     */
    String role_type_shop_exists = "33044";

    /**
     * 角色权限包是无效的,请刷新后重试
     * 
     * @author 张秋平 2015-12-14
     */
    String role_package_is_not_valid = "33045";

    /**
     * 该岗位被关联多个店员，不能修改为店长权限
     */
    String role_relate_user_is_more = "33046";
    /**
     * 角色信息已被修改，请刷新后重试
     */
    String role_relate_user_is_edit = "33047";

    /**
     * 已绑定推广人
     */
    String PROMOTIONER_HAS_EXIST = "40001";

    /**
     * 推广码无效
     */
    String PROMOTIONER_NO_EXIST = "40002";

    /**
     * 找不到门店
     */
    String SHOP_NO_EXIST = "40003";

    /**
     * 圈子已存在
     */
    String CIRCLE_EXIST = "40004";

    /**
     * 圈子未创建
     */
    String CIRCLE_NOT_EXIST = "40005";

    /**
     * 请先设置昵称才能发帖
     */
    String REQUEIRD_NICKNAME = "40006";

    /** 信息无效 线刷推广信息无效 */
    String ROUTER_OR_SHOP_INVALID = "40007";

    /** 不能绑定自己 */
    String NOT_BIND_MYSELF = "40008";

    /** 不能绑定其它门店 */
    String NOT_BIND_OTHERSHOP = "40009";

    /**
     * 大转盘活动异常编码，错误信息自定义,没有配置到common.result.properties中
     * 
     * @author 张秋平 2015-12-04增加
     */
    String activity_province_err = "33042";

    /**
     * 您已经报过名了
     */
    String MEETING_JOIN_ONCE = "40010";

    /**
     * 对应关系已存在,不能重复添加
     */
    String RELATION_EXSIT = "40011";

    /**
     * 没有该系统品牌
     */
    String PRODUCT_NOT_EXSIT = "40012";

    /**
     * 没有该系统型号
     */
    String MODEL_NOT_EXSIT = "40013";

    /**
     * 系统型号与系统品牌不匹配
     */
    String PRDOCUT_NOT_MATCH = "40014";

    /**
     * 剁手党活动已停止
     * 
     * @author 张秋平
     */
    String CHOP_HAND_ACTIVITY_STOP = "50001";

    /**
     * 用户未安装app
     */
    String APP_UNINSTALL = "50002";

    /**
     * 请确认该流量厂商的中文全称是否正确
     */
    String FLOW_MANUFACTORY_NOT_EXIST = "50419";

    /**
     * 该流量厂商已存在,不能重复添加
     */
    String FLOW_MANUFACTORY_EXIST = "54190";
    
    /**
     * 流量套餐已下架
     *  @author 张秋平 2016-06-30
     */
    String FLOW_MEAL_NOT_EXIST = "54191";
    
    /**
     * 修改奖品数量小于中奖数量
     * @author 张秋平 2016-06-30
     */
    String PRIZE_COUNT_LESS_THEN_WIN_COUNT = "54192";

    
    /**
     * 已领取过该优惠
     */
    String DISCOUNT_HAS_BEEN_TOKEN = "55000";
    
    /**
     * 请先设置到店优惠
     */
    String DISCOUNT_NOT_SET = "55001";
    
    /**
     * 已使用过该优惠
     */
    String DISCOUNT_HAS_BEEN_USED = "55002";
   
    /****** 209开头的 系统级的提示码 请勿乱用 hy ****/
    interface Sys {
        /*** 签名错误 */
        static final String SIGN_ERROR = "20901";// @author 刘红艳 2016-06-08
        /*** 非法访问数据 */                                        // add@账户体系
        static final String NO_PERMISSION = "20902";    
        /** 资源锁定中 2016-08-15 hy add at 账户体系分布式事务优化 */
        static final String RESOURCE_LOCKED = "20903";
        /** 分布式事务操作失败 */
        static final String ZK_TRANSACTION_ERROR = "20904";
        /** 审核业务不存在 */
        static final String BIZ_NO_EXIST = "20905";

    }

    /** 601 开头的是账户体系用的，请勿乱用 hy */
    interface Account {
        /** 已审核 */
        final String CHECKED = "601001";
        
        /**分店已删除*/
        final String ORG_IS_DELETE = "601002";
        
        /**普通店员必须制定分店*/
        final String CLERK_MUST_APPOINT_BRANCH = "601003";
        
        
    }
}
