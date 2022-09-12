package com.example.appproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class Questions extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    List<String[]> list;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    String TopicID="";
    String userID="";
    Button PostBtn;
    Switch byVote;
    public  String QnID="";
    SwipeRefreshLayout swipeRefreshLayout;
    ProcessRequests requests=new ProcessRequests("https://lamp.ms.wits.ac.za/home/s2345106/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);


        drawerLayout=(DrawerLayout)findViewById(R.id.draw_layout);
        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TopicID=getIntent().getStringExtra("TopicID");
        userID = getIntent().getStringExtra("userID");

        PostBtn = findViewById(R.id.PostBtn);
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
        PostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = new Post(TopicID,userID,"","QUESTION");
                post.show(getSupportFragmentManager(),"Post Question");

            }
        });
        byVote=findViewById(R.id.listByVotes);
        if(byVote.isChecked()){
            GetData("true");
        }
        else {
            GetData("false");
        }

        NavigationView nav =(NavigationView)findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id =item.getItemId();
                if(id==R.id.Home){
                    Intent intent = new Intent(Questions.this,NavMenu.class);
                    startActivity(intent);

                }
                if(id==R.id.myprofile){
                    Intent intent = new Intent(Questions.this,Profile.class);
                    intent.putExtra("userID",userID);
                    startActivity(intent);


                }
                return false;
            }
        });
    }

    public void GetData(String ByVote){
        RequestBody formBody = new FormBody.Builder()
                .add("TopicID", TopicID)
                .add("ByVote",ByVote)
                .build();
        requests.processRequest(Questions.this, "questions", formBody, new RequestHandler() {
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
          strings[3]=(jsonObject.getString("QuestionID"));
          QnID=strings[3];

          list.add(strings);
      }
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter= new MainAdapter(list,false, Questions.this,userID);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }


}