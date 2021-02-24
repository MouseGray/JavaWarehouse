package commands;

import storage.ItemStack;
import storage.Storage;

import java.io.FileWriter;
import java.io.IOException;

public class CmdSave implements ICommand {
    private static final String SUCCESS_TEXT = "Data successfully saved.";
    private static final String FAIL_TEXT = "Writing to file failed.";
    private static final String SIGNATURE = "Enter: save";

    private Storage storage;
    private String filePath;

    public CmdSave(Storage storage, String filePath) {
        this.storage = storage;
        this.filePath = filePath;
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
        try {
            FileWriter writer = new FileWriter(filePath);
            for(ItemStack i: storage.getData()) {
                writer.write(i.toString() + "\r\n");
            }
            writer.close();
            System.out.println(SUCCESS_TEXT);
        } catch (IOException e) {
            System.out.println(FAIL_TEXT);
        }
    }
}
