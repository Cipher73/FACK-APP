package com.example.appproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class Answers extends AppCompatActivity {

    List<String[]> list;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    String questionID="";
    String loggedUser="";
    Button btnPost;
    Switch byVote;
    SwipeRefreshLayout swipeRefreshLayout;
    ProcessRequests requests=new ProcessRequests("https://lamp.ms.wits.ac.za/home/s2345106/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);
        questionID = getIntent().getStringExtra("QuestionID");
        loggedUser=getIntent().getStringExtra("userID");


        btnPost = findViewById(R.id.PostBtn);
        GetData("false");

        swipeRefreshLayout = findViewById(R.id.SwipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(byVote.isChecked()){
                    GetData("true");
                }
                else {
                    GetData("false");
                }
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = new Post("",loggedUser,questionID,"ANSWER");
                post.show(getSupportFragmentManager(),"Post Answer");
            }
        });
        byVote=findViewById(R.id.listByVotes);
        if(byVote.isChecked()){
            GetData("true");
        }
        else {
            GetData("false");
        }

    }

    public void GetData(String ByVote){
        RequestBody formBody = new FormBody.Builder()
                .add("QuestionID", questionID)
                .add("ByVote",ByVote)
                .build();
        requests.processRequest(Answers.this, "answers", formBody, new RequestHandler() {
            @Override
            public void ProcessResponse(String response) throws JSONException {
                ProcessJSON(response);

            }
        });

    }

    public void ProcessJSON(String data) throws JSONException {
        JSONArray jsonArray = new JSONArray(data);
        list=new ArrayList<>();


        for(int i = 0 ; i<jsonArray.length();i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String[] strings = new String[4];
            strings[0]=(jsonObject.getString("Content"));
            strings[2]=(jsonObject.getString("NumOfVotes"));
            strings[1]=(jsonObject.getString("UserID"));
            strings[3]=(jsonObject.getString("AnswerID"));

            list.add(strings);
        }


        recyclerView=findViewById(R.id.answerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter= new MainAdapter(list,true,Answers.this,loggedUser);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
}