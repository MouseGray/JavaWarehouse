import java.util.Scanner;

public class Application {
    private static final String ERROR_PROPERTIES_PATH = "[ERROR://config] Property path not found";
    private static final String ERROR_DATA_PATH = "[ERROR://data] File not found: ";

    private Storage storage;

    private CommandSystem commandSystem;

    public Application(Config config) {
        storage = new Storage(config);
        commandSystem = new CommandSystem(storage.getClass());
    }

    public void exec() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            if (!scan.hasNext()) continue;
            String line = scan.nextLine();
            if (line.isEmpty()) continue;

            if (line.equals("quit")) return;

            String[] parts = line.split("\\s");

            String result = commandSystem.apply(storage, parts);
            if (result == null) {
                System.out.println("Unknown command");
                continue;
            }
            System.out.println(result);
        }
    }
}
