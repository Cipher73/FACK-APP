package com.example.appproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class Post extends DialogFragment {
    EditText Content;
    Button PostBtn;
    String TopicID;
    String userID;
    String Type;
    String QnID;
    ProcessRequests requests = new ProcessRequests("https://lamp.ms.wits.ac.za/home/s2345106/");

    public Post(String TopicID, String userID,String QnID,String Type) {
        this.Type=Type;
        this.TopicID = TopicID;
        this.userID = userID;
        this.QnID=QnID;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_post, container, false);
        PostBtn = view.findViewById(R.id.QnBtn);
        Content = view.findViewById(R.id.Content);
        if(Type.equals("ANSWER")) {
            Content.setHint("Type your answer");
        }
        if(Type.equals("QUESTION")) {
            Content.setHint("Type your question");
        }
        PostBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (Type.equals("QUESTION")) {
                    RequestBody formBody = new FormBody.Builder()
                            .add("TopicID", TopicID)
                            .add("Content", Content.getText().toString())
                            .add("userID", userID)
                            .build();
                    requests.processRequest(getActivity(), "post", formBody, new RequestHandler() {
                        @Override
                        public void ProcessResponse(String response) throws JSONException {
                            ProcessJSON(response);

                        }
                    });

                }
                else if (Type.equals("ANSWER")){
                    RequestBody formBody = new FormBody.Builder()
                            .add("questionID",QnID)
                            .add("Content", Content.getText().toString())
                            .add("userID", userID)
                            .build();
                    requests.processRequest(getActivity(), "postAnswer", formBody, new RequestHandler() {
                        @Override
                        public void ProcessResponse(String response) throws JSONException {
                            ProcessJSON(response);

                        }
                    });

                }
            }
        });
        return view;

    }

    public void ProcessJSON(String data) throws JSONException {

        Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();
        Content.setText("");
    }
}
