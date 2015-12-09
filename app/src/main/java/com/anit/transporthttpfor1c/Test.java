package com.anit.transporthttpfor1c;

import android.os.AsyncTask;

import java.util.concurrent.TimeUnit;

/**
 * Created by 79900 on 06.12.2015.1
 */


public class Test extends AsyncTask<Void, Void, Void> {


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
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
