package ApplicationPkg;

import CommandPkg.Command;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Storage {
    private final Config config;

    private final Map<String, ArrayList<ItemStack>> data = new HashMap<>();

    public Storage(Config config) {
        this.config = config;
    }

    @Command
    public String help() {
        return "List of commands: " +
                "add [name] [category] - add new item" +
                "remove [name] - remove item" +
                "find [name] - find item by name or part name (use *)" +
                "all - show all list of item" +
                "save - save changes" +
                "quit - close program";
    }

    @Command
    public String load() {
        try(Scanner scan = new Scanner(new File(config.getDataFilePath()))) {
            while (scan.hasNext()) {
                String[] parts = scan.nextLine().split("\\|");
                if (parts.length < 3) continue;
                try {
                    add(parts[0], parts[1], Integer.parseInt(parts[2]));
                } catch (NumberFormatException e) { /* skip line */ }
            }
        } catch (FileNotFoundException e) {
            return "Data file not found";
        }
        return "Data loaded successfully";
    }

    @Command
    public String save() {
        try {
            FileWriter writer = new FileWriter(config.getDataFilePath());
            for(ArrayList<ItemStack> value: data.values()) {
                for (ItemStack itemStack: value) {
                    writer.write(itemStack.toString() + "\r\n");
                }
            }
            writer.close();
            return "Data saved successfully";
        } catch (IOException e) {
            return "Failed to save data";
        }
    }

    @Command
    public String add(String name, String category) {
        return add(name, category, 1);
    }

    public String add(String name, String category, int count) {
        if (!isCorrectName(name)) return "Incorrect name";

        ArrayList<ItemStack> existedStack = data.get(name);
        if (existedStack == null) {
            data.put(name, new ArrayList<>(Collections.singletonList(new ItemStack(new Item(name, category), count))));
            return "Item successfully added";
        }
        for (ItemStack item: existedStack) {
            if (item.getItem().getCategory().equals(category)) {
                item.increase(count);
                return "Item successfully added";
            }
        }
        existedStack.add(new ItemStack(new Item(name, category), count));
        return "Item successfully added";
    }

    @Command
    public String remove(String name, String category) {
        ArrayList<ItemStack> existedStack = data.get(name);
        if (existedStack == null) return "Item not found";
        for (ItemStack item: existedStack) {
            if (item.getItem().getCategory().equals(category)) {
                item.decrease();
                if (item.isEmpty()) existedStack.remove(item);
                if (existedStack.isEmpty()) data.remove(name);
                return "Item successfully removed";
            }
        }
        return "Item not found";
    }

    @Command
    public String find(String name) {
        return ArrayItemsToString(data.get(name));
    }

    @Command
    public String all() {
        StringBuilder buffer = new StringBuilder();
        for (Map.Entry<String, ArrayList<ItemStack>> value: data.entrySet())
            buffer.append(ArrayItemsToString(value.getValue()));
        if (buffer.length() == 0) {
            return "--Empty--";
        }
        return buffer.toString();
    }

    private boolean isCorrectName(String name) {
        if (config.getFilters() == null) return true;
        for (Pattern pattern: config.getFilters())
            if (!pattern.matcher(name).matches()) return false;
        return true;
    }

    private String ArrayItemsToString(ArrayList<ItemStack> array) {
        if (array == null || array.isEmpty()) {
            return "--Empty--";
        }
        StringBuilder buffer = new StringBuilder();
        for (ItemStack itemStack: array) {
            buffer.append(itemStack.toString()).append("\n");
        }
        return buffer.substring(0, buffer.length() - 1);
    }
}
