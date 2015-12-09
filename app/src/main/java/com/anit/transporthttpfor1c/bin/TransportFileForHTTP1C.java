package com.anit.transporthttpfor1c.bin;

import java.io.DataOutputStream;
import java.io.FileInputStream;
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
public class TransportFileForHTTP1C implements ITransportFileForHTTP1C{

    /**
     * Поток для оттправки
     */
    FileInputStream outStream;

    /**
     * Поток для получения
     */
    FileOutputStream inStream;

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


    public TransportFileForHTTP1C(FileInputStream outStream, FileOutputStream inStream, URL url, Map<String, String> requestProperty, String user, String password) {
        this.outStream = outStream;
        this.inStream  = inStream;
        this.url = url;
        this.requestProperty = requestProperty;
        this.user = user;
        this.password = password;
    }




    @Override
    public FileOutputStream ExchengeFile1C() throws IOException {

        HttpURLConnection connection = getConnection();

        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

        // Считывание файла в оперативную память и запись его в соединение
        sendStream(outputStream);



        // Получение ответа от сервера
        int serverResponseCode = connection.getResponseCode();



        // Считка ответа от сервера в зависимости от успеха
        if(serverResponseCode != 200) {
           throw new IOException("Ответ не 200");
        }


        connection.connect();




        return getStreamFromConnection(connection);
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
