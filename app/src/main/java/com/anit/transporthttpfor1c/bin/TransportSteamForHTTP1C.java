package com.anit.transporthttpfor1c.bin;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * Created by Александр on 09.12.2015.
 */
public class TransportSteamForHTTP1C implements ITransportSteamForHTTP1C {

    /**
     * Поток для оттправки
     */
    FileInputStream outStream;

    String pathOutFile;

    /**
     * Поток для получения
     */
    FileOutputStream inStream;

    String pathInFile;

    /**
     * Путь к сервису
     */
    URL url;

    /**
     * Свойства запроса
     */
    Map<String, String> requestProperty;

    /**
     * Логин пароль
     */
    String user, password;


    public TransportSteamForHTTP1C(String pathOutFile, FileOutputStream inStream, URL url, Map<String, String> requestProperty, String user, String password) throws FileNotFoundException {


        outStream = null;

        this.inStream  = inStream;
        this.url = url;
        this.requestProperty = requestProperty;
        this.user = user;
        this.password = password;

    }




    @Override
    public File ExchengeFile1C() {

        File fileOut = null;

        HttpURLConnection connection = null;


        try {
            outStream = new FileInputStream(new File(pathOutFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }


        try {
            connection = getConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


        DataOutputStream outputStream = null;
        try {
            outputStream = new DataOutputStream(connection.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();


        }

        /*
        // Считывание файла в оперативную память и запись его в соединение
        sendStream(outputStream);



        // Получение ответа от сервера
        int serverResponseCode = connection.getResponseCode();



        // Считка ответа от сервера в зависимости от успеха
        if(serverResponseCode == 200) {
            connection.connect();
            fos = getStreamFromConnection(connection);

        }

        outStream.close();
        connection.disconnect();

        */
        return new File("");
    }


    /**
     * Из соеденения в поток
     * @param connection
     * @return
     * @throws IOException
     */
    private FileOutputStream getStreamFromConnection(HttpURLConnection connection) throws IOException {

        InputStream inputStream = connection.getInputStream();

        int totalSize;
        int downloadedSize;
        byte[] buffer;
        int bufferLength;



        totalSize = connection.getContentLength();
        downloadedSize = 0;

        buffer = new byte[1024];
        bufferLength = 0;

        // читаем со входа и пишем в выход,
        // с каждой итерацией публикуем прогресс
        while ((bufferLength = inputStream.read(buffer)) > 0) {
            inStream.write(buffer, 0, bufferLength);
            downloadedSize += bufferLength;

        }


        return inStream;
    }

    /**
     * Отправляем поток
     * @param outputStream
     * @throws IOException
     */
    private void sendStream(DataOutputStream outputStream) throws IOException {


        // Переменные для считывания файла в оперативную память
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1*1024*1024;

        bytesAvailable = outStream.available();
        bufferSize = Math.min(bytesAvailable, maxBufferSize);
        buffer = new byte[bufferSize];

        // Считывание файла в оперативную память и запись его в соединение
        bytesRead = outStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {
            outputStream.write(buffer, 0, bufferSize);
            bytesAvailable = outStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = outStream.read(buffer, 0, bufferSize);
        }

    }


    /**
     * Заполняем параметры соеденения
     * @return
     * @throws IOException
     */
    private HttpURLConnection getConnection() throws IOException {

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



        Set<Map.Entry<String, String>> set = requestProperty.entrySet();
        for (Map.Entry<String, String> pr : set) {

            connection.setRequestProperty(pr.getKey(), pr.getValue());

        }





        return connection;


    }

}
