package Commands;

public interface ICommand {
    String signature();
    int argCount();
    void release(String[] parts);
}
