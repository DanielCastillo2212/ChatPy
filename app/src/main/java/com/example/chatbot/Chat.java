package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import android.view.View;


public class Chat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ImageButton btnBack = findViewById(R.id.btnBack);
        ImageButton btnAgregar = findViewById(R.id.btnAgregar);

        EditText etMensaje = findViewById(R.id.etMensaje);
        ScrollView scrollMensaje = findViewById(R.id.scrollMensaje);

        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(Chat.this, MainActivity.class);
            startActivity(intent);
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarTextView(etMensaje.getText().toString(), scrollMensaje);
                etMensaje.setText("");
            }
        });

    }

    private void agregarTextView(String texto, ScrollView scrollView) {
        TextView textView = new TextView(this);
        textView.setText(texto);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(textView);

        LinearLayout scrollLayout = scrollView.findViewById(R.id.scrollLayout);
        scrollLayout.addView(linearLayout);
    }



}