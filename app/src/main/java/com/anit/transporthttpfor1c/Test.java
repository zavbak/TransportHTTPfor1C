package com.anit.transporthttpfor1c;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.anit.transporthttpfor1c.bin.Exchange1C;
import com.anit.transporthttpfor1c.bin.ITransportSteamForHTTP1C;
import com.anit.transporthttpfor1c.bin.TransportSteamForHTTP1C;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by 79900 on 06.12.2015.1
 */


public class Test extends AsyncTask<Void, Void, Void> {


    Exchange1C exchange;
    Context context;

    String message = "";

    public Test(Exchange1C exchange,Context context) {

        this.exchange = exchange;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... params) {


        Boolean exchangeOK =  exchange.executeExchange();



        if (exchangeOK){

            message = "Обмен прошел !";

        }else {

            message = "Проблемы с обменом!";

        }





        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();


        FileInputStream fis;
        String content = "";
        try {
            fis = new FileInputStream(exchange.getFrom1C());  // открываем файл для чтения

            byte[] input = new byte[fis.available()];
            while (fis.read(input) != -1) {
                content += new String(input);
            }
            fis.close();

            MainActivity mainActivity = (MainActivity) context;

            mainActivity.setOutText(content);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

}
