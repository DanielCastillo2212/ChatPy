package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnWrite = findViewById(R.id.btnWrite);

        btnWrite.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Chat.class);
            startActivity(intent);
        });

    }
}