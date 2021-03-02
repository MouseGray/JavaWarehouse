package ApplicationPkg;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Config {
    private static final String property_path = "path";
    private static final String property_filters = "filters";

    private String dataFilePath = null;
    private final List<Pattern> filters = new ArrayList<>();

    public Config(String filename) throws FileNotFoundException, IllegalArgumentException {
        Scanner scan = new Scanner(new File(filename));
        while (scan.hasNext()) {
            String[] parts = scan.nextLine().trim().split("(\\+=)|=", 2);
            if (parts.length != 2) continue;
            if (parts[0].equals(property_path)) {
                dataFilePath = parts[1];
                continue;
            }
            if (parts[0].equals(property_filters)) {
                try {
                    filters.add(Pattern.compile(parts[1]));
                }
                catch(PatternSyntaxException ignored){ }
            }
        }
        scan.close();

        if (dataFilePath == null) {
            throw new IllegalArgumentException("Data file path not found or incorrect.");
        }
    }

    public List<Pattern> getFilters() {
        return filters;
    }

    public String getDataFilePath() {
        return dataFilePath;
    }
}
