package org.lista1.app.source;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** read each json file data */

public class jsonLoader {
    private final Path ordersDirectory;

    public jsonLoader(String directoryPath) {
        this.ordersDirectory = Paths.get(directoryPath);
    }

    public List<String> loadAllJsonOrders() /** konwertujemy json na java string*/ {
        List<String> jsonContents = new ArrayList<>();

        if (!Files.exists(ordersDirectory) || !Files.isDirectory(ordersDirectory)) {
            System.err.println("error" + ordersDirectory.toAbsolutePath());
            return jsonContents;
        }

        try { /** odczytanie plikow json z katalogu a następnie zawartosci*/
            List<Path> jsonFiles = Files.list(ordersDirectory)
                    .filter(p -> p.toString().toLowerCase().endsWith(".json"))
                    .collect(Collectors.toList());

            for (Path jsonFile : jsonFiles) {
                String content = Files.readString(jsonFile);
                jsonContents.add(content);
            }

        } catch (IOException e) {
            System.err.println("error" + e.getMessage());
        }

        return jsonContents;
    }
}

