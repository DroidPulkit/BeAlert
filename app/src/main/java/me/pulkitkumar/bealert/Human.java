package me.pulkitkumar.bealert;

import android.graphics.Bitmap;

/**
 * Created by pulkit on 23/3/17.
 */

public class Human {
    private String input;
    private int type;
    private Bitmap bm;

    public Human(String input, int type) {
        this.input = input;
        this.type = type;
    }

    public Human(Bitmap bm,int type) {
        this.type = type;
        this.bm = bm;
    }

    public String getInput() {
        return input;
    }

    public int getType() {
        return type;
    }

    public Bitmap getBm() {
        return bm;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
