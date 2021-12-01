package com.example.englishstudy.global;

import androidx.cardview.widget.CardView;

public class StageItem {//stage:번호, running:진행중인지,다했는지
    private String stage;
    private String running;


    public StageItem(String stage, String running) {
        this.stage = stage;
        this.running = running;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getRunning() {
        return running;
    }

    public void setRunning(String running) {
        this.running = running;
    }
}
