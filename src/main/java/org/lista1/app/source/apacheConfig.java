package org.lista1.app.source;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;

/** class to load configuration from .properties file */
public class apacheConfig {
    private static final String properties = "appsettings.properties"; /** link do properties w katalogu resources bo musza byc w resources w budowaniu maven*/
    private final Configuration config; /** obiekt wczytujacy configuracje z jakis plikow np .properties*/

    //zadanie 3.1
    private static final String PATH = "orders_lista3";
    //----------------

    public apacheConfig() /** odczyt zawartości z .properties*/ {
        try {
            Configurations configs = new Configurations();
            config = configs.properties(new File(
                    getClass().getClassLoader().getResource(properties).getFile()
            ));
        } catch (ConfigurationException exc) {
            throw new RuntimeException("Nie udało się wczytać pliku konfiguracyjnego", exc);
        }
    }

    public String getOrderRequestsDirectory() /** zwraca wartosc przypisana do "directory="w .propetties*/ {

        //zadanie 3.1
        String envPath = System.getenv(PATH);
        if (envPath != null && !envPath.trim().isEmpty()) {
            return envPath;
        }
        //-----------------------

        return config.getString("directory");
    }
}
