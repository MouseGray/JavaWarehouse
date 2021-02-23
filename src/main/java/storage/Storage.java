package storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Storage {
    HashMap<String, ItemStack> data = new HashMap<>();

    public boolean add(Item item) {
        return add(item, 1);
    }

    public boolean add(Item item, int count) {
        ItemStack existedStack = data.get(item.getName());
        if (existedStack == null) {
            data.put(item.getName(), new ItemStack(item, count));
            return true;
        }
        return existedStack.add(item, count);
    }

    public boolean remove(String name) {
        ItemStack existedStack = data.get(name);
        if (existedStack == null) return false;
        existedStack.decrease();
        if (existedStack.isEmpty()) data.remove(name);
        return true;
    }

    public List<ItemStack> getData() {
        return new ArrayList<>(data.values());
    }

    public ItemStack find(String name) {
        return data.get(name);
    }

    public List<ItemStack> filter(String name) {
        return data.values().stream()
                .filter(x -> x.getItem().getName().matches(name + ".*"))
                .collect(Collectors.toList());
    }
}
