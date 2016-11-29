package com.atguigu.shoppingmall.domain;

/**
 * Created by xpf on 2016/11/28 :)
 * Wechat:18091383534
 * Function:
 */

// 注册信息的Bean类
public class RegisterBean {

    /**
     * status : 313
     * message : 用户已存在，注册失败！
     * body : null
     * timestamp : 1480321729573
     * exception : null
     */

    private int status;
    private String message;
    private Object body;
    private long timestamp;
    private Object exception;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Object getException() {
        return exception;
    }

    public void setException(Object exception) {
        this.exception = exception;
    }
}
