package com.findthenotes.findthenotes.service;

import java.util.Date;

/**
 * Created by samirtf on 26/07/17.
 */

public class StartGameRequest {

    /**
     * The date when start game request was created.
     */
    private Date timestamp;

    public StartGameRequest() {
        timestamp = new Date();
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "StartGameRequest{" +
                "timestamp=" + timestamp +
                '}';
    }
}
