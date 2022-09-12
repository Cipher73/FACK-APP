package com.example.appproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ListMenuItemView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class ProcessCards extends Activity {
    String target;
    String userID;
    String ID;
    Context view;
    Activity activity;
    ProcessRequests requests=new ProcessRequests("https://lamp.ms.wits.ac.za/home/s2345106/");
    public ProcessCards(String table, String loggedUser, String targetID, Activity a){
        target=table;
        userID=loggedUser;
        ID=targetID;
        activity=a;
    }

    public void GetData(){
        if (target.equals("QUESTION")) {
            RequestBody formBody = new FormBody.Builder()
                   .add("questionID", ID)
                    .add("userID", userID)
                    .build();
            requests.processRequest(activity, "voteQuestion", formBody, new RequestHandler() {
                @Override
                public void ProcessResponse(String response) throws JSONException {
                    ProcessJSON(response);

                }
            });
        }
        if (target.equals("ANSWERS")) {
            RequestBody formBody = new FormBody.Builder()
                    .add("answerID", ID)
                     .add("userID", userID)
                    .build();
            requests.processRequest(activity, "voteAnswer", formBody, new RequestHandler() {
                @Override
                public void ProcessResponse(String response) throws JSONException {
                    ProcessJSON(response);

                }
            });
        }
    }
    public void ProcessJSON(String data) throws JSONException {
        JSONArray jsonArray = new JSONArray(data);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        String s = jsonObject.getString("vote");

    }

}