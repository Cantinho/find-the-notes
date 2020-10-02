package com.findthenotes.findthenotes.service;

/**
 * Created by samirtf on 26/07/17.
 */

public interface IApiCall {

    interface APICallResponse {
    }

    interface APICallbackResponse extends APICallResponse {
        void onResponse(APICallResponse response);

        void onFailure(String errorMesssage, int code);
    }
}
