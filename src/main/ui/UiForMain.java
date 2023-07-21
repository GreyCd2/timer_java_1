package ui;

import model.Storage;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;


public class UiForMain extends JFrame {
    private JPanel mainPage;
    private JButton timerButton;
    private JButton storageButton;
    private static final int WIDTH = 200;
    private static final int HEIGHT = 100;
    private static final String JSON_STORE = "./data/storage.json";
    public static JsonWriter jsonWriter;
    public static JsonReader jsonReader;
    private Storage storage;

    public UiForMain() {

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(
                        null, "Do you want to save all the changes before quit?",
                        "System quit", JOptionPane.YES_NO_CANCEL_OPTION
                );

                if (option == JOptionPane.YES_OPTION) {
                    new SaveAction();
                } else if (option == JOptionPane.NO_OPTION) {
                    System.exit(0);
                } else {
                    return;
                }
            }
        });


        timerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        storageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }

    private class SaveAction extends AbstractAction {

        SaveAction() {
            super("Save");
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

    public static void main(String[] args) {
        new UiForMain().setVisible(true);
    }
}
