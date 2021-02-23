package Commands;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import storage.Item;
import storage.ItemStack;
import storage.Storage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class CmdSave implements ICommand {
    private Storage storage;
    private String filePath;

    public CmdSave(Storage storage, String filePath) {
        this.storage = storage;
        this.filePath = filePath;
    }

    @Override
    public String signature() {
        return "Enter: save";
    }

    @Override
    public int argCount() {
        return 0;
    }

    @Override
    public void release(String[] parts) {
        try {
            FileWriter writer = new FileWriter(filePath);
            for(ItemStack i: storage.getData()) {
                writer.write(i.toString() + "\r\n");
            }
            writer.close();
            System.out.println("Data successfully saved.");
        } catch (IOException e) {
            System.out.println("Writing to file failed: " + filePath);
        }
    }
}
