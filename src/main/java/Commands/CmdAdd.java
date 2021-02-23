package Commands;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import storage.Item;
import storage.Storage;

import java.util.ArrayList;

public class CmdAdd implements ICommand {
    private Storage storage;
    private ArrayList<String> filters;

    public CmdAdd(Storage storage, ArrayList<String> filters) {
        this.storage = storage;
        this.filters = filters;
    }

    @Override
    public String signature() {
        return "Enter: add [name] [category]";
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
            System.out.println("Item successfully added.");
            return;
        }
        System.out.println("Incorrectly item data.");
    }
}
