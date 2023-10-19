package seedu.address.logic.parser.appointmentparser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_PATIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_START;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.address.logic.commands.appointmentcommands.ScheduleCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDescription;
import seedu.address.model.appointment.AppointmentTime;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddAppointmentCommandParser implements Parser<ScheduleCommand> {

    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param args
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public ScheduleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_APPOINTMENT_PATIENT, PREFIX_APPOINTMENT_START,
                        PREFIX_APPOINTMENT_END, PREFIX_APPOINTMENT_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_APPOINTMENT_PATIENT, PREFIX_APPOINTMENT_START,
                PREFIX_APPOINTMENT_END, PREFIX_APPOINTMENT_DESCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ScheduleCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_APPOINTMENT_PATIENT, PREFIX_APPOINTMENT_START,
                PREFIX_APPOINTMENT_END, PREFIX_APPOINTMENT_DESCRIPTION);

        LocalDateTime startTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_APPOINTMENT_START).get());
        LocalDateTime endTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_APPOINTMENT_END).get());
        AppointmentDescription appointmentDescription = ParserUtil.parseDescription(argMultimap
                .getValue(PREFIX_APPOINTMENT_DESCRIPTION).get());

        AppointmentTime appointmentTime;
        try {
            appointmentTime = new AppointmentTime(startTime, endTime);
        } catch (IllegalArgumentException e) {
            throw new ParseException(AppointmentTime.MESSAGE_CONSTRAINTS);
        }

        int patientIndex = ParserUtil.parsePatientIndex(argMultimap.getValue(PREFIX_APPOINTMENT_PATIENT).get());

        Appointment appointment = new Appointment(patientIndex, appointmentTime, appointmentDescription);

        return new ScheduleCommand(appointment, patientIndex);

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
