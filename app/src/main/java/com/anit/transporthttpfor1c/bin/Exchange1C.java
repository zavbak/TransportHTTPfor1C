package com.anit.transporthttpfor1c.bin;

import java.net.URL;
import java.util.Map;

/**
 * Created by Александр on 10.12.2015.
 */
public class Exchange1C {

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
    Map<String,String> paramReqest;

    /**
     * Авторизация пользователь в 1С
     */
    String user;

    /**
     * Пароль 1С
     */
    String password;


    public Boolean executeExchange(){


        return true;
    }



}
