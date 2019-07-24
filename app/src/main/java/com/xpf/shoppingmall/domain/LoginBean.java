package com.xpf.shoppingmall.domain;

/**
 * Created by xpf on 2016/11/28 :)
 * Wechat:18091383534
 * Function:
 */

// 登录信息的Bean类
public class LoginBean {

    /**
     * status : 200
     * message : 请求成功
     * body : {"user":{"id":3,"createTime":null,"updateTime":null,"enableFlag":0,"username":"s","password":"34","createTimeString":"","updateTimeString":""}}
     * timestamp : 1480322024032
     * exception : null
     */

    private int status;
    private String message;
    /**
     * user : {"id":3,"createTime":null,"updateTime":null,"enableFlag":0,"username":"s","password":"34","createTimeString":"","updateTimeString":""}
     */

    private BodyBean body;
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

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
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

    public static class BodyBean {
        /**
         * id : 3
         * createTime : null
         * updateTime : null
         * enableFlag : 0
         * username : s
         * password : 34
         * createTimeString :
         * updateTimeString :
         */

        private UserBean user;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            private int id;
            private Object createTime;
            private Object updateTime;
            private int enableFlag;
            private String username;
            private String password;
            private String createTimeString;
            private String updateTimeString;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
            }

            public Object getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(Object updateTime) {
                this.updateTime = updateTime;
            }

            public int getEnableFlag() {
                return enableFlag;
            }

            public void setEnableFlag(int enableFlag) {
                this.enableFlag = enableFlag;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getCreateTimeString() {
                return createTimeString;
            }

            public void setCreateTimeString(String createTimeString) {
                this.createTimeString = createTimeString;
            }

            public String getUpdateTimeString() {
                return updateTimeString;
            }

            public void setUpdateTimeString(String updateTimeString) {
                this.updateTimeString = updateTimeString;
            }
        }
    }
}
