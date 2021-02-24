package commands;

import storage.ItemStack;
import storage.Storage;

import java.util.List;

public class CmdFind implements ICommand {
    private static final String FAIL_REGEX_FIND = "--Empty--";
    private static final String FAIL_FIND = "Item not found";
    private static final String SIGNATURE = "Enter: find [name] or [name*] for list";

    private Storage storage;

    public CmdFind(Storage storage) {
        this.storage = storage;
    }

    @Override
    public String signature() { return SIGNATURE; }

    @Override
    public int argCount() {
        return 1;
    }

    private void findRegex(String regex) {
        List<ItemStack> res = storage.filter(regex);

        if (res.isEmpty())
            System.out.println(FAIL_REGEX_FIND);
        else
            for(ItemStack i: res) System.out.println(i.toString());
    }

    private void findStd(String name) {
        ItemStack res = storage.find(name);
        if (res == null)
            System.out.println(FAIL_FIND);
        else
            System.out.println(res.toString());
    }

    @Override
    public void release(String[] args) {
        if (args[0].endsWith("*"))
            findRegex(args[0]);
        else
            findStd(args[0]);
    }
}
