package seedu.address.testutil;

import static seedu.address.commons.util.DateUtil.dateTimeToString;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.appointment.Appointment;

/**
 * A utility class containing a list of {@code Appointment} objects to be used in tests.
 */
public class TypicalAppointments {

    public static final Appointment APPOINTMENT1 = new AppointmentBuilder()
            .withPatient(ALICE).withAppointmentTime("2023/10/10 10:00", "2023/10/10 11:00")
            .withDescription("Checkup").withPriorityTag("high").build();

    public static final Appointment APPOINTMENT2 = new AppointmentBuilder()
            .withPatient(BENSON).withAppointmentTime("2023/01/29 21:00", "2023/01/29 23:30")
            .withDescription("X-ray Checkup").withPriorityTag("low").build();

    // Manually added
    public static final Appointment APPOINTMENT3 = new AppointmentBuilder()
            .withPatient(HOON).withAppointmentTime("2021/04/18 15:00", "2023/04/18 17:30")
            .withDescription("Follow-up").withPriorityTag("medium").build();

    public static final Appointment APPOINTMENT4 = new AppointmentBuilder()
            .withPatient(IDA).withAppointmentTime("2022/06/13 15:00", "2022/06/13 16:30")
            .withDescription("Follow-up").withPriorityTag("high").build();

    public static final Appointment NOCLASHAPPOINTMENT = new AppointmentBuilder()
            .withPatient(ALICE).withAppointmentTime("3000/01/01 10:00", "3000/01/01 11:00")
            .withDescription("Checkup").withPriorityTag("low").build();

    public static final Appointment TODAYAPPOINTMENT = new AppointmentBuilder()
            .withPatient(ALICE)
            .withAppointmentTime(
                    dateTimeToString(LocalDateTime.now()), dateTimeToString(LocalDateTime.now().plusMinutes(5)))
            .withDescription("Appointment Today")
            .withPriorityTag("medium")
            .build();

    public static final Appointment UPCOMINGAPPOINTMENT = new AppointmentBuilder()
            .withPatient(ALICE)
            .withAppointmentTime(
                    dateTimeToString(LocalDateTime.now().plusDays(1)),
                    dateTimeToString(LocalDateTime.now().plusDays(1).plusMinutes(5)))
            .withDescription("Appointment in Future")
            .withPriorityTag("low")
            .build();

    public static final Appointment ANCIENT_APPOINTMENT = new AppointmentBuilder()
            .withPatient(HOON).withAppointmentTime("1800/04/18 15:00", "2023/04/18 17:30")
            .withDescription("Stoned").withPriorityTag("medium").build();

    public static List<Appointment> getTypicalAppointments() {
        return new ArrayList<>(Arrays.asList(APPOINTMENT1, APPOINTMENT2, APPOINTMENT3, APPOINTMENT4));
    }

    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Appointment appointment : getTypicalAppointments()) {
            ab.addAppointment(appointment);
        }
        return ab;
    }
}
