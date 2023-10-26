package seedu.address.logic.commands.appointmentcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_PATIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_PRIORITY_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_START;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Adds an appointment to the address book.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Schedules an appointment.\n"
            + "Parameters: "
            + PREFIX_APPOINTMENT_PATIENT + "PATIENT "
            + PREFIX_APPOINTMENT_START + "START "
            + PREFIX_APPOINTMENT_END + "END "
            + PREFIX_APPOINTMENT_DESCRIPTION + "DESCRIPTION"
            + "[" + PREFIX_APPOINTMENT_PRIORITY_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_APPOINTMENT_PATIENT + "Alex Yeoh "
            + PREFIX_APPOINTMENT_START + "2023/10/20 12:00 "
            + PREFIX_APPOINTMENT_END + "2023/10/20 13:00 "
            + PREFIX_APPOINTMENT_DESCRIPTION + "Follow up on Chest X-Ray "
            + PREFIX_APPOINTMENT_PRIORITY_TAG + "HIGH";

    public static final String MESSAGE_SUCCESS = "New appointment scheduled: %1$s.";

    private final Appointment currAppointment;

    private final Name patientName;

    /**
     * Creates a ScheduleCommand to add the specified {@code Appointment}
     */
    public ScheduleCommand(Appointment appointment, Name patientName) {

        // Check that appointment is non-null.
        requireNonNull(appointment);
        // Save the appointment to currAppointment during initialisation.
        this.currAppointment = appointment;
        this.patientName = patientName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> latestPersonList = model.getFilteredPersonList();
        List<Appointment> lastShownList = model.getFilteredAppointmentList();

        if (!model.hasPerson(patientName)) {
            throw new CommandException(Messages.MESSAGE_PATIENT_DOES_NOT_EXIST);
        }

        Person personToAdd = latestPersonList
                .stream().filter(person -> person.getName().equals(patientName)).collect(Collectors.toList()).get(0);

        // Add the Person patient to the current appointment
        currAppointment.setPatient(personToAdd);

        // Clash in appointment slot
        if (!AppointmentTime.isValidTimeSlot(lastShownList, currAppointment)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_TIMESLOT);
        }

        // Appointment already exists
        if (model.hasAppointment(currAppointment)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_APPOINTMENT);
        }

        model.addAppointment(currAppointment);

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, Messages.format(currAppointment)), false, false, true);
    }


    @Override
    public boolean equals(Object other) {

        // Check if the given object is the same object.
        if (this == other) {
            return true;
        }

        // if Object other is of type AddAppointmentCommand, cast it to type AddAppointmentCommand and compare the
        // containing
        // currAppointment
        if (other instanceof ScheduleCommand) {
            ScheduleCommand otherAppointment = (ScheduleCommand) other;

            return currAppointment.equals(otherAppointment.currAppointment)
                    && patientName.equals(otherAppointment.patientName);
        }
        return false;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", currAppointment)
                .toString();
    }
}
