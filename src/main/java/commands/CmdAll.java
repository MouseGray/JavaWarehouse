package commands;

import storage.ItemStack;
import storage.Storage;

public class CmdAll implements ICommand {
    private Storage storage;

    public CmdAll(Storage storage) {
        this.storage = storage;
    }

    @Override
    public String signature() {
        return "Enter: all";
    }

    @Override
    public int argCount() {
        return 0;
    }

    @Override
    public void release(String[] parts) {
        System.out.println("Items: ");
        if (storage.getData().isEmpty())
            System.out.println("--Empty--");
        else
            for(ItemStack i: storage.getData())
                System.out.println(i.toString());
    }
}
