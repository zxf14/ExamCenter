package com.nju.coursework.saas.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guhan on 17/11/8.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeneralResponse {
    private boolean success;
    private String msg;
    private Map<String, Object> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public GeneralResponse(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

//    public void setData(Map<String, Object> data) {
//        this.data = data;
//    }

    public void putDate(String name, Object value) {
        if (data == null) data = new HashMap<>();
        data.put(name, value);
    }

    @Override
    public String toString() {
        return "GeneralResponse{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
