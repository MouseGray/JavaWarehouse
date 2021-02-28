package ApplicationPkg;

public class ItemStack {
    private Item item;
    private int count;

    public ItemStack(Item item) {
        this.item = item;
        this.count = 1;
    }

    public ItemStack(Item item, int count) {
        this.item = item;
        this.count = count;
    }

    public boolean add(Item item, int count) {
        if (this.item.equals(item)) {
            this.count += count;
            return true;
        }
        return false;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void increase() {
        this.count++;
    }

    public void increase(int count) {
        this.count += count;
    }

    public void decrease() {
        this.count--;
    }

    public void decrease(int count) {
        this.count = Math.max(this.count - count, 0);
    }

    public int getCount() {
        return this.count;
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    @Override
    public String toString() {
        return item.toString() + "|" + count;
    }
}
