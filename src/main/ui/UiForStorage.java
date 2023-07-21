package ui;

import model.Event;
import model.EventHistory;
import model.Storage;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class UiForStorage extends JFrame {
    private static final int WIDTH = 300;
    private static final int HEIGHT = 600;
    private static final String JSON_STORE = "./data/storage.json";
    private Storage storage;
    private JPanel panel;
    private JTextArea outputArea;
    private JScrollPane scrollPane;
    private static JsonWriter jsonWriter;
    private static JsonReader jsonReader;

    private static void startPage() {
        int displayTime = 2000;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        JWindow startWindow = new JWindow();
        startWindow.setSize(WIDTH, HEIGHT);
        startWindow.setLocationRelativeTo(null);
        startWindow.setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon("starterPage.png");
        JLabel label = new JLabel(icon);
        startWindow.add(label, BorderLayout.CENTER);

        startWindow.setVisible(true);

        ActionListener closeWindow = e -> {
            startWindow.setVisible(false);
            startWindow.dispose();
        };

        javax.swing.Timer timer = new javax.swing.Timer(displayTime, closeWindow);
        timer.setRepeats(false);
        timer.start();
    }

    public UiForStorage() {
        storage = Storage.getInstance();

        startPage();
        addMenuBar();

        setTitle("Storage");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printLog(EventHistory.getInstance());
                System.exit(0);
            }
        });

        panel = new JPanel(new BorderLayout());
        getContentPane().add(panel);

        outputArea = new JTextArea();
        outputArea.setEditable(false);

        scrollPane = new JScrollPane(outputArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        displayStorage();

        setVisible(true);
    }

    private void addMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        addMenuItem(fileMenu, new SaveFileAction(),
                KeyStroke.getKeyStroke("control S"));
        addMenuItem(fileMenu, new LoadFileAction(),
                KeyStroke.getKeyStroke("control L"));
        menuBar.add(fileMenu);

        JMenu folderMenu = new JMenu("Folder");
        addMenuItem(folderMenu, new AddFolderAction(), null);
        addMenuItem(folderMenu, new RemoveFolderAction(), null);
        menuBar.add(folderMenu);

        this.setJMenuBar(menuBar);
    }

    private void addMenuItem(JMenu theMenu, AbstractAction action, KeyStroke accelerator) {
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setMnemonic(menuItem.getText().charAt(0));
        menuItem.setAccelerator(accelerator);
        theMenu.add(menuItem);
    }

    private class SaveFileAction extends AbstractAction {

        SaveFileAction() {
            super("Save File");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                jsonWriter.open();
                jsonWriter.write(storage);
                jsonWriter.close();
                JOptionPane.showMessageDialog(null, "File saved successfully.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error: unable to save file.");
            }
        }
    }

    private class LoadFileAction extends AbstractAction {

        LoadFileAction() {
            super("Load File");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                storage = jsonReader.read();
                displayStorage();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error: unable to load file.");
            }
        }
    }

    private class AddFolderAction extends AbstractAction {

        AddFolderAction() {
            super("Add Folder");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String folderName = JOptionPane.showInputDialog(null, "Enter your folder name:",
                    "folder");
            if (!folderName.isEmpty() && folderName != null) {
                storage.addFolder(folderName);
                displayStorage();
            } else {
                JOptionPane.showMessageDialog(null, "Error: folder name should not be empty.");
            }
        }
    }

    private class RemoveFolderAction extends AbstractAction {

        RemoveFolderAction() {
            super("Remove Folder");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String indexStr = JOptionPane.showInputDialog(null,
                    "Enter the index of the folder that you want to remove:", "0");
            if (!indexStr.isEmpty() && indexStr != null) {
                try {
                    int index = Integer.parseInt(indexStr);
                    if (index > 0 && index <= storage.getFolders().size()) {
                        storage.deleteFolder(index - 1);
                        displayStorage();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: invalid index.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Error: invalid index.");
                }
            }
        }
    }

    private void displayStorage() {
        outputArea.setText("");
        for (int i = 0; i < storage.getFolders().size(); i++) {
            String name = storage.getFolders().get(i).getName();
            outputArea.append((i + 1) + ": " + name + "\n");
        }
    }

    private void printLog(EventHistory el) {
        for (Event next : el) {
            System.out.println(next.toString());
        }
    }

    public static void main(String[] args) {
        new UiForStorage();
    }
}
