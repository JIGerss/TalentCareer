package pro.franky.talentcareer.pojo.enums;

/**
 * HTTP请求异常封装
 *
 * @author Steveny
 * @since 2023/6/24
 */
public enum HttpCodeEnum {
    // 操作成功==>200
    SUCCESS(200, "操作成功"),

    // 超时 ==> 400
    TIME_OUT(400, "系统超时"),

    // 登录错误==>1~50
    NEED_LOGIN_AFTER(1, "需要登录后操作"),
    LOGIN_PASSWORD_ERROR(2, "密码错误"),
    ACCOUNT_NOT_FOUND(3, "账号不存在"),
    ACCOUNT_DISABLED(4, "账号被禁用"),
    NUMBER_CODE_NOT_EQUAL(4, "验证码不匹配"),
    NUMBER_CODE_NOT_EXIST(5, "请先发送验证码"),

    // TOKEN50~100
    TOKEN_INVALID(50, "无效的TOKEN"),
    TOKEN_REQUIRE(51, "TOKEN是必须的"),
    TOKEN_EXPIRED_TIME(52, "TOKEN已过期"),
    TOKEN_ALGORITHM_SIGNATURE_INVALID(53, "TOKEN的算法或签名不对"),
    TOKEN_CANT_EMPTY(54, "TOKEN不能为空"),

    // SIGN验签 100~120
    SIGN_INVALID(100, "无效的SIGN"),
    SIG_TIMEOUT(101, "SIGN已过期"),

    // 参数错误 500~1000
    PARAM_REQUIRE(500, "缺少参数"),
    PARAM_INVALID(501, "无效参数"),
    PARAM_IMAGE_FORMAT_ERROR(502, "图片格式有误"),
    INVALID_ID(503, "该ID查无数据"),
    SERVER_ERROR(503, "服务器内部错误"),
    SERVER_BUSY(504, "服务器繁忙"),
    USER_NOT_REGISTERED(505, "用户未注册"),
    USER_REGISTERED(506, "用户已注册"),
    REQUEST_ERROR(507, "请求出错"),

    // 数据错误 1000~2000
    DATA_EXIST(1000, "数据已经存在"),
    DATA_PART_NOT_EXIST(1002, "前端请求，部分必要数据不能为空"),
    DATA_ALL_NOT_EXIST(1002, "前端请求，数据完全为空"),
    DATA_PART_ILLEGAL(1003, "部分数据不合法"),
    DATA_UPLOAD_ERROR(1004, "服务器上传失败"),
    PHONE_ILLEGAL(1005, "手机号为空或者格式不合法"),
    PASSWORD_ILLEGAL(1006, "密码为空或者格式不合法"),
    NUMBER_CODE_ILLEGAL(1007, "未获取验证码或者格式不合法"),
    TOKEN_DATA_INVALID(1008, "token里的荷载数据有误"),
    USERNAME_PASSWORD_ILLEGAL(1009, "账号或密码为空或长度过长"),

    // 数据错误 3000~3500
    NO_OPERATOR_AUTH(3000, "无权限操作"),
    NEED_ADMIN(3001, "需要管理员权限"),
    DATABASE_MYSQL_ERROR(3002, "MySQL数据库错误，联系后端"),
    DATABASE_MONGODB_ERROR(3003, "MongoDB数据库错误，联系后端"),
    CROSS_SERVICE_FAILED(3003, "跨服务调用失败"),

    // 自媒体文章错误 3501~3600
    MATERIASL_REFERENCE_FAIL(3501, "素材引用失效"),

    // 移动云API错误 4000+
    INVALID_REQUEST_PARAMETER(4003, "无效的请求参数（包括参数格式、参数类型、参数取值范围）"),
    UNSUPPORTED_PICTURE_FORMAT(4004, "不支持的文件格式"),
    UNSUPPORTED_PICTURE_TYPE(4005, "不支持的文件类型"),
    PICTURE_SIZE_INVALID(4006, "图片尺寸或大小不符合要求"),
    PDF_PAGE_NUMBER_OUT_RANGE(4007, "pdf页码超出范围"),
    ALGORITHM_INTERNAL_ERROR(4008, "算法内部错误"),

    // 本地错误 5000+
    FILE_READ_ERROR(5001, "文件无法成功读取"),
    FILE_TYPE_ERROR(5002, "文件格式错误"),
    PDF_READ_ERROR(5003, "PDF无法成功读取");


    private final Integer code;
    private final String errorMessage;

    HttpCodeEnum(int code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
