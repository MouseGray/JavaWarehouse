package Commands;

import com.google.common.collect.HashMultiset;
import storage.Item;
import storage.ItemStack;
import storage.Storage;

import java.util.List;
import java.util.stream.Collectors;

public class CmdFind implements ICommand {
    private Storage storage;

    public CmdFind(Storage storage) {
        this.storage = storage;
    }

    @Override
    public String signature() {
        return "Enter: find [name] or [name*] for list";
    }

    @Override
    public int argCount() {
        return 1;
    }

    private void findRegex(String regex) {
        List<ItemStack> res = storage.filter(regex);

        if (res.isEmpty())
            System.out.println("--Empty--");
        else
            for(ItemStack i: res) System.out.println(i.toString());
    }

    private void findStd(String name) {
        ItemStack res = storage.find(name);
        if (res == null)
            System.out.println("Item not found");
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
