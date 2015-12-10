package com.anit.transporthttpfor1c;

import android.content.Context;
import android.os.AsyncTask;

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


    private Context context;
    private String pathOut;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... params) {
        try {




            // Поток для считывания файла в оперативную память
            try {
                FileInputStream fis = new FileInputStream(new File(pathOut));

                FileOutputStream fos = context.openFileOutput("test.jpg", Context.MODE_PRIVATE);

                URL url = new URL("http://10.0.2.2/exServer/hs/Exchange/");

                Map<String, String> param = new HashMap<String, String>();

                param.put("file","myfile.jpg");
                param.put("time","10:45");

                String user = "Гладкиз";
                String pass = "123";



                /*
                ITransportSteamForHTTP1C transport = new TransportSteamForHTTP1C(fis,fos,url,param,user,pass);

                 fos = transport.ExchengeFile1C();

                 */


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            TimeUnit.SECONDS.sleep(2);
            IexHttpFilesOneS iexHttpFilesOneS = new ExHttpFilesOneS();
            //iexHttpFilesOneS.setURL("http://127.0.0.1/exServer/hs/test/");
            //iexHttpFilesOneS.setURL("http://10.0.2.2/exServer/hs/test/");
            iexHttpFilesOneS.setURL("http://10.0.2.2/exServer/hs/Exchange/");
            //iexHttpFilesOneS.setURL("http://localhost/exServer/hs/test/");
            //iexHttpFilesOneS.setURL("https://www.google.ru/");
            iexHttpFilesOneS.getOutputFile();



        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

    }

}
