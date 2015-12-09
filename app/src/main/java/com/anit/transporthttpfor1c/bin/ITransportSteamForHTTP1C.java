package com.anit.transporthttpfor1c.bin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Александр on 09.12.2015.
 */
public interface ITransportSteamForHTTP1C {



     /**
     *
     * @return
     * @throws IOException
      *
      * Обменяться с 1с файлами
      * Возвращает в ответ путь к файлу ответа
      * Запускать в отдельном потоке
     */
    public File ExchengeFile1C() throws IOException;



}
