package com.ipd.mayachuxingoperation.bean;

import java.util.List;

public class DayMalfunctionBean {
    /**
     * code : 200
     * message : 操作成功
     * data : {"list":[{"id":28,"item_no":"866039041842528","time":"2019-07-31 14:51:40","type":"刹车失灵","lng":121.26633139881,"lat":31.199297308524}]}
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 28
             * item_no : 866039041842528
             * time : 2019-07-31 14:51:40
             * type : 刹车失灵
             * lng : 121.26633139881
             * lat : 31.199297308524
             */

            private int id;
            private String item_no;
            private String time;
            private String type;
            private double lng;
            private double lat;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getItem_no() {
                return item_no;
            }

            public void setItem_no(String item_no) {
                this.item_no = item_no;
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
}
