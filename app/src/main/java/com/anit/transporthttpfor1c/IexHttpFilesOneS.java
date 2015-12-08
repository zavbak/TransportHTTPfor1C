package com.anit.transporthttpfor1c;

/**
 * Интерфейс для обмена с 1С
 */
public interface IexHttpFilesOneS {




    /**
     * Устанавливаем имя пользователя
     * @param user Имя пользователя
     */
    public abstract void setUser(String user);

    /**
     * Устанавливаем пароль
     * @param pass
     */
    public abstract void setPassword(String pass);


    /**
     * Устанавливаем адрес
     * @param targetURL строка с адресом
     */
    public abstract void setURL(String targetURL);

    /**
     * файл для отправки
     * @param strFile
     */
    public abstract void setFileOut(String strFile);



    /**
     * Возвращает путь к файлу ответа
     * @return
     */
    public abstract String getOutputFile();


}
