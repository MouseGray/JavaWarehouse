import java.io.FileNotFoundException;

public class Main {
    static final String configFileName = "settings.cfg";

    public static void main(String[] args)
    {
        try {
            Application app = new Application(new Config(configFileName));
            app.exec();
        }
        catch (FileNotFoundException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}