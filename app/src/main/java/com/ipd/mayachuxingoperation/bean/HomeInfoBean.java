package com.ipd.mayachuxingoperation.bean;

public class HomeInfoBean {
    /**
     * code : 200
     * message : 操作成功
     * data : {"count":1,"normal":1,"monthCount":0,"monthRepair":0,"countProblem":4,"countStop":1,"countBade":3,"countNormal":2}
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
         * count : 1
         * normal : 1
         * monthCount : 0
         * monthRepair : 0
         * countProblem : 4
         * countStop : 1
         * countBade : 3
         * countNormal : 2
         */

        private int count;
        private int normal;
        private int monthCount;
        private int monthRepair;
        private int countProblem;
        private int countStop;
        private int countBade;
        private int countNormal;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getNormal() {
            return normal;
        }

        public void setNormal(int normal) {
            this.normal = normal;
        }

        public int getMonthCount() {
            return monthCount;
        }

        public void setMonthCount(int monthCount) {
            this.monthCount = monthCount;
        }

        public int getMonthRepair() {
            return monthRepair;
        }

        public void setMonthRepair(int monthRepair) {
            this.monthRepair = monthRepair;
        }

        public int getCountProblem() {
            return countProblem;
        }

        public void setCountProblem(int countProblem) {
            this.countProblem = countProblem;
        }

        public int getCountStop() {
            return countStop;
        }

        public void setCountStop(int countStop) {
            this.countStop = countStop;
        }

        public int getCountBade() {
            return countBade;
        }

        public void setCountBade(int countBade) {
            this.countBade = countBade;
        }

        public int getCountNormal() {
            return countNormal;
        }

        public void setCountNormal(int countNormal) {
            this.countNormal = countNormal;
        }
    }
}
