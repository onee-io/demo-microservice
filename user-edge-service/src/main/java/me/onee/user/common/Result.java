package me.onee.user.common;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by VOREVER
 * Date: 2018/12/29 16:53
 */
@Data
public class Result<T> implements Serializable {
    private int code;
    private String info;
    private T data;

    public Result() {
    }

    public Result(int code, String info, T data) {
        this.code = code;
        this.info = info;
        this.data = data;
    }

    public Result(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public Result(ResultCode httpState) {
        this.code = httpState.getCode();
        this.info = httpState.getInfo();
    }

    public Result(ResultCode httpState, T data) {
        this.code = httpState.getCode();
        this.info = httpState.getInfo();
        this.data = data;
    }
}
