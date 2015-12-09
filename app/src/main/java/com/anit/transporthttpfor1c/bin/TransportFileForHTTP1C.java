package com.anit.transporthttpfor1c.bin;

import android.content.Context;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Александр on 09.12.2015.
 */
public class TransportFileForHTTP1C implements ITransportFileForHTTP1C{


    @Override
    public String ExchengeFile1C(String pathFile, URL url, String user, String pass) throws IOException {


        /*
        Сначала надо проверить наличие файла для передачи
         */

        File fileOut = new File(pathFile);
        if (fileOut.exists() && fileOut.isFile()) {
            return null;  //Вообще надо исключение
        }


        // Создание соединения для отправки файла
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Разрешение ввода соединению
        connection.setDoInput(true);
        // Разрешение вывода соединению
        connection.setDoOutput(true);
        // Отключение кеширования
        connection.setUseCaches(false);

        // Задание запросу типа POST
        connection.setRequestMethod("POST");

        // Задание необходимых свойств запросу
        connection.setRequestProperty("FileName", fileOut.getName()); //Имя надо только латиницей

        // Создание потока для записи в соединение
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());



        /*

        Пошла отправка

         */

        //Отправляем файл в соиденение
        sendFile(pathFile,outputStream);



















        return null;
    }


    /**
     *
     * Записываем файл в соиденение
     * @param filePath
     * @return
     */
    private void sendFile(String filePath,DataOutputStream outputStream) throws IOException {

        // Переменные для считывания файла в оперативную память
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1*1024*1024;

        // Поток для считывания файла в оперативную память
        FileInputStream fileInputStream = new FileInputStream(new File(filePath));

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




    }




}
