package com.ocss.back.common.util;

public class ResponseResult<T> {
    private static final long serialVersionUID = -5972075541185087205L;
    //      请求成功
    public static final String SUCCESS_CODE = "0000";
    //      产生异常
    public static final String EXCEPTION_CODE = "0500";
    //      处理失败
    public static final String FAIL_CODE = "0100";
    //      无权限访问
    public static final String AUTH_FAIL_CODE = "0403";
    //      登录失败
    public static final String LOGIN_FAIL_CODE = "0402";
    //      未登录
    public static final String LOGIN_REQUIRE_CODE = "0401";
    //      找不到
    public static final String NOT_FOUND_CODE = "0404";

    private String code = SUCCESS_CODE;

    private String msg;

    private T data;

    public ResponseResult() {

    }

    public ResponseResult(T data) {
        setData(data);
    }

    public ResponseResult(String code, String msg) {
        this(code, msg, null);
    }

    public ResponseResult(String code, String msg, T data) {
        this(data);
        setCode(code);
        setMsg(msg);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ResponseResult<T> createSuccessResult(String msg, T data) {
        return new ResponseResult<T>(ResponseResult.SUCCESS_CODE, msg, data);
    }

    public static <T> ResponseResult<T> createFailResult(String msg, T data) {
        return new ResponseResult<T>(ResponseResult.FAIL_CODE, msg, data);
    }

    public static <T> ResponseResult<T> createExceptionResult(String msg, T data) {
        return new ResponseResult<T>(ResponseResult.EXCEPTION_CODE, msg, data);
    }

    public static <T> ResponseResult<T> createAuthFailResult(T data) {
        return new ResponseResult<T>(ResponseResult.AUTH_FAIL_CODE, "无权限", data);
    }

    public static <T> ResponseResult<T> createLoginFailResult(T data) {
        return new ResponseResult<T>(ResponseResult.AUTH_FAIL_CODE, "登录失败", data);
    }

    public static <T> ResponseResult<T> createLoginRequireResult(T data) {
        return new ResponseResult<T>(ResponseResult.LOGIN_REQUIRE_CODE, "未登录", data);
    }

    public static <T> ResponseResult<T> createNotFoundResult(String msg, T data) {
        return new ResponseResult<T>(ResponseResult.NOT_FOUND_CODE, msg, data);
    }

    public String toString() {
        return JSONUtils.toJSONString(this);
    }

}