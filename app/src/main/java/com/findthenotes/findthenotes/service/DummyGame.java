package com.findthenotes.findthenotes.service;

/**
 * Created by samirtf on 25/07/17.
 */

public class DummyGame {

    /**
     * The game name.
     */
    private String name;

    /**
     * The game active: active or inactive.
     */
    private boolean active;

    /**
     * Retrieves the dummy game name.
     *
     * @return a string representing the dummy game name.
     */
    public String getName() {
        return name;
    }

    /**
     * Configures the dummy game name.
     *
     * @param name a string representing the dummy game name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns if dummy game is active.
     *
     * @return true if active; otherwise, false.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Configures the activation game state.
     *
     * @param active a boolean representing if game is active or inactive.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "DummyGame{" +
                "name='" + name + '\'' +
                ", active=" + active +
                '}';
    }
}
