package ApplicationPkg;

import java.util.Scanner;

public class Application {
    private Storage storage;

    public Application(Config config) {
        storage = new Storage(config);
    }

    public void exec() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            if (!scan.hasNext()) continue;
            String line = scan.nextLine();
            if (line.equals("quit")) return;

            String result = ApplicationPkg.CommandSystems.exec(storage, line.split("\\s"));
            if (result == null) {
                System.out.println("Unknown command");
                continue;
            }
            System.out.println(result);
        }
    }
}
