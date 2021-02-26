import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandSystem {
    private final class CommandMethod {
        private final Method method;
        private final int argsCount;
        private final String signature;

        public CommandMethod(Method method) {
            this.method = method;
            this.argsCount = method.getParameterCount();
            this.signature = createSignature(method);
            method.setAccessible(true);
        }

        private String createSignature(Method method) {
            StringBuilder buffer = new StringBuilder();
            buffer.append(method.getName());
            for (Parameter param : method.getParameters()) {
                buffer.append(" ")
                        .append("[")
                        .append(param.getName())
                        .append("]");
            }
            return buffer.toString();
        }
    }


    private final List<CommandMethod> methods = new ArrayList<>();

    public CommandSystem(Class<?> ... classes) {
        for (Class<?> c: classes){
            for (Method m: c.getMethods()) {
                if (m.isAnnotationPresent(Command.class)) {
                    methods.add(new CommandMethod(m));
                }
            }
        }
    }

    public String apply(Object obj, String ... args) {
        if (args.length == 0) return null;

        for (CommandMethod cm : methods) {
            if (args[0].equals(cm.method.getName())) {
                if (args.length - 1 < cm.argsCount) return cm.signature;
                try {
                    return (String) cm.method.invoke(obj, Arrays.copyOfRange(args, 1, args.length));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    return e.getMessage();
                }
            }
        }
        return null;
    }
}
