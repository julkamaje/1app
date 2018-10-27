package com.julia.demo2.Entity;

public class LastFrame extends Frame {

    private int roll3;
    private boolean roll3Done;


    public LastFrame() {}

    public LastFrame(int id, int roll1, int roll2, int roll3) {
        super(id, roll1, roll2);
        this.roll3 = roll3;
        this.roll3Done = true;
    }

    public LastFrame(int id, int roll1, int roll2, int roll3
            , boolean isRoll1Done, boolean isRoll2Done, boolean isRoll3Done) {
        super(id, roll1, roll2, isRoll1Done, isRoll2Done);
        this.roll3 = roll3;
        this.roll3Done = isRoll3Done;
    }

    public int getRoll3() {
        return roll3;
    }

    public void setRoll3(int roll3) {
        this.roll3 = roll3;
    }

    public boolean isRoll3Done() {
        return roll3Done;
    }

    public void setRoll3Done(boolean roll3Done) {
        this.roll3Done = roll3Done;
    }
}
