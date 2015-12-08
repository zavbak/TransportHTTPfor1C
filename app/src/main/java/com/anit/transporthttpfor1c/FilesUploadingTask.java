package com.anit.transporthttpfor1c;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Загружает файл на сервер
 */
public class FilesUploadingTask extends AsyncTask<Void, Void, String> {

    // Конец строки
    private String lineEnd = "\r\n";


    // Два тире
    private String twoHyphens = "--";


    // Разделитель
    private String boundary =  "----WebKitFormBoundary9xFB2hiUhzqbBQ4M";

    // Переменные для считывания файла в оперативную память
    private int bytesRead, bytesAvailable, bufferSize;
    private byte[] buffer;
    private int maxBufferSize = 1*1024*1024;

    // Путь к файлу в памяти устройства
    private String filePath;

    private  Context context;

    // Адрес метода api для загрузки файла на сервер
    public static final String API_FILES_UPLOADING_PATH = "127.0.0.1/upload";

    // Ключ, под которым файл передается на сервер
    public static final String FORM_FILE_NAME = "file1";

    public FilesUploadingTask(String filePath,Context context) {
        this.filePath = filePath;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        // Результат выполнения запроса, полученный от сервера
        String result = null;

        try {
            // Создание ссылки для отправки файла
            URL uploadUrl = new URL("http://10.0.2.2/exServer/hs/Exchange/");



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

            // Задание необходимых свойств запросу
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("FileName", "Мой файл"); //Карявки
            //connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

            // Создание потока для записи в соединение
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

            // Формирование multipart контента

            /*

            // Начало контента
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            // Заголовок элемента формы
            outputStream.writeBytes("Content-Disposition: form-data; name=\"" + FORM_FILE_NAME + "\"; filename=\"" + filePath + "\"" + lineEnd);


            // Тип данных элемента формы
            outputStream.writeBytes("Content-Type: image/jpeg" + lineEnd);

            // Конец заголовка
            outputStream.writeBytes(lineEnd);

            */

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


            /*
            // Конец элемента формы
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            */

            // Получение ответа от сервера
            int serverResponseCode = connection.getResponseCode();



            // Считка ответа от сервера в зависимости от успеха
            if(serverResponseCode == 200) {
                //result = readStream(connection.getInputStream());
            } else {
                //result = readStream(connection.getErrorStream());
            }



            connection.connect();

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



            // Закрытие соединений и потоков
            fileInputStream.close();
            outputStream.flush();
            outputStream.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



/*

        //String fileName = "inFile.jpg";
        FileOutputStream fos;


        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE); // открываем файл для записи
            fos.write(result.getBytes()); // записываем данные
            fos.close(); // закрываем файл

//            // выводим сообщение
//            Toast.makeText(context,
//                    "Файл " + fileName + " сохранён", Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
        }


        */

        return result;
    }

    // Считка потока в строку
    public static String readStream(InputStream inputStream) throws IOException {
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        return buffer.toString();
    }
}