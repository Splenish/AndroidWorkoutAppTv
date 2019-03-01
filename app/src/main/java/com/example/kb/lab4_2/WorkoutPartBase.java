package com.example.kb.lab4_2;

import java.io.Serializable;

public abstract class WorkoutPartBase implements Serializable {

    protected int length;

    public enum Type {
        WORKOUT,
        PAUSE
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    protected Type type;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
