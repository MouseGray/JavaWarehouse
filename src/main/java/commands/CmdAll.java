package commands;

import storage.ItemStack;
import storage.Storage;

public class CmdAll implements ICommand {
    private static final String HEADER_TEXT = "Items: ";
    private static final String FAIL_TEXT = "--Empty--";
    private static final String SIGNATURE = "Enter: all";

    private Storage storage;

    public CmdAll(Storage storage) {
        this.storage = storage;
    }

    @Override
    public String signature() {
        return SIGNATURE;
    }

    @Override
    public int argCount() {
        return 0;
    }

    @Override
    public void release(String[] parts) {
        System.out.println(HEADER_TEXT);
        if (storage.getData().isEmpty())
            System.out.println(FAIL_TEXT);
        else
            for(ItemStack i: storage.getData())
                System.out.println(i.toString());
    }
}
