package seedu.address.logic.commands.appointmentcommands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showAppointmentAtIndex;
import static seedu.address.testutil.TypicalAppointments.NOCLASHAPPOINTMENT;
import static seedu.address.testutil.TypicalAppointments.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPOINTMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPOINTMENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.testutil.AppointmentTimeBuilder;

public class RescheduleCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexValidTimeUnfilteredList_success() {
        Appointment newAppointment = NOCLASHAPPOINTMENT;
        AppointmentTime newAppointmentTime = new AppointmentTimeBuilder(newAppointment).build();

        Appointment appointmentToReschedule = model.getFilteredAppointmentList()
                .get(INDEX_FIRST_APPOINTMENT.getZeroBased());
        Appointment rescheduledAppointment = new Appointment(appointmentToReschedule.getPerson(),
                newAppointment.getAppointmentTime(), appointmentToReschedule.getAppointmentDescription(),
                appointmentToReschedule.getPriorityTag());

        RescheduleCommand rescheduleCommand = new RescheduleCommand(INDEX_FIRST_APPOINTMENT, newAppointmentTime);
        String expectedMessage = String.format(RescheduleCommand.MESSAGE_SUCCESS,
                Messages.format(rescheduledAppointment));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setAppointment(expectedModel.getFilteredAppointmentList().get(0), rescheduledAppointment);

        assertCommandSuccess(rescheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTimeUnfilteredList_failure() {
        Appointment rescheduledAppointment = model.getFilteredAppointmentList()
                .get(INDEX_SECOND_APPOINTMENT.getZeroBased());
        AppointmentTime appointmentTime = new AppointmentTimeBuilder(rescheduledAppointment).build();
        RescheduleCommand rescheduleCommand = new RescheduleCommand(INDEX_FIRST_APPOINTMENT, appointmentTime);

        String expectedMessage = String.format(Messages.MESSAGE_DUPLICATE_TIMESLOT,
                Messages.format(rescheduledAppointment));

        assertCommandFailure(rescheduleCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAppointmentList().size() + 1);
        Appointment rescheduledAppointment = NOCLASHAPPOINTMENT;
        AppointmentTime appointmentTime = new AppointmentTimeBuilder(rescheduledAppointment).build();
        RescheduleCommand rescheduleCommand = new RescheduleCommand(outOfBoundIndex, appointmentTime);

        assertCommandFailure(rescheduleCommand, model, Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexValidTimeFilteredList_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showAppointmentAtIndex(model, INDEX_SECOND_APPOINTMENT);
        Appointment newAppointment = NOCLASHAPPOINTMENT;
        AppointmentTime newAppointmentTime = new AppointmentTimeBuilder(newAppointment).build();

        Appointment appointmentToReschedule = model.getFilteredAppointmentList()
                .get(INDEX_FIRST_APPOINTMENT.getZeroBased());
        Appointment rescheduledAppointment = new Appointment(appointmentToReschedule.getPerson(),
                newAppointment.getAppointmentTime(), appointmentToReschedule.getAppointmentDescription(),
                appointmentToReschedule.getPriorityTag());
        RescheduleCommand rescheduleCommand = new RescheduleCommand(INDEX_FIRST_APPOINTMENT, newAppointmentTime);
        String expectedMessage = String.format(RescheduleCommand.MESSAGE_SUCCESS,
                Messages.format(rescheduledAppointment));
        expectedModel.setAppointment(expectedModel.getFilteredAppointmentList().get(1), rescheduledAppointment);

        assertCommandSuccess(rescheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTimeFilteredList_failure() {
        Appointment rescheduledAppointment = model.getFilteredAppointmentList()
                .get(INDEX_SECOND_APPOINTMENT.getZeroBased());
        AppointmentTime appointmentTime = new AppointmentTimeBuilder(rescheduledAppointment).build();
        showAppointmentAtIndex(model, INDEX_FIRST_APPOINTMENT);
        RescheduleCommand rescheduleCommand = new RescheduleCommand(INDEX_FIRST_APPOINTMENT, appointmentTime);

        String expectedMessage = String.format(Messages.MESSAGE_DUPLICATE_TIMESLOT,
                Messages.format(rescheduledAppointment));

        assertCommandFailure(rescheduleCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showAppointmentAtIndex(model, INDEX_FIRST_APPOINTMENT);
        Index outOfBoundIndex = Index.fromOneBased(2);
        Appointment rescheduledAppointment = NOCLASHAPPOINTMENT;
        AppointmentTime appointmentTime = new AppointmentTimeBuilder(rescheduledAppointment).build();
        RescheduleCommand rescheduleCommand = new RescheduleCommand(outOfBoundIndex, appointmentTime);

        assertCommandFailure(rescheduleCommand, model, Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }
    @Test
    public void equals() {
        Appointment appointment1 = model.getFilteredAppointmentList()
                .get(INDEX_FIRST_APPOINTMENT.getZeroBased());
        Appointment appointment2 = model.getFilteredAppointmentList()
                .get(INDEX_SECOND_APPOINTMENT.getZeroBased());

        AppointmentTime appointmentTime1 = appointment1.getAppointmentTime();
        AppointmentTime appointmentTime2 = appointment2.getAppointmentTime();

        RescheduleCommand rescheduleFirstCommand = new RescheduleCommand(INDEX_FIRST_APPOINTMENT, appointmentTime1);
        RescheduleCommand rescheduleSecondCommand = new RescheduleCommand(INDEX_SECOND_APPOINTMENT, appointmentTime2);

        // same object -> returns true
        assertTrue(rescheduleFirstCommand.equals(rescheduleFirstCommand));

        // same values -> returns true
        RescheduleCommand rescheduleFirstCommandCopy = new RescheduleCommand(INDEX_FIRST_APPOINTMENT, appointmentTime1);
        assertTrue(rescheduleFirstCommand.equals(rescheduleFirstCommandCopy));

        // different types -> returns false
        assertFalse(rescheduleFirstCommand.equals(1));

        // null -> returns false
        assertFalse(rescheduleFirstCommand.equals(null));

        // different appointment -> returns false
        assertFalse(rescheduleFirstCommand.equals(rescheduleSecondCommand));
    }
}
