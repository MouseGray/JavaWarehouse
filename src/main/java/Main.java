import java.io.IOException;

public class Main {
    static final String configFileName = "settings.cfg";

    public static void main(String[] args) {

        Config cfg = new Config();
        try {
            cfg.load(configFileName);
        }
        catch (IOException e) {
            System.out.println("[ERROR] Config file not found.");
            return;
        }

        Application app = new Application(cfg);
        app.load();
        app.exec();
    }
}