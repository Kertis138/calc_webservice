package calc;

public class Log {
    static void info(String message) {
        System.out.println(message);
    }

    static void info(String format, Object... args) {
        System.out.printf(format + "\n", args);
    }
}
