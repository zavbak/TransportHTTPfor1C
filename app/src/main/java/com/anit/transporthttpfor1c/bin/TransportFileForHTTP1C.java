package com.anit.transporthttpfor1c.bin;

import android.content.Context;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Александр on 09.12.2015.
 */
public class TransportFileForHTTP1C implements ITransportFileForHTTP1C{



    /**
     * Путь к сервису
     */
    URL url;

    /**
     * Пользователь 1С
     */
    String user;

    /**
     * Пароль
     */
    String pass;

    /**
     * Путь к файлу в 1С
     */
    String pathFileOut;

    /**
     * Путь к файлу ответа
     */
    String pathFileIn;


    /**
     * Файл для передачи
     */
    File fileOut;


    public TransportFileForHTTP1C(URL url, String user, String pass, String pathFileOut,String pathFileIn ) throws IOException {
        this.url  = url;
        this.user = user;
        this.pass = pass;
        this.pathFileOut = pathFileOut;

        this.pathFileIn  = pathFileIn;

        /*
        Сначала надо проверить наличие файла для передачи
         */
        fileOut = new File(pathFileOut);
        if (fileOut.exists() && fileOut.isFile()) {
            throw new IOException("no file out!");
        }




    }

    @Override
    public String ExchengeFile1C() throws IOException {

        /**
         * Соиденение
         */
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();;


        /**
         * Устанавливаем параметры соеденения
         */
        setParamconnection(connection);



        // Создание потока для записи в соединение
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());


        /*

        Выгружаем файл в поток

         */

        //Отправляем файл в соиденение
        sendFileInOutStream(outputStream);


        // Получение ответа от сервера
        int serverResponseCode = connection.getResponseCode();



        // Считка ответа от сервера в зависимости от успеха
        if(serverResponseCode != 200) {
            return null;  //Вообще надо исключение
        }


        //Открываем седенение
        connection.connect();










        return null;
    }




    private InputStream readStream(HttpURLConnection connection) throws IOException {


        File file = null;
        FileOutputStream fos = null;
        InputStream inputStream;
        int totalSize;
        int downloadedSize;
        byte[] buffer;
        int bufferLength;

        String fileName = "inFile.jpg";


        //file = File.createTempFile("Mustachify", "download");
        fos = context.openFileOutput(fileName, Context.MODE_PRIVATE); // открываем файл для записи

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




    }




    /**
     * Устанавливаем параметры соиденения
     * @param connection
     * @throws ProtocolException
     */
    private void setParamconnection(HttpURLConnection connection) throws ProtocolException {


        // Разрешение ввода соединению
        connection.setDoInput(true);
        // Разрешение вывода соединению
        connection.setDoOutput(true);
        // Отключение кеширования
        connection.setUseCaches(false);

        // Задание запросу типа POST
        connection.setRequestMethod("POST");

        // Задание необходимых свойств запросу
        connection.setRequestProperty("File", fileOut.getName()); //Имя надо только латиницей

    }


    /**
     *
     * Записываем файл в соиденение
     * @return
     */
    private void sendFileInOutStream(DataOutputStream outputStream) throws IOException {

        // Переменные для считывания файла в оперативную память
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1*1024*1024;

        // Поток для считывания файла в оперативную память
        FileInputStream fileInputStream = new FileInputStream(fileOut);

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
