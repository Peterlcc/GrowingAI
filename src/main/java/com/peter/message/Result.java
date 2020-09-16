package com.peter.message;

import java.io.Serializable;

/**
 * @author lcc
 * @date 2020/7/14 下午12:45
 */
public class Result implements Serializable {

    private static final long serialVersionUID = -3948389268046368059L;

    private Integer code;

    private String msg;

    private Object data;

    public Result() {
    }
    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setResultCode(ResultCode code) {
        this.code = code.code();
        this.msg = code.message();
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static Result success() {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    public static Result failure(ResultCode resultCode) {
        Result result = new Result();
        result.setResultCode(resultCode);
        return result;
    }

    public static Result failure(ResultCode resultCode, Object data) {
        Result result = new Result();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }

//    public static void main(String[] args) {
//        System.out.println(Result.success());
//
//        System.out.println(Result.failure(ResultCode.DATA_ALREADY_EXISTED));
//    }
}
