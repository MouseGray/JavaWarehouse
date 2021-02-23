import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Config {
    private static final String property_path = "path";
    private static final String property_filters = "filters";

    private Multimap<String, String> properties = HashMultimap.create();

    private String dataFilePath = System.getProperty("user.dir") + "\\Data.wh";

    private void create(String filename) throws IOException {
        File file = new File(filename);
        if (file.createNewFile()) {
            try(FileWriter writer = new FileWriter(file)) {
                writer.write(property_path + "=" + dataFilePath +"\n");
            }
        }
    }

    private void setProperties() {
        properties.put(property_path, dataFilePath);
    }

    private void getProperties(String filename) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(filename));
        while (scan.hasNext()) {
            String[] parts = scan.nextLine().trim().split("(\\+=)|=", 2);
            if (parts.length != 2) continue;
            properties.put(parts[0].trim(), parts[1].trim());
        }
        scan.close();
    }

    public void load(String filename) throws IOException {
        File file = new File(filename);
        if (file.exists())
            getProperties(filename);
        else {
            setProperties();
            create(filename);
        }
    }

    public String getDataFilePath() {
        ArrayList<String> result = new ArrayList<>(properties.get(property_path));
        return result.isEmpty() ? null : result.get(0);
    }

    public ArrayList<String> getItemFilters() {
        return new ArrayList<>(properties.get(property_filters));
    }
}
