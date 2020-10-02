package com.findthenotes.findthenotes.service;

/**
 * Created by samirtf on 26/07/17.
 */

public class StartGameResponse implements IApiCall.APICallResponse{

    /**
     * The date when start game request was created.
     */
    private String response;

    public StartGameResponse() {
        response = StartGameResponse.class.getCanonicalName() + " seen.";
    }

    public StartGameResponse(StartGameRequest startGameRequest) {
        response = StartGameResponse.class.getCanonicalName() + " seen at " + startGameRequest.getTimestamp() + ".";
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "StartGameResponse{" +
                "response='" + response + '\'' +
                '}';
    }
}
