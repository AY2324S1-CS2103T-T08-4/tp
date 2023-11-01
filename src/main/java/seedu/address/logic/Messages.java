package seedu.address.logic;

import static seedu.address.commons.util.DateUtil.dateTimeToString;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    /**
     * Messages for person class.
     */
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The patient index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d patients listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Messages for appointment class.
     */
    public static final String MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX = "The appointment index provided is"
            + "invalid";
    public static final String MESSAGE_PATIENT_DOES_NOT_EXIST = "Patient provided does not exist";
    public static final String MESSAGE_INVALID_START_AND_END_TIMES =
            "Your start time is either before or on the same time as the end "
            + "time. Start time should be before end time";
    public static final String MESSAGE_DUPLICATE_TIMESLOT =
            "Please choose another timing for the appointment, there "
            + "already exists another appointment in this timing that clashes with the requested appointment";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT =
            "This appointment has already been created and we have taken note!";

    public static final String MESSAGE_APPOINTMENTS_LISTED_OVERVIEW = "%1$d appointments listed!";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Gender: ")
                .append(person.getGender())
                .append("; Birthdate: ")
                .append(person.getBirthdate())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Illness: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code appointment} for display to the user.
     */
    public static String format(Appointment appointment) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Patient: ")
                .append(appointment.getPatientName())
                .append("; Start: ")
                .append(dateTimeToString(appointment.getStartTime()))
                .append("; End: ")
                .append(dateTimeToString(appointment.getEndTime()))
                .append("; Description: ")
                .append(appointment.getAppointmentDescription())
                .append("; Priority Tag: ")
                .append(appointment.getPriorityTag());
        return builder.toString();
    }
}
