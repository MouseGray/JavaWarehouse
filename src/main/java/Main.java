import java.io.IOException;

public class Main {
    static final String configFileName = "settings.cfg";
    static final String ERROR_CONFIG_FILE_PATH = "[ERROR] Config file not found.";
    public static void main(String[] args) {

        Config cfg = new Config();
        try {
            cfg.load(configFileName);
        }
        catch (IOException e) {
            System.out.println(ERROR_CONFIG_FILE_PATH);
            return;
        }

        Application app = new Application(cfg);
        app.load();
        app.exec();
    }
}