package com.abucloud.logbackdemo.exception;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @author party-abu
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resp<T> implements Serializable {

    private static Logger LOGGER = LoggerFactory.getLogger(Resp.class);

    private final static Integer SUCCESS_CODE = 200;

    public Integer resultCode;

    public String resultMsg;

    public T data;

    /**
     * 判断请求是否成功
     *
     * @param resultCode
     * @return
     */
    public static Boolean successful(Integer resultCode) {
        return SUCCESS_CODE.equals(resultCode);
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public static <T> Resp<T> fail(Integer code, String message) {

        Resp<T> resp = new Resp<T>();
        resp.setResultCode(code);
        resp.setResultMsg(message);
        return resp;
    }

    public static <T> Resp<T> success(T data) {

        Resp<T> resp = new Resp<T>();
        resp.setResultCode(SUCCESS_CODE);
        resp.setResultMsg("请求成功");
        resp.setData(data);
        return resp;
    }

    public static <T> Resp<T> success() {

        Resp<T> resp = new Resp<T>();
        resp.setResultCode(SUCCESS_CODE);
        resp.setResultMsg("请求成功");
        return resp;
    }
}
