package com.ipd.mayachuxingoperation.bean;

import java.util.List;

public class FeedListBean {
    /**
     * code : 200
     * message : 操作成功
     * data : {"list":[{"imei":"866039041842528","lat":31.199576062479,"lng":121.26726149947},{"imei":"866039041838005","lat":30.037186865742,"lng":120.85592996357},{"imei":"57500001","lat":0,"lng":0},{"imei":"57500257","lat":0,"lng":0},{"imei":"57500134","lat":0,"lng":0},{"imei":"57500255","lat":0,"lng":0},{"imei":"57500019","lat":0,"lng":0},{"imei":"57500245","lat":0,"lng":0},{"imei":"57500167","lat":0,"lng":0},{"imei":"57500256","lat":29.517755222393,"lng":120.85612517786}]}
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
             * imei : 866039041842528
             * lat : 31.199576062479
             * lng : 121.26726149947
             */

            private String imei;
            private double lat;
            private double lng;

            public String getImei() {
                return imei;
            }

            public void setImei(String imei) {
                this.imei = imei;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }
        }
    }
}
