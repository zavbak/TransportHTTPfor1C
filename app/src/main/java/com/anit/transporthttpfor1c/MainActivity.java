package com.anit.transporthttpfor1c;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anit.transporthttpfor1c.bin.Exchange1C;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *Тест 1
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btExchange,btRead;
    EditText edOutText;

    TextView tvInText;

    String nameInFile  = "inFileTo1c.json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btExchange =  (Button) findViewById(R.id.btExchange);
        btRead     =  (Button) findViewById(R.id.btRead);
        edOutText  =  (EditText) findViewById(R.id.edOutText);
        tvInText   =  (TextView) findViewById(R.id.tvInText);

        btExchange.setOnClickListener(this);

        btRead.setOnClickListener(this);
    }


    public void setOutText(String str){

        tvInText.setText(str);

    }


    void executeExchange(){


        /*

        Готовим файл

         */


        String nameOutFile = "to1C";
        String nameInFile  = "from1C";
        String data = edOutText.getText().toString();




        FileOutputStream fos;

        File fileTo1C = null;
        File fileFrom1C = null;

        try {

            fileTo1C = File.createTempFile(nameOutFile, null, this.getExternalCacheDir());

            fos = new FileOutputStream(fileTo1C); // открываем файл для записи

            fos.write(data.getBytes()); // записываем данные
            fos.close(); // закрываем файл




            fileFrom1C = File.createTempFile(nameInFile, null, this.getExternalCacheDir());



        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }


        Map<String, String> requestProperty = new HashMap<String, String>();
        requestProperty.put("file", "test.txt");
        requestProperty.put("android", "exit");




        Exchange1C exchange = new Exchange1C();

        exchange.setTo1C(fileTo1C);

        exchange.setFrom1C(fileFrom1C);

        exchange.setUrl("http://10.0.2.2/exServer/hs/Exchange/");
        //exchange.setUrl("http://172.31.255.41/exServer/hs/Exchange");

        exchange.setUser("Гладких");
        exchange.setPassword("1234512345");

        exchange.setRequestProperty(requestProperty);


        Test test = new Test(exchange,this);
        test.execute();



        //Toast.makeText(this, getApplicationContext().getCacheDir().toString(), Toast.LENGTH_LONG).show();



    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btExchange:

                executeExchange();

                Toast.makeText(this,"Обмен запустили!",Toast.LENGTH_SHORT).show();

                break;
            case R.id.btRead:

                FileInputStream fis;
                String content = "";
                try {
                    fis = openFileInput(nameInFile);  // открываем файл для чтения
                    byte[] input = new byte[fis.available()];
                    while (fis.read(input) != -1) {
                        content += new String(input);
                    }
                    fis.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                tvInText.setText(content);


                String state = Environment.getExternalStorageState();
                Toast.makeText(this,state,Toast.LENGTH_SHORT).show();


                File file;
                try
                {
                    String fileName = "from1c";
                    file = File.createTempFile(fileName, null, this.getExternalCacheDir());

                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(fileName.getBytes());
                    fos.close();
                }
                catch (IOException e)
                {

                }






            default:
                break;

        }
    }






}
