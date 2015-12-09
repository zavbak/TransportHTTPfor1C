package com.anit.transporthttpfor1c.bin;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Александр on 09.12.2015.
 */
public interface ITransportFileForHTTP1C {


    /**
     * Обменяться с 1с файлами
     * Возвращает в ответ путь к файлу ответа
     * Запускать в отдельном потоке
     *
     *
     * @param pathFile путь к файлу для передачи в 1С
     * @param url      URL url = new URL(127.0.0.1/exServer/hs/Exchenge);
     * @param user     Пользователь 1С
     * @param pass     Пароль
     * @return
     */
    public String ExchengeFile1C(String pathFile, URL url, String user, String pass) throws IOException;



}
