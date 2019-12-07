package com.peter.bean;

public class Result {
    private boolean correct;
    private String msg;

    public Result(boolean correct, String msg) {
        this.correct = correct;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Result{" +
                "correct=" + correct +
                ", msg='" + msg + '\'' +
                '}';
    }
}
