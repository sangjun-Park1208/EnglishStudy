package com.example.englishstudy.global;

public class Stage_Item {//stage:번호, running:진행중인지,다했는지
    private String stage;
    private String running;
    private int correct;
    private int wrong;


    public Stage_Item(String stage, String running, int correct, int wrong) {
        this.stage = stage;
        this.running = running;
        this.correct = correct;
        this.wrong = wrong;
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

    public int getCorrect() {
        return correct;
    }



}
