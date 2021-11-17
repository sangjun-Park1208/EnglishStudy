package com.example.englishstudy;

public class ReviewDayItem {
    private String stage;
    private String runnig;

    public ReviewDayItem(String stage, String runnig) {
        this.stage = stage;
        this.runnig = runnig;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getRunnig() {
        return runnig;
    }

    public void setRunnig(String runnig) {
        this.runnig = runnig;
    }
}
