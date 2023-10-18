package seedu.address.logic;

import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Patient;

/**
 * Container for user visible messages.
 */
public class Messages {

    /**
     * Messages for patient class.
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
    public static final String MESSAGE_INVALID_START_AND_END_TIMES =
            "Your start time is either before or on the same time as the end "
            + "time. Start time should be before end time.";
    public static final String MESSAGE_DUPLICATE_TIMESLOT =
            "Please choose another timing for the appointment. There "
            + "already exists another appointment in this timing that clashes with the requested appointment.";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT =
            "This appointment has already been created and we have taken note!";

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
     * Formats the {@code patient} for display to the user.
     */
    public static String format(Patient patient) {
        final StringBuilder builder = new StringBuilder();
        builder.append(patient.getName())
                .append("; Gender: ")
                .append(patient.getGender())
                .append("; Birthdate: ")
                .append(patient.getBirthdate())
                .append("; Phone: ")
                .append(patient.getPhone())
                .append("; Email: ")
                .append(patient.getEmail())
                .append("; Address: ")
                .append(patient.getAddress())
                .append("; Illness: ");
        patient.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code appointment} for display to the user.
     */
    public static String format(Appointment appointment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        final StringBuilder builder = new StringBuilder();
        builder.append("Patient: ")
                .append(appointment.getPatientName())
                .append("; Start: ")
                .append(appointment.getStartTime().format(formatter))
                .append("; End: ")
                .append(appointment.getEndTime().format(formatter))
                .append("; Description: ")
                .append(appointment.getAppointmentDescription());
        return builder.toString();
    }
}
