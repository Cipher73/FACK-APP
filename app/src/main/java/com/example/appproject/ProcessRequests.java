package com.example.appproject;

import android.app.Activity;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProcessRequests {
    private final OkHttpClient client = new OkHttpClient();
    String url;
    public ProcessRequests(String prfx) {
        url=prfx;
    }

    public void processRequest(Activity activity, String method,RequestBody requestBody,RequestHandler requestHandler)
    {
        post(url+method+".php?", requestBody,"", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Something went wrong
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseStr = response.body().string();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                requestHandler.ProcessResponse(responseStr);
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }


                        }
                    });


                }
                else {
                    // Request not successful
                }
            }
        });

    }

    public void profile(){

    }
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Call post(String url, RequestBody formBody,String json, Callback callback) {

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

}


