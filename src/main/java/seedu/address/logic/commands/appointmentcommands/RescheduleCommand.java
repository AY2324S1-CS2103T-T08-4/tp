package seedu.address.logic.commands.appointmentcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_START;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDescription;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.person.Person;
import seedu.address.model.tag.PriorityTag;

/**
 * Reschedules an existing appointment
 */
public class RescheduleCommand extends Command {
    public static final String COMMAND_WORD = "reschedule";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Reschedules the appointment identified "
            + "by the index number used in the displayed appointment list.\n"
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX "
            + PREFIX_APPOINTMENT_START + "START "
            + PREFIX_APPOINTMENT_END + "END\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_APPOINTMENT_START + "2023/05/02 09:00 "
            + PREFIX_APPOINTMENT_END + "2023/05/02 11:00 ";

    public static final String MESSAGE_SUCCESS = "Patient appointment rescheduled: %1$s";

    private final Index index;

    private final AppointmentTime appointmentTime;

    /**
     * Creates a RescheduleCommand to change the appointment time
     * for the specified {@code Appointment}
     * @param index of the appointment in the filtered appointment list to edit
     * @param appointmentTime new appointment time
     */
    public RescheduleCommand(Index index, AppointmentTime appointmentTime) {
        requireNonNull(index);
        this.index = index;
        this.appointmentTime = appointmentTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Appointment> lastShownList = model.getFilteredAppointmentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }

        Appointment appointmentToReschedule = lastShownList.get(index.getZeroBased());
        Appointment rescheduledAppointment = createRescheduledAppointment(appointmentToReschedule, appointmentTime);

        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        lastShownList = model.getFilteredAppointmentList();

        // Clash in appointment slot
        if (!AppointmentTime.isValidTimeSlot(lastShownList, rescheduledAppointment)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_TIMESLOT);
        }

        // Appointment already exists
        if (model.hasAppointment(rescheduledAppointment)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_APPOINTMENT);
        }

        model.setAppointment(appointmentToReschedule, rescheduledAppointment);
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, Messages.format(rescheduledAppointment)), false, false, false, true);
    }

    private static Appointment createRescheduledAppointment(Appointment appointmentToReschedule,
                                                             AppointmentTime newTime) {
        assert appointmentToReschedule != null;

        Person patient = appointmentToReschedule.getPerson();
        AppointmentDescription description = appointmentToReschedule.getAppointmentDescription();
        PriorityTag priorityTag = appointmentToReschedule.getPriorityTag();

        return new Appointment(patient, newTime, description, priorityTag);
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RescheduleCommand)) {
            return false;
        }

        RescheduleCommand e = (RescheduleCommand) other;
        return index.equals(e.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", index)
                .add("appointmentTime", appointmentTime)
                .toString();
    }
}
