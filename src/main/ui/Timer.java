package ui;

public class Timer {
    private static long start;
    private static long end;

    static void displayPage() {
        System.out.println("Timer Instruction:");
        System.out.println("Enter the letter s, and hit enter key when you want to start the timer.");
        System.out.println("When you are down, enter the letter l and hit enter as fast as possible to end timing.");

        String selection = TimeRecordController.appContext.input.next();
        selection = selection.toLowerCase();
        if (selection.equals(TimeRecordController.COMMAND_START)) {
            start = System.nanoTime();
            System.out.println("Time starts now!");
            System.out.println("Reminder: enter letter l and then hit enter key to end the timer.");
        } else {
            System.out.println("The timer is not yet started. Please follow the instruction above.");
            displayPage();
        }

        String selection2 = TimeRecordController.appContext.input.next();
        selection2 = selection2.toLowerCase();
        if (selection2.equals(TimeRecordController.COMMAND_STOP)) {
            end = System.nanoTime();
        }

        double time = (end - start) * 0.000000001;
        System.out.println("Your Time is: " + time);
        TimeRecordController.displayPage(time);
    }
}
