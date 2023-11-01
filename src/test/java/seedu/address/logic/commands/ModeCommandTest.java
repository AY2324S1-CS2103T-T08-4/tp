package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ModeCommand.MESSAGE_TOGGLED;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ModeCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_toggleMode_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_TOGGLED, false, false, true);
        assertCommandSuccess(new ModeCommand(), model, expectedCommandResult, expectedModel);
    }
}
