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


    public void setParamReqest(Map<String, String> paramReqest) {
        this.paramReqest = paramReqest;
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

    public Boolean executeExchange(){


        return true;
    }



}
