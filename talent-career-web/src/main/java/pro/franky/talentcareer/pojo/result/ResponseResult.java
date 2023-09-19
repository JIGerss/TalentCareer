package pro.franky.talentcareer.pojo.result;

import pro.franky.talentcareer.pojo.enums.HttpCodeEnum;

import java.io.Serial;
import java.io.Serializable;

/**
 * 响应结果
 * 通用返回结果类
 *
 * @author Steveny
 * @since 2023/6/24
 */
@SuppressWarnings({"rawtypes", "unchecked", "unused"})
public class ResponseResult<T> implements Serializable {
    //=============================成员属性
    private Integer code;

    private String errorMessage;

    private T data;

    @Serial
    private static final long serialVersionUID = 1919081114514233L;

    //=============================构造方法
    public ResponseResult() {
        this.code = 200;
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.errorMessage = msg;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.errorMessage = msg;
    }

    //====================================静态方法

    public static <T> ResponseResult<T> okResult(int code, String msg) {
        ResponseResult result = new ResponseResult();
        return result.ok(code, null, msg);
    }

    public static <T> ResponseResult<T> okResult(T data) {
        ResponseResult result = setHttpCodeEnum(HttpCodeEnum.SUCCESS, HttpCodeEnum.SUCCESS.getErrorMessage());
        if (data != null) {
            result.setData(data);
        }
        return result;
    }

    public static <T> ResponseResult<T> okResult() {
        ResponseResult result = new ResponseResult();
        return result.ok(200, null, "操作成功");
    }

    public static <T> ResponseResult<T> errorResult(int code, String msg) {
        ResponseResult result = new ResponseResult();
        return result.error(code, msg);
    }

    public static <T> ResponseResult<T> errorResult(HttpCodeEnum enums) {
        return setHttpCodeEnum(enums, enums.getErrorMessage());
    }

    public static <T> ResponseResult<T> errorResult(HttpCodeEnum enums, String errorMessage) {
        return setHttpCodeEnum(enums, errorMessage);
    }

    public static <T> ResponseResult<T> setHttpCodeEnum(HttpCodeEnum enums) {
        return okResult(enums.getCode(), enums.getErrorMessage());
    }

    private static <T> ResponseResult<T> setHttpCodeEnum(HttpCodeEnum enums, String errorMessage) {
        return okResult(enums.getCode(), errorMessage);
    }

    //=============================泛型构造方法

    public ResponseResult<?> ok(Integer code, T data) {
        this.code = code;
        this.data = data;
        return this;
    }

    public ResponseResult<?> ok(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.errorMessage = msg;
        return this;
    }

    public ResponseResult<?> ok(T data) {
        this.data = data;
        return this;
    }

    public ResponseResult<?> error(Integer code, String msg) {
        this.code = code;
        this.errorMessage = msg;
        return this;
    }

    //===============================setter and getter
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "\ncode=" + code +
                "\nerrorMessage='" + errorMessage + '\'' +
                "\ndata=" + data +
                '}';
    }
}
