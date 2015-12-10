package com.anit.transporthttpfor1c;

import android.content.Context;
import android.os.AsyncTask;

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

    public Test(Exchange1C exchange) {
        this.exchange = exchange;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... params) {


        try {
            exchange.executeExchange();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

    }

}
