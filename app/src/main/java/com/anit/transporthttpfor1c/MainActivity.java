package com.anit.transporthttpfor1c;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.anit.transporthttpfor1c.bin.Exchange1C;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 *Тест 1
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btExchange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btExchange =  (Button) findViewById(R.id.btExchange);
        btExchange.setOnClickListener(this);
    }


    void executeExchange(){


        /*

        Готовим файл

         */


        String nameOutFile = "outFileTo1c.json";
        String nameInFile  = "inFileTo1c.json";




        String data = "Это должны получить в 1С";


        FileOutputStream fos;

        try {
            fos = openFileOutput(nameOutFile,Context.MODE_PRIVATE); // открываем файл для записи
            fos.write(data.getBytes()); // записываем данные
            fos.close(); // закрываем файл
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }


        Exchange1C exchange = new Exchange1C();

        exchange.setPathOutFile(nameOutFile);
        exchange.setPathInFile(nameInFile);

        exchange.setUrl("http://10.0.2.2/exServer/hs/Exchange/");

        exchange.setUser("Гладких");
        exchange.setPassword("1234512345");


        Map<String, String> requestProperty = new HashMap<String, String>();
        requestProperty.put("file", "test.txt");
        requestProperty.put("android", "exit");

        exchange.setParamReqest(requestProperty);

        exchange.executeExchange();



        //Toast.makeText(this, getApplicationContext().getCacheDir().toString(), Toast.LENGTH_LONG).show();



    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btExchange:

                executeExchange();

                Toast.makeText(this,"Обмен выполнен",Toast.LENGTH_SHORT).show();

                break;

            default:
                break;

        }
    }






}
