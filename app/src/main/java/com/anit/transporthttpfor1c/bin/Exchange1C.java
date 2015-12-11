package com.anit.transporthttpfor1c.bin;

import android.content.Context;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * Created by Александр on 10.12.2015.
 */
public class Exchange1C {


    /**
     * Файл на отдачу
     */
    File to1C;

    /**
     * Файл на прием
     */
    File from1C;


    /**
     * Адрес HTTP сервиса
     */
    String url;

    /**
     * Параметры в забросе
     */
    Map<String, String> requestProperty;

    /**
     * Авторизация пользователь в 1С
     */
    String user;

    /**
     * Пароль 1С
     */
    String password;


    String METOD_POST = "POST";


    public void setRequestProperty(Map<String, String> requestProperty) {
        this.requestProperty = requestProperty;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFrom1C(File from1C) {
        this.from1C = from1C;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }


    public void setTo1C(File to1C) {
        this.to1C = to1C;
    }

    public File getFrom1C() {
        return from1C;
    }


    /**
     * Запускаем обмен
     * @return
     */
    public Boolean executeExchange() {

        DataOutputStream outputStream = null;


        HttpURLConnection connection = null;


        try {
            URL uploadUrl = new URL(url);

            // Создание соединения для отправки файла
            connection = (HttpURLConnection) uploadUrl.openConnection();

            // Разрешение ввода соединению
            connection.setDoInput(true);

            //connection.setReadTimeout(3*1000);

            // Разрешение вывода соединению
            connection.setDoOutput(true);
            // Отключение кеширования
            connection.setUseCaches(false);

            // Задание запросу типа POST
            connection.setRequestMethod(METOD_POST);


            Set<Map.Entry<String, String>> set = requestProperty.entrySet();
            for (Map.Entry<String, String> pr : set) {

                connection.setRequestProperty(pr.getKey(), pr.getValue());

            }

            // Создание потока для записи в соединение
            outputStream = new DataOutputStream(connection.getOutputStream());

        } catch (IOException e) {

            if (outputStream != null) {

                try {
                    outputStream.close();

                } catch (IOException e1) {
                    e1.printStackTrace();

                }

                e.printStackTrace();


            }

            return false;

        }


        try {


            //Отправляем файл в поток
            writeFileinStream(outputStream);


        } catch (IOException e) {

            e.printStackTrace();

            connection.disconnect();

            return false;

        } finally {

            try {

                outputStream.close();

            } catch (IOException e) {

                e.printStackTrace();

                connection.disconnect();

                return false;
            }
        }



        /*


        СМОТРИМ ОТВЕТ ОТ СЕРВЕРА

         */


        int responseCode = HttpURLConnection.HTTP_NOT_FOUND;

        try {
            responseCode = connection.getResponseCode();

        } catch (IOException e) {
            connection.disconnect();
            e.printStackTrace();

            return false;
        }


        if (responseCode != HttpURLConnection.HTTP_OK) {

            connection.disconnect();
            return false;
        }




        /*

        ПИШЕМ ФАЙЛ

         */

        InputStream inputStream = null;

        try {
            inputStream = connection.getInputStream();

            //Размер потока
            int totalSize = connection.getContentLength();

            readStreamInFile(inputStream);

        } catch (IOException e) {

            e.printStackTrace();
            connection.disconnect();
            return false;


        } finally {


            try {

                inputStream.close();

            } catch (IOException e) {

                e.printStackTrace();

                connection.disconnect();

                return false;
            }
        }


        return true;
    }


    /**
     * Читаем входящий поток в файл
     *
     * @param inputStream
     * @return
     */

    void readStreamInFile(InputStream inputStream) throws IOException {


        FileOutputStream fos = null;


        try {

            fos = new FileOutputStream(from1C);

            int downloadedSize = 0;
            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            // читаем со входа и пишем в выход,
            // с каждой итерацией публикуем прогресс
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fos.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
            }

        } catch (IOException e) {

            e.printStackTrace();

            throw new IOException();

        } finally {

            try {
                fos.close();

            } catch (IOException e) {

                e.printStackTrace();
                throw new IOException();
            }
        }


    }


    /**
     * Файл в поток
     *
     * @param outputStream
     */
    void writeFileinStream(DataOutputStream outputStream) throws IOException {


        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

        // Поток для считывания файла в оперативную память
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(to1C);

            bytesAvailable = fis.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // Считывание файла в оперативную память и запись его в соединение
            bytesRead = fis.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                outputStream.write(buffer, 0, bufferSize);
                bytesAvailable = fis.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fis.read(buffer, 0, bufferSize);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();


        } finally {

            try {

                fis.close();

            } catch (IOException e) {

                e.printStackTrace();
                throw new IOException();
            }


        }


    }


}
