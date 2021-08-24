package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import java.io.IOException;

/**
 * Represents how Duke responds to a "done" command.
 *
 * @author Javier Phon Zhee Kai.
 * @version CS2103T AY21/22 Sem 1.
 */
public class DoneCommand extends Command {

    /**
     * Returns a boolean that tells Duke if this is the command to exit.
     *
     * @return A boolean representing the exit condition.
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Executes a "done" command.
     *
     * @param taskList The taskList where all tasks are stored.
     * @param ui An instance of the Ui class that is responsible for Duke's user interactions.
     * @param storage An instance of a the Storage class that saves and loads Duke's data.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        String[] inputValues = ui.getCommand().split(" ");
        try {
            taskList.markAsDone(Integer.parseInt(inputValues[1]));
            storage.save(taskList);
        } catch (NumberFormatException e) {
            ui.showError("     Error! Please ensure a number is entered after done (eg: done 2)");
        } catch (IndexOutOfBoundsException e) {
            if (Integer.parseInt(inputValues[1]) <= 0) {
                ui.showError("     Error! Please specify a number greater than 0");
            } else if (Integer.parseInt(inputValues[1]) == 1) {
                ui.showError("     Error! You do not have any tasks in the list");
            }
            else {
                ui.showError("     Error! You do not have " + inputValues[1] + " tasks in the list");
            }
        } catch (IOException exception) {
            ui.showSavingError();
        }
    }
}
