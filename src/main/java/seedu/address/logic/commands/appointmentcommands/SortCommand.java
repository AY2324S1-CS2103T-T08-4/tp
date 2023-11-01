package seedu.address.logic.commands.appointmentcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;


/**
 * Sorts all appointments in MediFlowR according to start time.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted all appointments.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortAppointmentList();
        return new CommandResult(MESSAGE_SUCCESS, false, false, false, true);
    }
}
