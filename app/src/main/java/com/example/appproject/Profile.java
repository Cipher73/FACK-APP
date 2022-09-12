package com.example.appproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class Profile extends AppCompatActivity {
    TextView Name;
    TextView Surname;
    TextView NumQ;
    TextView NumAnswers;
    String userID="";
    Button button;
    ProcessRequests requests=new ProcessRequests("https://lamp.ms.wits.ac.za/home/s2345106/");
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //Name= findViewById(R.id.edtname);
        //Surname= findViewById(R.id.edt);
        button=findViewById(R.id.UserQuestions);
        NumAnswers = findViewById(R.id.NumAns);
        NumQ= findViewById(R.id.NumQ);
        userID = getIntent().getStringExtra("userID");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Profile.this, "clicked", Toast.LENGTH_SHORT).show();
                RequestBody formBody = new FormBody.Builder()
                        .add("userID", userID)
                        .build();
                requests.processRequest(Profile.this, "numOf", formBody, new RequestHandler() {
                    @Override
                    public void ProcessResponse(String response) throws JSONException {
                        ProcessJSON(response);

                    }
                });

            }
        });

    }


    public void ProcessJSON(String data) throws JSONException {
        JSONArray jsonArray =  new JSONArray(data);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        String s = jsonObject.getString("answers");
        Toast.makeText(getBaseContext(), data, Toast.LENGTH_SHORT).show();
        NumAnswers.setText("");
        NumQ.setText("");
    }
}