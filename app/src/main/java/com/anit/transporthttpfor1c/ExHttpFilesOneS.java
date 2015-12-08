package com.anit.transporthttpfor1c;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Сам объект для обмена
 */
public class ExHttpFilesOneS implements IexHttpFilesOneS {

    /**
     * Имя пользователя
     */
    private String user = null;

    /**
     * Пароль
     */
    private String pass = null;

    /**
     * URL
     */
    private String targetURL = null;

    /**
     * Путь к файлу отправки
     */
    private String strFile = null;



    @Override
    public void setUser(String user) {
       this.user = user;
    }

    @Override
    public void setPassword(String pass) {
        this.pass = pass;
    }

    @Override
    public void setURL(String targetURL) {
        this.targetURL = targetURL;

    }

    @Override
    public void setFileOut(String strFile) {

        this.strFile = strFile;
    }

    @Override
    public String getOutputFile() {

        return null;
    }


    public String getOutputFile1() {


        InputStream inputstream = null;
        String data = "";
        try {
            URL url = new URL(targetURL);

            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();

            connection.setReadTimeout(100000);
            connection.setConnectTimeout(100000);
            connection.setRequestMethod("POST");
            //connection.setRequestMethod("POST");
            connection.setInstanceFollowRedirects(true);
            connection.setUseCaches(false);
            connection.setDoInput(true);




            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) { // 200 OK

                inputstream = connection.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                int read = 0;
                while ((read = inputstream.read()) != -1) {
                    bos.write(read);
                }
                byte[] result = bos.toByteArray();
                bos.close();

                data = new String(result);

            } else {
                data = connection.getResponseMessage() + " . Error Code : " + responseCode;
            }





            connection.disconnect();
            //return data;



        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (inputstream != null) {
                try {
                    inputstream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return data;




    }
}
