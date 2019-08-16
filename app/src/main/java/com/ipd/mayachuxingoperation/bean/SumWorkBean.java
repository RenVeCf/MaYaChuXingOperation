package com.ipd.mayachuxingoperation.bean;

public class SumWorkBean {
    /**
     * code : 200
     * message : 操作成功
     * data : {"problem":11,"repair":1,"dayProblem":0,"dayRepair":0}
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
         * problem : 11
         * repair : 1
         * dayProblem : 0
         * dayRepair : 0
         */

        private int problem;
        private int repair;
        private int dayProblem;
        private int dayRepair;

        public int getProblem() {
            return problem;
        }

        public void setProblem(int problem) {
            this.problem = problem;
        }

        public int getRepair() {
            return repair;
        }

        public void setRepair(int repair) {
            this.repair = repair;
        }

        public int getDayProblem() {
            return dayProblem;
        }

        public void setDayProblem(int dayProblem) {
            this.dayProblem = dayProblem;
        }

        public int getDayRepair() {
            return dayRepair;
        }

        public void setDayRepair(int dayRepair) {
            this.dayRepair = dayRepair;
        }
    }
}
