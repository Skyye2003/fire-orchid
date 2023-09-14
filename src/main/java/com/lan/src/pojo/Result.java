package com.lan.src.pojo;

import com.lan.src.common.CommonConstant;
import lombok.Data;

import java.io.Serializable;

/**
 *  接口返回数据格式
 */
@Data
public class Result<T> implements Serializable {

    /**
     * 成功标志
     */
    private boolean success = true;

    /**
     * 返回处理消息
     */
    private String message = "";

    /**
     * 返回代码
     */
    private Integer code = 0;

    /**
     * 返回数据对象 data
     */
    private T result;

    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();

    public Result() {
    }

    public Result(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public Result<T> success(String message) {
        this.message = message;
        this.code = CommonConstant.OK;
        return this;
    }

    public static<T> Result<T> ok(String msg) {
        Result<T> r = new Result<>();
        r.setSuccess(true);
        r.setCode(CommonConstant.OK);
        r.setMessage(msg);
        return r;
    }

    public static<T> Result<T> ok(T data) {
        Result<T> r = new Result<>();
        r.setSuccess(true);
        r.setCode(CommonConstant.OK);
        r.setResult(data);
        return r;
    }

    public static<T> Result<T> error(String msg) {
        Result<T> r = new Result<>();
        r.setSuccess(false);
        r.setMessage(msg);
        r.setCode(CommonConstant.INTERNAL_SERVER_ERROR);
        return r;
    }
}
