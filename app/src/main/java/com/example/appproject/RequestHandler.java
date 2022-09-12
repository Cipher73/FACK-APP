package com.example.appproject;

import org.json.JSONException;

public interface RequestHandler {
    public abstract void ProcessResponse(String response) throws JSONException;
}
