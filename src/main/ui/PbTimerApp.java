package ui;

import model.Folder;
import model.Storage;
import model.TimeRecord;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// PbTimerApplication
public class PbTimerApp {
    private static final String JSON_STORE = "./data/storage.json";
    private Storage storage;
    private long start;
    private long end;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: shows the starter page and runs the pb timer application
    public PbTimerApp() {
        System.out.println("Hi! Welcome to PB Timer.");

        init();
        runTimer();
    }

    // MODIFIES: this
    // EFFECTS:  processes user input
    private void runTimer() {
        startPage();
        String command = input.next();
        command = command.toLowerCase();

        if (command.equals("q")) {
            System.out.println("Thank you for using PB Timer.");
            System.out.println("See you next time! :)");
            System.exit(0);
        } else if (command.equals("s")) {
            saveStorage();
        } else if (command.equals("l")) {
            loadStorage();
        } else {
            timerOrStorage(command);
        }
    }

    // MODIFIES: this
    // EFFECTS:  initializes the app
    private void init() {
        input = new Scanner(System.in);
        storage = new Storage();
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS:  process the starter page for user command
    private void startPage() {
        System.out.println("Please select which page you want to go.");
        System.out.println("t -> start your timer here!");
        System.out.println("g -> go to storage to see your past records!");
        System.out.println("l -> load storage from file and go to storage!");
        System.out.println("s -> save your changes to file and quit!");
        System.out.println("q -> ignore all the changes and quit!");
    }

    //EFFECTS:  saves the storage to file
    private void saveStorage() {
        try {
            jsonWriter.open();
            jsonWriter.write(storage);
            jsonWriter.close();
            System.out.println("Your changes to storage have been saved to " + JSON_STORE + ".");
            System.out.println("See you next time! :) ");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save to file: " + JSON_STORE + ".");
        }
    }

    //MODIFIES: this
    //EFFECTS:  loads storage from file
    private void loadStorage() {
        try {
            storage = jsonReader.read();
            System.out.println("Storage from file:" + JSON_STORE + " has been loaded.");
            goToStorage();
        } catch (IOException e) {
            System.out.println("Unable to read from file " + JSON_STORE + ".");
        }
    }

    // MODIFIES: this
    // EFFECTS:  process user command of going to timer page or storage page
    private void timerOrStorage(String command) {
        if (command.equals("t")) {
            startTimer();
        } else if (command.equals("g")) {
            goToStorage();
        } else {
            System.out.println("Invalid selection.");
            System.out.println("Please input an character that is provided.");
        }
    }

    // MODIFIES: this
    // EFFECTS:  start a timer
    private void startTimer() {
        System.out.println("Timer Instruction:");
        System.out.println("Enter the letter s, and hit enter key when you want to start the timer.");
        System.out.println("When you are down, enter the letter l and hit enter as fast as possible to end timing.");

        String selection = input.next();
        selection = selection.toLowerCase();
        if (selection.equals("s")) {
            start = System.nanoTime();
            System.out.println("Time starts now!");
            System.out.println("Reminder: enter letter l and then hit enter key to end the timer.");
        } else {
            System.out.println("The timer is not yet started. Please follow the instruction above.");
            startTimer();
        }

        String selection2 = input.next();
        selection2 = selection2.toLowerCase();
        if (selection2.equals("l")) {
            end = System.nanoTime();
        }

        double time = (end - start) * 0.000000001;
        System.out.println("Your Time is: " + time);
        doWithTimeRecord(time);
    }

    // MODIFIES: folder
    // EFFECTS:  instantiate a new time record, user can then decide to keep this record in a folder or ignore it.
    private void doWithTimeRecord(double time) {
        System.out.println("What would you like to do with this time record?");
        System.out.println("k -> keep it");
        System.out.println("i -> ignore it and go back to main starter page");

        String selection = input.next();
        selection = selection.toLowerCase();
        if (selection.equals("k")) {
            System.out.println("Please enter any note you would like to add for this time record:");
            String note = input.next() + input.nextLine();
            TimeRecord timeRecord = new TimeRecord(time, note);
            addTimeRecordTo(timeRecord);
        } else if (selection.equals("i")) {
            runTimer();
        } else {
            System.out.println("Invalid input. Please enter a character that is provided.");
            doWithTimeRecord(time);
        }
    }

    // MODIFIES: this
    // EFFECTS:  add the time record into a folder with given index.
    private void addTimeRecordTo(TimeRecord tr) {
        System.out.println("Please enter the index of the folder you would like to add this time record into:");
        displayFolders(storage);
        int index = input.nextInt();
        Folder f = storage.getIthFolder(index);
        f.addTimeRecord(tr);
        System.out.println("Your time record has been successfully added.");
        System.out.println("Directing you back to storage page...");
        goToStorage();
    }

    // MODIFIES: this
    // EFFECTS:  go to the storage, display all the folders. User can then choose to
    //           go to a folder or back to main page.
    private void goToStorage() {
        System.out.println("Storage:");
        displayFolders(storage);

        System.out.println("c -> create a new folder");
        System.out.println("d -> delete a folder");
        System.out.println("r -> rename a folder");
        System.out.println("o -> open a folder");
        System.out.println("b -> back to starter page.");

        String selection = input.next();
        selection = selection.toLowerCase();
        if (selection.equals("c")) {
            createFolder();
        } else if (selection.equals("d")) {
            deleteFolder();
        } else if (selection.equals("r")) {
            renameFolder();
        } else if (selection.equals("o")) {
            openFolder();
        } else if (selection.equals("b")) {
            runTimer();
        } else {
            System.out.println("Invalid input. Please enter a character that is provided.");
            goToStorage();
        }
    }

    // MODIFIES: this
    // EFFECTS:  add a new folder with input name into storage, and then go back to storage page.
    private void createFolder() {
        System.out.println("How would you like to name this new Folder?");
        String folderName = input.next() + input.nextLine();
        storage.addFolder(folderName);
        System.out.println("Folder " + folderName + " has been created.");

        goToStorage();
    }

    // MODIFIES: this
    // EFFECTS: delete the folder with given index, and then go back to storage page
    private void deleteFolder() {
        System.out.println("Please enter the index of the folder you want to delete:");
        int index = input.nextInt();
        storage.deleteFolder(index);
        System.out.println("Folder " + index + " has been deleted.");

        goToStorage();
    }

    // MODIFIES: folder
    // EFFECTS: rename the folder with given index, and then go back to storage page
    private void renameFolder() {
        System.out.println("Please enter the index of the folder you want to rename:");
        int index = input.nextInt();
        Folder f = storage.getIthFolder(index);
        System.out.println("Please enter the new name for this folder:");
        String newName = input.next() + input.nextLine();
        f.renameFolder(newName);
        System.out.println("Folder " + index + " has been renamed as " + newName + ".");

        goToStorage();
    }

    // EFFECTS: open the folder with given index, display all the records in the folder
    //          Then choose what to do with the folder.
    private void openFolder() {
        System.out.println("Please enter the index of the folder you want to open:");
        int index = input.nextInt();
        Folder f = storage.getIthFolder(index);
        System.out.println("Folder: " + f.getName());
        displayRecords(f);
        doWithFolder(f);
    }

    // MODIFIES: this
    // EFFECTS:  delete a record, go back to storage page, or go back to starter page
    private void doWithFolder(Folder f) {
        System.out.println("d -> delete a time record");
        System.out.println("e -> edit note for a time record");
        System.out.println("c -> call the average calculator");
        System.out.println("s -> go back to storage page");
        System.out.println("m -> go back to main starter page");

        String selection = input.next();
        selection = selection.toLowerCase();
        if (selection.equals("d")) {
            deleteRecord(f);
        } else if (selection.equals("e")) {
            editNote(f);
        } else if (selection.equals("c")) {
            openCalculator(f);
        } else if (selection.equals("s")) {
            goToStorage();
        } else if (selection.equals("m")) {
            runTimer();
        } else {
            System.out.println("Invalid input. Please enter a character that is provided.");
            doWithFolder(f);
        }
    }

    // MODIFIES: this
    // EFFECTS:  delete the record with given index
    private void deleteRecord(Folder f) {
        System.out.println("Please enter the index of the record you want to delete:");
        int index = input.nextInt();
        f.deleteTimeRecord(index);
        System.out.println("Time record " + index + " has been deleted.");

        displayRecords(f);
    }

    // MODIFIES: this
    // EFFECTS:  replace the note of the record with given index with a new input note
    private void editNote(Folder f) {
        System.out.println("Please enter the index of the record you want to edit:");
        int index = input.nextInt();
        TimeRecord editRecord = f.getIthTimeRecord(index);
        String newNote = getNewNote();
        editRecord.editNote(newNote);
        System.out.println("Note for record " + index + " has been edited.");

        doWithFolder(f);
    }

    // MODIFIES: this
    // EFFECTS:  call out a calculator that can compute the average for all records' average time in the folder,
    //           or compute the input amount of recent records' average time.
    private void openCalculator(Folder f) {
        System.out.println("The calculator is ready.");
        System.out.println("Please enter the number of records to count, or -1 for all records in the folder.");

        int amount = input.nextInt();
        if (amount == -1) {
            calculateAll(f);
        } else {
            calculateDesiredAmount(f, amount);
        }

        doWithFolder(f);
    }

    // MODIFIES: this
    // EFFECTS:  calculate all records' average time in the given folder.
    public void calculateAll(Folder f) {
        double sum = 0;
        int count = 0;

        for (TimeRecord tr : (f.getRecords())) {
            sum = sum + tr.getTime();
            count++;
        }

        double result = sum / count;
        System.out.println("Your average time is: " + result);
    }

    // MODIFIES: this
    // EFFECTS:  calculate given amount of records' average time in the given folder.
    public void calculateDesiredAmount(Folder f, int amount) {
        double sum = 0;
        ArrayList<TimeRecord> records = f.getRecords();

        for (TimeRecord tr : records) {
            if (records.indexOf(tr) < amount) {
                sum = sum + tr.getTime();
            }
        }

        double result = sum / amount;
        System.out.println("Your average time is: " + result);
    }

    // EFFECTS:  get user input as new note
    private String getNewNote() {
        System.out.println("Please enter the new note:");
        return (input.next() + input.nextLine());
    }

    // EFFECTS:  display all the time records in this folder with index, time, and note.
    public void displayRecords(Folder f) {
        System.out.println("PB Record: " + (f.getPbRecord()).getTime() + ": " + (f.getPbRecord()).getNote());

        for (TimeRecord r : (f.getRecords())) {
            System.out.println((f.getRecords()).indexOf(r) + ". " + r.getTime() + ": " + r.getNote());
        }

        System.out.println("All records you have in this folder are shown above.");
    }

    // EFFECTS:  displays all folders with index and name in order.
    public void displayFolders(Storage s) {
        for (Folder f : (s.getFolders())) {
            System.out.println((s.getFolders()).indexOf(f) + ". Folder: " + f.getName());
        }
        System.out.println("All folders you have are shown above.");
    }
}
