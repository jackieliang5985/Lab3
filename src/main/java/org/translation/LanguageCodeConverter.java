package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides the service of converting language codes to their names.
 */
public class LanguageCodeConverter {
    private Map<String, String> languageMap; // Maps language codes to language names
    private String[] languageCodes; // Stores language codes
    private String[] languageNames; // Stores language names
    private int size; // Keeps track of the number of languages loaded


    /**
     * Default constructor which will load the language codes from "language-codes.txt"
     * in the resources folder.
     */
    public LanguageCodeConverter() {
        this("language-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the language code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public LanguageCodeConverter(String filename) {
        languageMap = new HashMap<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            // Populate the map from the lines read
            for (String line : lines) {
                // Split by tab instead of comma
                String[] parts = line.split("\t");
                if (parts.length == 2) {
                    String languageName = parts[0].trim();
                    String languageCode = parts[1].trim();
                    languageMap.put(languageCode, languageName);
                } else {
                    System.out.println("Invalid line: " + line); // Debug: Invalid line format
                }
            }

            // Debug: Print the number of languages loaded
            System.out.println("Number of languages loaded: " + languageMap.size());
        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Returns the name of the language for the given language code.
     * @param code the language code
     * @return the name of the language corresponding to the code
     */
    public String fromLanguageCode(String code) {
        // TODO Task: update this code to use your instance variable to return the correct value
        return languageMap.get(code);
    }

    /**
     * Returns the code of the language for the given language name.
     * @param language the name of the language
     * @return the 2-letter code of the language
     */
    public String fromLanguage(String language) {
        for (Map.Entry<String, String> entry : languageMap.entrySet()) {
            if (entry.getValue().equals(language)) {
                return entry.getKey(); // Returns the code for the language name
            }
        }
        return null; // Return null if not found
    }

    /**
     * Returns how many languages are included in this code converter.
     * @return how many languages are included in this code converter.
     */
    public int getNumLanguages() {
        return languageMap.size(); // Returns the number of languages in the map
    }
}
