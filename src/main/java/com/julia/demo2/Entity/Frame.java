package com.julia.demo2.Entity;

public class Frame {

    private int id;
    private int roll1;
    private int roll2;

    public Frame() {}

    public Frame(int id, int roll1, int roll2) {
        this.id = id;
        this.roll1 = roll1;
        this.roll2 = roll2;
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
}
