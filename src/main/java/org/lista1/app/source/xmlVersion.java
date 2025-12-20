package org.lista1.app.source;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import java.io.File;

class xmlVersion {

    public static String getVersion() {
        String version = "Unknown";
        try {
            File pomFile = new File("pom.xml"); // zakłada, że uruchamiasz w katalogu projektu
            if (pomFile.exists()) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(pomFile);
                doc.getDocumentElement().normalize();
                version = doc.getElementsByTagName("version").item(0).getTextContent();
            }
        } catch (Exception e) {}
        return version;
    }
}
