import commands.*;
import storage.*;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Application {
    private static final String ERROR_PROPERTIES_PATH = "[ERROR://config] Property path not found";
    private static final String ERROR_DATA_PATH = "[ERROR://data] File not found: ";

    Storage storage = new Storage();
    Config config;
    boolean isInitialized;

    HashMap<String, ICommand> commands = new HashMap<>();

    public Application(Config config) {
        this.config = config;
        this.isInitialized = false;

        fillCommands();
    }

    public void fillCommands() {
        commands.put("add",      new CmdAdd(storage, config.getItemFilters()));
        commands.put("remove",   new CmdRemove(storage));
        commands.put("find",     new CmdFind(storage));
        commands.put("all",      new CmdAll(storage));
        commands.put("help",     new CmdHelp());
        commands.put("save",     new CmdSave(storage, config.getDataFilePath()));
    }

    public void load() {
        if (config.getDataFilePath() == null) {
            System.out.println(ERROR_PROPERTIES_PATH);
            return;
        }
        try(Scanner scan = new Scanner(new File(config.getDataFilePath()))) {
            while (scan.hasNext()) {
                String[] parts = scan.nextLine().split("\\|");
                if (parts.length < 3) continue;
                try {
                    storage.add(new Item(parts[0], parts[1]), Integer.parseInt(parts[2]));
                } catch (NumberFormatException e) { /* skip line */ }
            }
        } catch (FileNotFoundException e) {
            System.out.println(ERROR_DATA_PATH + config.getDataFilePath());
        }
        this.isInitialized = true;
    }

    public void exec() {
        if (!this.isInitialized) return;

        commands.get("help").release(null);

        Scanner scan = new Scanner(System.in);
        while (true) {
            if (!scan.hasNext()) continue;
            String line = scan.nextLine();
            if (line.isEmpty()) continue;

            if (line.equals("quit")) return;

            String[] parts = line.split("\\s");

            ICommand cmd = commands.get(parts[0]);
            if (cmd == null) {
                System.out.println("Unknown command");
                continue;
            }
            if (parts.length < cmd.argCount() + 1) {
                System.out.println(cmd.signature());
                continue;
            }
            cmd.release(Arrays.copyOfRange(parts, 1, parts.length));
        }
    }
}
