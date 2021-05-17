package com.example.qu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CreateQuestActivity extends AppCompatActivity {

    private OnNewItemAddedListener onNewItemAddedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quest);

        //onNewItemAddedListener = (OnNewItemAddedListener) this;
    }
}