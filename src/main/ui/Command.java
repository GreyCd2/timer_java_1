package ui;

/**
 * Represent a command option.
 *
 * @author Grey
 */
public class Command {
    public final String key;
    public final String desc;

    public Command(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    /**
     * A fixed text format for a command.
     *
     * @return
     */
    @Override
    public String toString() {
        return """
                \u001b[4;32m[%s]\u001b[0m -> %s
                """.formatted(key, desc);
    }
}
