package CommandPkg;

import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

public class CommandProcessor extends AbstractProcessor {

    private Filer filer;
    private Messager messager;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            JavaFileObject jfo = filer.createSourceFile("CommandSystems");
            try {
                Writer writer = jfo.openWriter();
                writer.append("package ApplicationPkg;\n\n");
                writer.append("public class CommandSystems {\n");
                writer.append("public static String exec (Storage obj, String ... args) {\n");
                writer.append("if (args.length == 0) return null;");

                for(Element element : roundEnv.getElementsAnnotatedWith(Command.class)) {
                    if (element.getKind() != ElementKind.METHOD) {
                        messager.printMessage(Diagnostic.Kind.ERROR, "Annotated not method.");
                        return true;
                    }
                    ExecutableElement executableElement = (ExecutableElement) element;
                    writer.append("if (\"").append(String.valueOf(executableElement.getSimpleName())).append("\".equals(args[0])) {\n");
                    writer.append("if (args.length - 1 < ").append(String.valueOf(executableElement.getParameters().size())).append(") return \"").append(createSignature(executableElement)).append("\";\n");
                    writer.append("return obj.").append(String.valueOf(executableElement.getSimpleName())).append("(");
                    for (int i = 0; i < executableElement.getParameters().size(); i++) {
                        writer.append("args[").append(String.valueOf(i + 1));
                        writer.append("]");
                        if (i + 1 != executableElement.getParameters().size()) writer.append(",");
                    }
                    writer.append(");\n");
                    writer.append("}\n");
                }

                writer.append("return null;");
                writer.append("}\n");
                writer.append("}\n");

                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(Command.class.getCanonicalName());
        return annotations;
    }

    private String createSignature(ExecutableElement element) {
        StringBuilder builder = new StringBuilder();
        builder.append(element.getSimpleName()).append(" ");
        for (VariableElement variableElement : element.getParameters()) {
            builder.append("[").append(variableElement.getSimpleName()).append("] ");
        }
        return builder.toString();
    }
}
