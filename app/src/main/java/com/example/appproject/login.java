package com.example.appproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class login extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    Button btnlogin;
    EditText edtusername;
    EditText edtpassword;
    String username, password;
    public TextView textView;

    ProcessRequests requests=new ProcessRequests("https://lamp.ms.wits.ac.za/home/s2345106/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = password = "";
        btnlogin = findViewById(R.id.btnlogin);
        edtusername = findViewById(R.id.edtusername);
        edtpassword = findViewById(R.id.edtpassword);
        textView = findViewById(R.id.textView);


        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.green_colour));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this,SignUp.class);
                startActivity(intent);
            }
        });


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBody formBody = new FormBody.Builder()
                        .add("userID",edtusername.getText().toString())
                        .add("password",edtpassword.getText().toString())
                        .build();
                requests.processRequest(login.this, "login", formBody, new RequestHandler() {
                    @Override
                    public void ProcessResponse(String response) throws JSONException {
                        ProcessJSON(response);
                    }
                });
            }
        });

    }


    public void ProcessJSON(String data) throws JSONException {
        JSONArray jsonArray=new JSONArray(data);

            JSONObject jsonObject = jsonArray.getJSONObject(0);

           String userID = "";
           userID =jsonObject.getString("userID");
           String pass ="";
           pass =jsonObject.getString("password");

        if (userID.length() > 0 && pass.length() > 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(login.this, "successfully logged in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(login.this, NavMenu.class);
                    intent.putExtra("userID",edtusername.getText().toString());
                    startActivity(intent);
                    edtusername.setText("");
                    edtpassword.setText("");

                }
            });



        }
        else{
            Toast.makeText(login.this, jsonObject.getString("failed"), Toast.LENGTH_SHORT).show();

        }
    }


}