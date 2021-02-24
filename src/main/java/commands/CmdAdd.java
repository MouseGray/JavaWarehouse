package commands;

import storage.Item;
import storage.Storage;

import java.util.ArrayList;

public class CmdAdd implements ICommand {
    private static final String SUCCESS_TEXT = "Item successfully added.";
    private static final String FAIL_TEXT = "Incorrectly item data.";
    private static final String SIGNATURE = "Enter: add [name] [category]";


    private Storage storage;
    private ArrayList<String> filters;

    public CmdAdd(Storage storage, ArrayList<String> filters) {
        this.storage = storage;
        this.filters = filters;
    }

    @Override
    public String signature() {
        return SIGNATURE;
    }

    @Override
    public int argCount() {
        return 2;
    }

    private boolean isCorrectName(String name) {
        if (filters == null) return true;
        for (String s: filters)
            if (!name.matches(s)) return false;
        return true;
    }

    @Override
    public void release(String[] args) {
        if (isCorrectName(args[0]) && storage.add(new Item(args[0], args[1]))) {
            System.out.println(SUCCESS_TEXT);
            return;
        }
        System.out.println(FAIL_TEXT);
    }
}
