package commands;

import storage.Storage;

public class CmdRemove implements ICommand {
    private static final String SUCCESS_TEXT = "Item successfully removed.";
    private static final String FAIL_TEXT = "Item not found.";
    private static final String SIGNATURE = "Enter: remove [name]";

    private Storage storage;

    public CmdRemove(Storage storage) {
        this.storage = storage;
    }

    @Override
    public String signature() {
        return SIGNATURE;
    }

    @Override
    public int argCount() {
        return 1;
    }

    @Override
    public void release(String[] args) {
        if (storage.remove(args[0]))
            System.out.println(SUCCESS_TEXT);
        else
            System.out.println(FAIL_TEXT);
    }
}
