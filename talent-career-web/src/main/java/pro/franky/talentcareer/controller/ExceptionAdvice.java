package pro.franky.talentcareer.controller;

import com.ecloud.sdk.ApiException;
import jakarta.servlet.ServletException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pro.franky.talentcareer.exception.ECloudException;
import pro.franky.talentcareer.pojo.enums.EcsErrorCode;
import pro.franky.talentcareer.pojo.enums.HttpCodeEnum;
import pro.franky.talentcareer.pojo.result.ResponseResult;

import java.io.IOException;
import java.util.List;

/**
 * 全局异常处理
 *
 * @author Steveny
 * @since 2023/6/24
 */
@RestControllerAdvice
@SuppressWarnings("rawtypes")
public class ExceptionAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseResult doException(Exception e) {
        e.printStackTrace();
        return ResponseResult.errorResult(HttpCodeEnum.SERVER_ERROR);
    }

    @ExceptionHandler(IOException.class)
    public ResponseResult doIOException() {
        return ResponseResult.errorResult(HttpCodeEnum.FILE_READ_ERROR);
    }

    @ExceptionHandler(ServletException.class)
    public ResponseResult doServletException() {
        return ResponseResult.errorResult(HttpCodeEnum.REQUEST_ERROR);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseResult doServletRequestBindingException() {
        return ResponseResult.errorResult(HttpCodeEnum.PARAM_REQUIRE);
    }

    @ExceptionHandler(BindException.class)
    public ResponseResult doBindException(BindException exception) {
        List<FieldError> allErrors = exception.getFieldErrors();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < allErrors.size(); i++) {
            FieldError error = allErrors.get(i);
            sb.append(error.getField())
                    .append(": ")
                    .append(error.getDefaultMessage());
            if (i != allErrors.size() - 1)
                sb.append(", ");
        }
        return ResponseResult.errorResult(HttpCodeEnum.PARAM_INVALID, sb.toString());
    }

    @ExceptionHandler(ApiException.class)
    public ResponseResult doApiException(ApiException e) {
        e.printStackTrace();
        return ResponseResult.errorResult(HttpCodeEnum.TIME_OUT, "跨服务请求失败");
    }

    @ExceptionHandler(ECloudException.class)
    public ResponseResult doECloudException(ECloudException e) {
        for (EcsErrorCode errorCode : EcsErrorCode.values()) {
            if (errorCode.getErrorCode().equals(e.getEcsErrorCode())) {
                return ResponseResult.errorResult(errorCode.getHttpCodeEnum());
            }
        }
        return doApiException(new ApiException(e));
    }
}
