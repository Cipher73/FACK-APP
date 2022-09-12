package com.example.appproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{
    List<String[]> dataList;
    List<CardView> cardList = new ArrayList<>();
    boolean aBoolean;
    Activity a;
    String LoggedUser;
    public MainAdapter(List<String[]>list,boolean answer,Activity activity,String user) {
        dataList=list;
        aBoolean=answer;
        a=activity;
        LoggedUser=user;

    }

    @NonNull
    @NotNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MainAdapter.ViewHolder holder, int position) {

        holder.content.setText(dataList.get(position)[0]);
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(a.getApplicationContext(),R.anim.animation_file));
        if(!cardList.contains(holder.cardView)){
            cardList.add(holder.cardView);
        }

        if(aBoolean==false) {
            holder.vote.setText("Votes: " + dataList.get(position)[2]);
            holder.user.setText("By: " + dataList.get(position)[1]);

            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v) {
                    //Answers answers = new Answers(dataList.get(position)[3]);
                    Context context=v.getContext();
                    Intent intent = new Intent(context,Answers.class);
                    intent.putExtra("userID",LoggedUser);
                    intent.putExtra("QuestionID",dataList.get(position)[3]);
                    context.startActivity(intent);
                }

            });
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProcessCards processCards = new ProcessCards("QUESTION",LoggedUser,dataList.get(position)[3],a);
                    processCards.GetData();
                }
            });
        }
        else {
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProcessCards processCards = new ProcessCards("ANSWERS",LoggedUser,dataList.get(position)[3],a);
                    processCards.GetData();
                }
            });
            holder.vote.setText("Votes: "+dataList.get(position)[2]);
            holder.user.setText("By: "+dataList.get(position)[1]);

        }
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView content ;
        public TextView user ;
        public TextView vote ;
        public Button button;
        public ImageButton imageButton;
        public CardView cardView;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            cardView=itemView.findViewById(R.id.cardView);
            button = itemView.findViewById(R.id.btnAnswers);
            user=itemView.findViewById(R.id.user);
            vote=itemView.findViewById(R.id.votes);
            content=itemView.findViewById(R.id.question);
            imageButton=itemView.findViewById(R.id.imageButton);

            if(aBoolean==false){
            button.setVisibility(View.VISIBLE);


            }
            else {
                button.setVisibility(View.GONE);



            }
        }
    }
}
