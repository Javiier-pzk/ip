package duke;

import java.io.IOException;

import duke.command.Command;
import duke.exception.DukeException;
import duke.util.Parser;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;


/**
 * Represents a chat bot called Duke.
 *
 * @author Javier Phon Zhee Kai.
 * @version CS2103T AY21/22 Sem 1.
 */
public class Duke {

    private static final String FILEPATH = "data/duke.txt";
    private Storage storage;
    private TaskList taskList;
    private final Ui ui;

    /**
     * Constructor for the Duke class.
     *
     * @param filePath A string representing a filepath to save or load Duke's responses.
     */
    public Duke(String filePath) {
        this.ui = new Ui();
        try {
            this.storage = new Storage(filePath);
            this.taskList = new TaskList(this.storage.load());
        } catch (IOException exception) {
            ui.showLoadingError();
        } catch (DukeException exception) {
            ui.showError(exception.getMessage());
        }
    }

    /**
     * Runs Duke.
     * Duke starts reading inputs from the user and executes the user's commands accordingly.
     */
    public void run() {
        this.ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            String fullCommand = this.ui.readCommand();
            ui.showTopLine();
            Command c = Parser.parse(fullCommand);
            c.execute(this.taskList, this.ui, this.storage);
            ui.showBottomLine();
            isExit = c.isExit();
        }
    }

    public static void main(String[] args) {
        new Duke(FILEPATH).run();
    }
}