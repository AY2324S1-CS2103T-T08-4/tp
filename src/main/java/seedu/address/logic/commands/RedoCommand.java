package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a redo command to restore the application state to before the last undo action.
 *
 * The redo command allows users to reapply changes that were undone by the previous undo command.
 * If there is no command available to redo, an exception is thrown.
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";

    /**
     * Success message displayed when the redo command is executed successfully.
     */
    public static final String MESSAGE_SUCCESS = "Redo Successful. ";

    /**
     * Error message displayed when there is no command available to redo.
     */
    public static final String MESSAGE_NO_COMMAND_TO_REDO_ERROR = "No command to redo.";

    /**
     * Usage message to guide the user on how to use the redo command.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Restores state to before the last undo command. "
            + "\n"
            + "Example: " + COMMAND_WORD;

    /**
     * Executes the redo command.
     *
     * If the UserHistoryManager indicates that a redo is possible, the application state is updated.
     * Otherwise, a CommandException is thrown.
     *
     * @param model The current application model.
     * @return A CommandResult indicating the result of the redo operation.
     * @throws CommandException if there is no command available to redo.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.getUserHistoryManager().canRedo()) {
            throw new CommandException(MESSAGE_NO_COMMAND_TO_REDO_ERROR);
        } else {
            model.redoHistory();
            return new CommandResult("Command redone.", false, false, false, false);
        }
    }

    /**
     * Compares this RedoCommand object to another object.
     *
     * @param other The object to compare with.
     * @return true if the other object is the same instance or is also an instance of RedoCommand, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RedoCommand); // instanceof handles nulls
    }
}
