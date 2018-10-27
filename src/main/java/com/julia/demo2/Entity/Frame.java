package com.julia.demo2.Entity;

public class Frame {

    private int id;
    private int roll1;
    private int roll2;
    private boolean roll1Done;
    private boolean roll2Done;

    public Frame() {}

    public Frame(int id, int roll1, int roll2) {
        this.id = id;
        this.roll1 = roll1;
        this.roll2 = roll2;
        this.roll1Done = true;
        this.roll2Done = true;
    }

    public Frame(int id, int roll1, int roll2, boolean roll1Done, boolean roll2Done) {
        this.id = id;
        this.roll1 = roll1;
        this.roll2 = roll2;
        this.roll1Done = roll1Done;
        this.roll2Done = roll2Done;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoll1() {
        return roll1;
    }

    public void setRoll1(int roll1) {
        this.roll1 = roll1;
    }

    public int getRoll2() {
        return roll2;
    }

    public void setRoll2(int roll2) {
        this.roll2 = roll2;
    }

    public boolean isRoll1Done() {
        return roll1Done;
    }

    public void setRoll1Done(boolean roll1Done) {
        this.roll1Done = roll1Done;
    }

    public boolean isRoll2Done() {
        return roll2Done;
    }

    public void setRoll2Done(boolean roll2Done) {
        this.roll2Done = roll2Done;
    }
}
