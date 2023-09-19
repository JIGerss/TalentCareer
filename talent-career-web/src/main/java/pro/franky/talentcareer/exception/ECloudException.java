package pro.franky.talentcareer.exception;

/**
 * 移动云API异常
 *
 * @author Steveny
 * @since 2023/6/24
 */
public class ECloudException extends RuntimeException {
    private String ecsErrorCode;

    public String getEcsErrorCode() {
        return ecsErrorCode;
    }

    public void setEcsErrorCode(String ecsErrorCode) {
        this.ecsErrorCode = ecsErrorCode;
    }

    //------------------------------------------------------------------------------------------------

    public ECloudException(String ecsErrorCode) {
        super();
        this.ecsErrorCode = ecsErrorCode;
    }

    public ECloudException(String message, String ecsErrorCode) {
        super(message);
        this.ecsErrorCode = ecsErrorCode;
    }

    public ECloudException(String message, Throwable cause, String ecsErrorCode) {
        super(message, cause);
        this.ecsErrorCode = ecsErrorCode;
    }

    public ECloudException(Throwable cause, String ecsErrorCode) {
        super(cause);
        this.ecsErrorCode = ecsErrorCode;
    }

    protected ECloudException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String ecsErrorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.ecsErrorCode = ecsErrorCode;
    }
}
