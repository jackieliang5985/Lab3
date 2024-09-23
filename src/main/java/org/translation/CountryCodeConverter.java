package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {

    private final Map<String, String> codeToCountryMap;  // To store code to country mapping
    private final Map<String, String> countryToCodeMap;
    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources folder.
     */
    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {
        codeToCountryMap = new HashMap<>();
        countryToCodeMap = new HashMap<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));
            // Skip the header line and parse each subsequent line
            for (int i = 1; i < lines.size(); i++) { // Start from index 1 to skip header
                String line = lines.get(i);
                String[] parts = line.split("\\t"); // Use tab as a delimiter

                if (parts.length >= 3) { // Ensure there's at least country and codes
                    String country = parts[0].trim(); // Country name
                    String alpha2Code = parts[1].trim(); // Alpha-2 code
                    String alpha3Code = parts[2].trim(); // Alpha-3 code

                    // Debug output to check entries
                    System.out.printf("Loading: country=%s, Alpha-2 code=%s, Alpha-3 code=%s%n", country, alpha2Code, alpha3Code);

                    // Store Alpha-2 and Alpha-3 mappings
                    codeToCountryMap.put(alpha2Code, country);
                    codeToCountryMap.put(alpha3Code, country);
                    countryToCodeMap.put(country, alpha2Code); // Assuming we want to use Alpha-2 for lookup
                }
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException("Error loading country codes", ex);
        }
    }

    /**
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        String country = codeToCountryMap.get(code.toUpperCase()); // Ensure case-insensitive lookup
        if (country == null) {
            return "Country not found for code: " + code;
        }
        return country;
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        String code = countryToCodeMap.get(country.trim());
        if (code == null) {
            return "Code not found for country: " + country;
        }
        return code;
    }


    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        return countryToCodeMap.size();
    }
}