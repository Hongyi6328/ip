package duke.command;

import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;

public class ExitCommand extends Command {

    ExitCommand() {
        super(CommandType.EXIT);
    }

    @Override
    public void executeConcretely(Ui ui, TaskList taskList, Storage storage) {

    }
}
