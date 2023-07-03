package ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a starter page for a router.
 * @author Grey
 */
public class Page {
    public final String title;
    public final String desc;
    public final List<Command> commands = new ArrayList<>();

    public Page(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    /**
     * A fixed text format for a starter page.
     * @return
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Command command : commands) {
            stringBuilder.append(command.toString());
        }
        return """
                \u001b[4;32m============== %s ==============\u001b[0m
                \u001b[4;32m %s \u001b[0m
                """.formatted(title, desc) + stringBuilder;
    }
}
