package commands;

import storage.Storage;

public class CmdRemove implements ICommand {
    private Storage storage;

    public CmdRemove(Storage storage) {
        this.storage = storage;
    }

    @Override
    public String signature() {
        return "Enter: remove [name]";
    }

    @Override
    public int argCount() {
        return 1;
    }

    @Override
    public void release(String[] args) {
        if (storage.remove(args[0]))
            System.out.println("Item successfully removed.");
        else
            System.out.println("Item not found.");
    }
}
