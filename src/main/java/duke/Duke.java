package duke;

import duke.exception.*;
import duke.util.Parser;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;

import java.util.Scanner;

/**
 *
 *
 *
 *
 * @param
 * @param
 * @param
 * @param
 * @param
 * @return
 * @throws
 */
public class Duke {

    public static final String EXIT_COMMAND_STRING = "bye";
    public static final String DISPLAY_LIST_COMMAND_STRING = "list";
    public static final String MARK_DONE_COMMAND_STRING = "mark";
    public static final String MARK_UNDONE_COMMAND_STRING = "unmark";
    public static final String DELETE_COMMAND_STRING = "delete";
    public static final String FIND_COMMAND_STRING = "find";
    public static final String ADD_EVENT_COMMAND_STRING = "event";
    public static final String ADD_TODO_COMMAND_STRING = "todo";
    public static final String ADD_DEADLINE_COMMAND_STRING = "deadline";

    public static final String DELIMITER = "/";
    public static final String BY_DATE_DELIMITER = "/by";
    public static final String AT_DATE_DELIMITER = "/at";
    public static final String FILE_WRITING_DELIMITER = "|";
    public static final String TAB = "    ";

    public static final String FILE_PATH = "../saved_list.txt";
    public static final String GREETING_MESSAGE = "Hi there! I' am duke.Duke, your personal time manager."
            + "\nWhat can I help you?";
    public static final String EXIT_OUTPUT_STRING = "Bye! See you next time!";
    public static final String GENERAL_ERROR_STRING = "Sorry, I don't understand that!";


    private TaskList taskList;
    private Ui ui;
    private Storage storage;

    Duke(String filePath) {
        this.taskList = new TaskList();
        this.ui = new Ui();
        this.storage = new Storage(filePath);
    }

    public void run() {
        greet();
        listen();
    }

    private void greet() {
        System.out.println(GREETING_MESSAGE);
    }

    private void listen() {
        Scanner scanner = new Scanner(System.in);
        boolean isExit = false;

        while (!isExit) {

            String output = "";
            String nextLine = scanner.nextLine();

            if (nextLine.isEmpty()) {
                continue;
            }

            boolean commandFetched = false;

            String firstWord = Parser.getCommandInstruction(nextLine);
            int index;

            try {
                switch (firstWord) {
                case (MARK_DONE_COMMAND_STRING):
                    index = Parser.getTaskIndexFromCommand(nextLine);
                    output = taskList.markTaskDone(index);
                    storage.saveFile(taskList.getFileStream());
                    commandFetched = true;
                    break;

                case (MARK_UNDONE_COMMAND_STRING):
                    index = Parser.getTaskIndexFromCommand(nextLine);
                    output = taskList.markTaskUndone(index);
                    storage.saveFile(taskList.getFileStream());
                    commandFetched = true;
                    break;

                case (DELETE_COMMAND_STRING):
                    index = Parser.getTaskIndexFromCommand(nextLine);
                    output = taskList.deleteTask(index);
                    storage.saveFile(taskList.getFileStream());
                    commandFetched = true;
                    break;

                default:
                    break;
                }

                if (!commandFetched) {
                    switch (nextLine) {
                    case (EXIT_COMMAND_STRING):
                        output = EXIT_OUTPUT_STRING;
                        commandFetched = true;
                        isExit = true;
                        break;

                    case (DISPLAY_LIST_COMMAND_STRING):
                        output = taskList.getListInfo();
                        commandFetched = true;
                        break;

                    case (FIND_COMMAND_STRING):
                        output = taskList.find(Parser.getCommandArgument(nextLine));

                    default:
                        output = taskList.addNewTask(nextLine);
                        commandFetched = true;
                        storage.saveFile(taskList.getFileStream());
                        break;
                    }
                }
            } catch (DukeIndexOutOfBoundException | DukeDateTimeFormatException | DukeTaskDateTimeMissingException |
                     DukeTaskTitleMissingException | DukeIndexMissingException | DukeCommandFormatException |
                     DukeIoException exception) {
                output = exception.getMessage();
            }

            ui.printOutput(output);
        }
    }

    private void saveFile() {

    }

    public static void main(String[] args) {
        Duke chatBot = new Duke(FILE_PATH);
        chatBot.run();
    }
}
