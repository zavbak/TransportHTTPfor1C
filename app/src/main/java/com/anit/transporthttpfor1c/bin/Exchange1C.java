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

    Context context;


    /**
     * Путь к файлу для отправки
     */
    String pathOutFile;

    /**
     * Путь к файлу на получение
     */
    String pathInFile;

    /**
     * Адрес HTTP сервиса
     */
    String url;

    /**
     * Параметры в забросе
     */
    Map<String,String> requestProperty;

    /**
     * Авторизация пользователь в 1С
     */
    String user;

    /**
     * Пароль 1С
     */
    String password;


    public void setRequestProperty(Map<String, String> requestProperty) {
        this.requestProperty = requestProperty;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPathOutFile(String pathOutFile) {
        this.pathOutFile = pathOutFile;
    }

    public void setPathInFile(String pathInFile) {
        this.pathInFile = pathInFile;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }


    public void setContext(Context context) {
        this.context = context;
    }

    public Boolean executeExchange() throws IOException {

        // Переменные для считывания файла в оперативную память
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1*1024*1024;

        URL uploadUrl = new URL(url);

        // Создание соединения для отправки файла
        HttpURLConnection connection = (HttpURLConnection) uploadUrl.openConnection();

        // Разрешение ввода соединению
        connection.setDoInput(true);
        // Разрешение вывода соединению
        connection.setDoOutput(true);
        // Отключение кеширования
        connection.setUseCaches(false);

        // Задание запросу типа POST
        connection.setRequestMethod("POST");


        Set<Map.Entry<String, String>> set = requestProperty.entrySet();
        for (Map.Entry<String, String> pr : set) {

            connection.setRequestProperty(pr.getKey(), pr.getValue());

        }


        // Создание потока для записи в соединение
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());


        // Поток для считывания файла в оперативную память
        FileInputStream fileInputStream = new FileInputStream(new File(pathOutFile));

        bytesAvailable = fileInputStream.available();
        bufferSize = Math.min(bytesAvailable, maxBufferSize);
        buffer = new byte[bufferSize];

        // Считывание файла в оперативную память и запись его в соединение
        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {
            outputStream.write(buffer, 0, bufferSize);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }

        int serverResponseCode = connection.getResponseCode();



        // Считка ответа от сервера в зависимости от успеха
        if(serverResponseCode == 200) {
            //result = readStream(connection.getInputStream());
        } else {
            //result = readStream(connection.getErrorStream());
        }



        /*

        Начинаем приневать файл

         */


        File file = null;
        FileOutputStream fos = null;
        InputStream inputStream;
        int totalSize;
        int downloadedSize;
        int bufferLength;




        //file = File.createTempFile("Mustachify", "download");
        fos = context.openFileOutput(pathInFile, Context.MODE_PRIVATE); // открываем файл для записи

        //fos = new FileOutputStream(file);
        inputStream = connection.getInputStream();

        totalSize = connection.getContentLength();

        downloadedSize = 0;
        buffer = new byte[1024];
        bufferLength = 0;

        // читаем со входа и пишем в выход,
        // с каждой итерацией публикуем прогресс
        while ((bufferLength = inputStream.read(buffer)) > 0) {
            fos.write(buffer, 0, bufferLength);
            downloadedSize += bufferLength;
            //publishProgress(downloadedSize, totalSize);
        }



        // Закрытие соединений и потоков
        fileInputStream.close();
        outputStream.flush();
        outputStream.close();


        return true;
    }



}
