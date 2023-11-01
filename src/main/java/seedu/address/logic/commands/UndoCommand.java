package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents an Undo command which restores the model state to the state before the last command was executed.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo Successful.";
    public static final String MESSAGE_NO_COMMAND_TO_UNDO_ERROR = "No command to undo.";

    /**
     * The usage syntax and examples for the undo command.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Restores state to before the last command.\n"
            + "Example: " + COMMAND_WORD;

    /**
     * Creates a new {@code UndoCommand}.
     */
    public UndoCommand() {

    }

    /**
     * Executes the undo operation.
     *
     * @param model The model to operate on.
     * @return A {@code CommandResult} indicating the result of the undo operation.
     * @throws CommandException if there's no command to undo.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.getUserHistoryManager().canUndo()) {
            throw new CommandException(MESSAGE_NO_COMMAND_TO_UNDO_ERROR);
        } else {
            model.undoHistory();
            return new CommandResult("Command undone.", false, false, false, false);
        }
    }

    /**
     * Checks if another object is equivalent to this {@code UndoCommand}.
     *
     * @param other The object to compare with.
     * @return True if the other object is equivalent to this {@code UndoCommand}, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UndoCommand);
    }
}
