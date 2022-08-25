package duke.util;

import duke.Duke;
import duke.exception.*;
import duke.task.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskList {

    private static final String TAB = Duke.TAB;
    private static final String EMPTY_LIST_MESSAGE ="The list is empty.";
    private static final String GENERAL_ERROR_STRING = Duke.GENERAL_ERROR_STRING;
    private static final String MARK_DONE_OUTPUT_STRING = "Good to hear that! I have marked this as done: ";
    private static final String MARK_DONE_ERROR_STRING = "Oops! Do check the index range, and the format should be \"mark <index>\"";
    private static final String MARK_UNDONE_OUTPUT_STRING = "Sure, I have marked this as not done yet";
    private static final String MARK_UNDONE_ERROR_STRING = "Oops! Do check the index range, and the format should be \"unmark <index>\"";
    private static final String DELETE_ERROR_STRING = "Oops! Do check the index range, and the format should be \"delete <index>\"";
    private static final String DELETE_OUTPUT_STRING = "Sure, I have removed this task from the list: ";
    private static final String FIND_COMMAND_OUTPUT_STRING = "Here are what I found: ";


    private List<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public String getListInfo() {
        int len = tasks.size();
        if (len == 0) {
            return EMPTY_LIST_MESSAGE;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            stringBuilder
                    .append(i + 1)
                    .append(". ")
                    .append(tasks.get(i));
            if (i < len - 1) {
                stringBuilder.append('\n' + TAB);
            }
        }
        return stringBuilder.toString();
    }

    public String addNewTask(String input)
            throws DukeCommandFormatException, DukeTaskTitleMissingException, DukeTaskDateTimeMissingException,
            DukeDateTimeFormatException {
        Task newTask = Task.createFromCommand(input);
        if (newTask == null) {
            return GENERAL_ERROR_STRING;
        }
        tasks.add(newTask);
        return "added: " + newTask.toString();
    }

    public String markTaskDone(int index) throws DukeIndexOutOfBoundException {
        if (index < 0 || index >= tasks.size()) {
            throw new DukeIndexOutOfBoundException(MARK_DONE_ERROR_STRING);
        } else {
            Task targetTask = tasks.get(index);
            targetTask.markDone();
            return MARK_DONE_OUTPUT_STRING
                    + "\n"
                    + TAB
                    + targetTask;
        }
    }

    public String markTaskUndone(int index) throws DukeIndexOutOfBoundException {
        if (index < 0 || index >= tasks.size()) {
            throw new DukeIndexOutOfBoundException(MARK_UNDONE_ERROR_STRING);
        } else {
            Task targetTask = tasks.get(index);
            targetTask.markUndone();
            return MARK_UNDONE_OUTPUT_STRING
                    + "\n"
                    + TAB
                    + targetTask;
        }
    }

    public String deleteTask(int index) throws DukeIndexOutOfBoundException {
        if (index < 0 || index >= tasks.size()) {
            throw new DukeIndexOutOfBoundException(DELETE_ERROR_STRING);
        } else {
            Task removedTask = tasks.remove(index);
            boolean onlyOneTask = tasks.size() == 1;
            return DELETE_OUTPUT_STRING
                    + "\n"
                    + TAB
                    + removedTask
                    + "\n"
                    + TAB
                    + "There "
                    + (onlyOneTask ? "is " : "are ")
                    + tasks.size()
                    + (onlyOneTask ? " task" : " tasks")
                    + " in the list";
        }
    }

    public String getFileStream() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            stringBuilder
                    .append(tasks.get(i).getFileRepresentation())
                    .append('\n');
        }
        return stringBuilder.toString();
    }

    public String find(String keyword) {
        int len = tasks.size();

        if (len == 0) {
            return "The list is empty.";
        }

        StringBuilder stringBuilder = new StringBuilder(FIND_COMMAND_OUTPUT_STRING);
        int displayIndex = 1;

        for (int i = 0; i < len; i++) {
            Task curr = tasks.get(i);
            if (curr.contains(keyword)) {
                stringBuilder
                        .append(displayIndex++)
                        .append(". ")
                        .append(curr);
                if (i < len - 1) {
                    stringBuilder.append('\n' + TAB);
                }
            }
        }
        return stringBuilder.toString();
    }
}
