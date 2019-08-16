package com.ipd.mayachuxingoperation.bean;

public class MalfunctionCarDetailsBean {
    /**
     * code : 200
     * message : 操作成功
     * data : {"id":"866039041842528","user":"185****4087","time":"2019-08-15 16:24:34","type":"车头歪了","supplement":"体内弟弟你哦你","status":1,"url":"/storage/problem/20190815/0a07b2ae10a0bcd013b974d792993bb1.jpeg","repair_url":"","end_time":"0000-00-00 00:00:00","lng":121.27188791089,"lat":31.197585777456}
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
         * id : 866039041842528
         * user : 185****4087
         * time : 2019-08-15 16:24:34
         * type : 车头歪了
         * supplement : 体内弟弟你哦你
         * status : 1
         * url : /storage/problem/20190815/0a07b2ae10a0bcd013b974d792993bb1.jpeg
         * repair_url :
         * end_time : 0000-00-00 00:00:00
         * lng : 121.27188791089
         * lat : 31.197585777456
         */

        private String id;
        private String user;
        private String time;
        private String type;
        private String supplement;
        private int status;
        private String url;
        private String repair_url;
        private String end_time;
        private double lng;
        private double lat;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSupplement() {
            return supplement;
        }

        public void setSupplement(String supplement) {
            this.supplement = supplement;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getRepair_url() {
            return repair_url;
        }

        public void setRepair_url(String repair_url) {
            this.repair_url = repair_url;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
    }
}
