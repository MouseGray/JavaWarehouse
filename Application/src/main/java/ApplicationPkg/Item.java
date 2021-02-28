package ApplicationPkg;

import java.util.Objects;

public class Item {
    private String name;
    private String category;

    public Item(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCategory());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name) && Objects.equals(category, item.category);
    }

    @Override
    public String toString() {
        return name + "|" + category;
    }
}
