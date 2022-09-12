package com.example.appproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

public class NavMenu extends AppCompatActivity {
    DrawerLayout drawerLayout;
    CardView javaCard;
    CardView oopCard;
    CardView phpCard;
    CardView memoryCard;
    CardView machineCard;
    CardView databaseCard;
    CardView mysqlCard;
    CardView androidCard;
    String userID;
    private ActionBarDrawerToggle drawerToggle;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_menu);
        drawerLayout=(DrawerLayout)findViewById(R.id.draw_layout);
        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userID = getIntent().getStringExtra("userID");


        javaCard =findViewById(R.id.javaCard);
        oopCard=findViewById(R.id.oopCard);
        phpCard=findViewById(R.id.phpCard);
        machineCard=findViewById(R.id.machineCard);
        memoryCard=findViewById(R.id.memoryCard);
        androidCard=findViewById(R.id.androidCard);
        mysqlCard=findViewById(R.id.mysqlCard);
        databaseCard=findViewById(R.id.databaseCard);



        //JAVA CARD


        javaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent= new Intent(NavMenu.this, Questions.class);
               intent.putExtra("TopicID","Java");
               intent.putExtra("userID",userID);
               startActivity(intent);
            }
        });

        // OOP CARD

        oopCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(NavMenu.this, Questions.class);
                intent.putExtra("TopicID","Object Oriented Programing");
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });

        phpCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(NavMenu.this, Questions.class);
                intent.putExtra("TopicID","PHP");
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });

        memoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(NavMenu.this, Questions.class);
                intent.putExtra("TopicID","Memory Management");
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });

        mysqlCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(NavMenu.this, Questions.class);
                intent.putExtra("TopicID","MySql");
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });

        machineCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(NavMenu.this, Questions.class);
                intent.putExtra("TopicID","Machine Learning");
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });

        androidCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(NavMenu.this, Questions.class);
                intent.putExtra("TopicID","Android Studio");
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });

        databaseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(NavMenu.this, Questions.class);
                intent.putExtra("TopicID","Database");
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });

  //navigation view things
        NavigationView nav =(NavigationView)findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id =item.getItemId();
                if(id==R.id.Home){
                    Intent intent = new Intent(NavMenu.this,NavMenu.class);
                    startActivity(intent);

                }
                if(id==R.id.myprofile){
                    Intent intent = new Intent(NavMenu.this,Profile.class);
                    intent.putExtra("userID",userID);
                    startActivity(intent);


                }
               return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }




}