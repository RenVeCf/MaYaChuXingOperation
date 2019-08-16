package com.ipd.mayachuxingoperation.bean;

public class LoginBean {

    /**
     * code : 200
     * message : 操作成功
     * data : {"token":"c2fdc2f4caa9f319756a2b956ad9ca76","headerUrl":"","area":"上海","name":"某某"}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * token : c2fdc2f4caa9f319756a2b956ad9ca76
         * headerUrl :
         * area : 上海
         * name : 某某
         */

        private String token;
        private String headerUrl;
        private String area;
        private String name;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getHeaderUrl() {
            return headerUrl;
        }

        public void setHeaderUrl(String headerUrl) {
            this.headerUrl = headerUrl;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
