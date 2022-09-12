package com.example.appproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class SignUp extends AppCompatActivity {
    EditText name;
    EditText surname;
    EditText username;
    EditText password;
    Button btnSignUp;
    ProcessRequests requests=new ProcessRequests("https://lamp.ms.wits.ac.za/home/s2345106/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name = findViewById(R.id.edtname);
        surname = findViewById(R.id.edtsurname);
        password = findViewById(R.id.edtpassword);
        username = findViewById(R.id.edtusername);
        btnSignUp = findViewById(R.id.btnsign_up);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestBody formBody = new FormBody.Builder()
                        .add("password",password.getText().toString())
                        .add("userID",username.getText().toString())
                        .add("Name",name.getText().toString())
                        .add("Surname",surname.getText().toString())
                        .build();
                requests.processRequest(SignUp.this, "signup", formBody, new RequestHandler() {
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

        String userID =userID =jsonObject.getString("userID");

        if (userID.length()>0) {
            //user name was not taken
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SignUp.this, "successfully signed in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this, NavMenu.class);
                    intent.putExtra("userID",username.getText().toString());
                    startActivity(intent);
                    username.setText("");
                    password.setText("");

                }
            });
        }
        else{
            Toast.makeText(SignUp.this, "username taken", Toast.LENGTH_SHORT).show();
        }


    }
}