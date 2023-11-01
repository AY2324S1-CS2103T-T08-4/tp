package seedu.address.logic.commands.appointmentcommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;

/**
 * Cancels an appointment identified using it's displayed index from the address book.
 */
public class CancelCommand extends Command {
    public static final String COMMAND_WORD = "cancel";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Cancel the appointment with the corresponding appointment id.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 3";

    public static final String MESSAGE_CANCEL_APPOINTMENT_SUCCESS = "Cancelled Appointment: %1$s";

    private final Index targetIndex;

    public CancelCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Appointment> lastShownList = model.getFilteredAppointmentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }

        Appointment appointmentToCancel = lastShownList.get(targetIndex.getZeroBased());
        model.deleteAppointment(appointmentToCancel);
        return new CommandResult(String.format(MESSAGE_CANCEL_APPOINTMENT_SUCCESS,
                Messages.format(appointmentToCancel)), false, false, false, true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CancelCommand)) {
            return false;
        }

        CancelCommand otherCancelCommand = (CancelCommand) other;
        return targetIndex.equals(otherCancelCommand.targetIndex);
    }
}
