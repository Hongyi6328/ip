package duke.task;

import duke.Duke;
import duke.util.CommandParser;

import java.time.LocalDateTime;

public class DeadlineTask extends Task {

    private static final String LABEL = "D";

    private LocalDateTime deadline;

    protected DeadlineTask(String taskTitle, LocalDateTime deadline) {
        super(taskTitle, TaskType.DEADLINE);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return super.getStringRepresentation(
                LABEL,
                super.taskTitle + " by " + deadline.format(Task.OUTPUT_FORMATTER)
        );
    }

    @Override
    public String getFileRepresentation() {
        return super.getFileRepresentation(LABEL, deadline.format(Task.OUTPUT_FORMATTER));
    }
}
