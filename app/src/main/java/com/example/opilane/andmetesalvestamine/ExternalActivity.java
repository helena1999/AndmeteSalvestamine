package com.example.opilane.andmetesalvestamine;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExternalActivity extends AppCompatActivity {

    EditText tekst;
    Button btnSalvesta, btnLoe;
    private String failinimi = "Fail.txt";
    private String failikaust = "AndmeteSalvestus";
    File väline;
    String andmed = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external);

        tekst = findViewById(R.id.tekst);
        btnLoe = findViewById(R.id.btn_loe);
        btnSalvesta = findViewById(R.id.btn_salvesta);

        btnSalvesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(väline);
                    fileOutputStream.write(tekst.getText().toString().getBytes());
                    fileOutputStream.close();
                }
                catch (IOException viga){
                    viga.printStackTrace();
                }
                tekst.setText("");
                teade("Fail.txt salvestati välisele mälule.");

            }
        });
        btnLoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(väline);
                    DataInputStream dataInputStream = new DataInputStream(fileInputStream);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
                    String stringLine;
                    while ((stringLine = bufferedReader.readLine())!=null){
                        andmed = andmed + stringLine;
                    }
                    dataInputStream.close();
                }
                catch (Exception viga){
                    viga.printStackTrace();
                }
                tekst.setText(andmed);
                teade("Fail.txt andmed saadi kätte.");

            }
        });
        if (!isExternalStorageAvailable()|| isExternalStorageReadOnly()){
            btnSalvesta.setEnabled(false);
        }
        else {väline = new File(getExternalFilesDir(failikaust), failinimi);}


    }
    private static boolean isExternalStorageReadOnly(){
        String externalStorageSate = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(externalStorageSate)){
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String externalStorageSate = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(externalStorageSate)){
            return true;
        }
        return false;
    }


    public void teade(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
