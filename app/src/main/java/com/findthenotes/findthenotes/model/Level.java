package com.findthenotes.findthenotes.model;


public enum Level {
    TRAINING ("TRAINING"),
    EASILY ("EASILY"),
    EASY ("EASY"),
    MEDIUM ("MEDIUM"),
    HARD ("HARD"),
    EXPERT ("EXPERT"),
    EXTREME ("EXTREME"),
    ABSOLUTE ("ABSOLUTE");

    private final String name;

    private Level(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }


}