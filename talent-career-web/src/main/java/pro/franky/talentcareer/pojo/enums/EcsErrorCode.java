package pro.franky.talentcareer.pojo.enums;

/**
 * 移动云异常映射类
 *
 * @author Steveny
 * @since 2023/6/24
 */
public enum EcsErrorCode {
    INVALID_REQUEST_PARAMETER("AIO.1003", HttpCodeEnum.INVALID_REQUEST_PARAMETER),
    UNSUPPORTED_PICTURE_FORMAT("AIO.1004", HttpCodeEnum.UNSUPPORTED_PICTURE_FORMAT),
    UNSUPPORTED_PICTURE_TYPE("AIO.1005", HttpCodeEnum.UNSUPPORTED_PICTURE_TYPE),
    PICTURE_SIZE_INVALID("AIO.1006", HttpCodeEnum.PICTURE_SIZE_INVALID),
    PDF_PAGE_NUMBER_OUT_RANGE("AIO.1007", HttpCodeEnum.PDF_PAGE_NUMBER_OUT_RANGE),
    ALGORITHM_INTERNAL_ERROR("AIO.1008", HttpCodeEnum.ALGORITHM_INTERNAL_ERROR);

    private final String errorCode;
    private final HttpCodeEnum httpCodeEnum;

    EcsErrorCode(String errorCode, HttpCodeEnum httpCodeEnum) {
        this.errorCode = errorCode;
        this.httpCodeEnum = httpCodeEnum;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpCodeEnum getHttpCodeEnum() {
        return httpCodeEnum;
    }

    @Override
    public String toString() {
        return "EcsErrorCode{" +
                "errorCode='" + errorCode + '\'' +
                ", httpCodeEnum=" + httpCodeEnum +
                '}';
    }
}
