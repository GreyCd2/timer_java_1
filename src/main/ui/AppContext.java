package ui;

import model.Storage;

import java.util.Scanner;

public class AppContext {
    private static final AppContext INSTANCE = new AppContext();
    public Storage storage;
    public Scanner input;

    private AppContext() {
    }

    public static AppContext getInstance() {
        return INSTANCE;
    }
}
