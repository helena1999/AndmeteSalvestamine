package com.example.opilane.andmetesalvestamine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class InternalActivity extends AppCompatActivity {

    EditText tekst;
    Button loe, salvesta, btnEdasi;
    static final int READ_BLOCK_SIZE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal);

        tekst = findViewById(R.id.tekst);
        loe = findViewById(R.id.btn_loe);
        salvesta = findViewById(R.id.btn_salvesta);
        btnEdasi = findViewById(R.id.btn_edasi);

        salvesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    FileOutputStream fileout = openFileOutput("tekstifail.txt", MODE_PRIVATE);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileout);
                    outputStreamWriter.write(tekst.getText().toString());
                    outputStreamWriter.close();
                    teade("Fail salvestati edukalt");
                    tekst.setText("");

                } catch (Exception viga) {
                    viga.printStackTrace();
                }
            }
        });
        loe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream fileIn = openFileInput("tekstifail.txt");
                    InputStreamReader InputRead = new InputStreamReader(fileIn);
                    char[] inputBuffer = new char[READ_BLOCK_SIZE];
                    String s = "";
                    int charRead;
                    while ((charRead = InputRead.read(inputBuffer)) > 0) {
                        String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                        s = s + readstring;
                    }
                    InputRead.close();
                    tekst.setText(s);
                } catch (Exception viga) {
                    viga.printStackTrace();
                }
            }
        });
        btnEdasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edasi = new Intent(getApplicationContext(), ExternalActivity.class);
                startActivity(edasi);
            }
        });
    }
    public void teade(String teade){
        Toast.makeText(this, teade, Toast.LENGTH_SHORT).show();
    }
}
