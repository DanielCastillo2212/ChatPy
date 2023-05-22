package com.example.chatbot;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.view.View;

import java.io.IOException;


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

                // Get data from user
                String userInput = etMensaje.getText().toString();
                agregarTextView(userInput, scrollMensaje);
                etMensaje.setText("");

                Handler handler =new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient();
                        String url = "https://api.openai.com/v1/chat/completions";

                        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
                        String requestBody = "\"model\": \"gpt-3.5-turbo\",\n" +
                                "    \"messages\": [{\"role\": \"assistant\", \"content\": \"" + userInput + "\"}]";

                        RequestBody body = RequestBody.create(requestBody, mediaType);

                        Request request = new Request.Builder()
                                .url(url)
                                .post(body)
                                .addHeader("Authorization", "Bearer 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")
                                .build();

                        try {
                            Response response = client.newCall(request).execute();

                            if (response.isSuccessful()) {
                                agregarTextView(response.body().string(), scrollMensaje);

                            } else {
                                agregarTextView("Error de Open IA", scrollMensaje);
                            }
                        } catch (IOException e) {
                            agregarTextView("Error de Input", scrollMensaje);
                        } catch (Exception e) {
                            agregarTextView(e.toString(), scrollMensaje);
                            Log.d("Chat", e.toString());
                        }
                    }
                });
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


    private String getResponse(String inputMessage) {

        final String[] responseRes = {""};


        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                String url = "https://api.openai.com/v1/chat/completions";

                // Construye el cuerpo de la solicitud con el mensaje del usuario
                MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
                String requestBody = "\"model\": \"gpt-3.5-turbo\",\n" +
                        "    \"messages\": [{\"role\": \"assistant\", \"content\": \"" + inputMessage + "\"}]";

                RequestBody body = RequestBody.create(requestBody, mediaType);

                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .addHeader("Authorization", "Bearer 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") // Reemplaza YOUR_API_KEY con tu clave de API de OpenAI
                        .build();

                try {
                    Response response = client.newCall(request).execute();

                    if (response.isSuccessful()) {
                        responseRes[0] = response.body().string();

                    } else {
                        responseRes[0] = "Error del servidor con Open IA";
                    }
                } catch (IOException e) {
                    responseRes[0] = "Error de input";
                } catch (Exception e) {
                    responseRes[0] = "Error desconocido";
                }
            }
        }).start();
        return  responseRes[0];
    }
}