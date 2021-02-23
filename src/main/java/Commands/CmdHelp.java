package Commands;

public class CmdHelp  implements ICommand {
    @Override
    public String signature() {
        return "Enter: help";
    }

    @Override
    public int argCount() {
        return 0;
    }

    @Override
    public void release(String[] parts) {
        System.out.println("List of commands: ");
        System.out.println("add [name] [category] - add new item");
        System.out.println("remove [name] - remove item");
        System.out.println("find [name] - find item by name or part name (use *)");
        System.out.println("all - show all list of item");
        System.out.println("save - save changes");
        System.out.println("quit - close program");
    }
}
