package duke.command;

import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;

public class DisplayListCommand extends Command {


    DisplayListCommand() {
        super(CommandType.DISPLAY_LIST);
    }

    @Override
    public void executeConcretely(Ui ui, TaskList taskList, Storage storage) {
        String output = taskList.getListInfo();
        ui.printOutput(output);
    }
}
