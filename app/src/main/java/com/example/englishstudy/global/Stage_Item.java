package com.example.englishstudy.global;

public class Stage_Item {
    private String stage;
    private String running;

    public Stage_Item(String stage, String running) {
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
