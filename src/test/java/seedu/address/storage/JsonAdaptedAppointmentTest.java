package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.util.DateUtil.dateTimeToString;
import static seedu.address.logic.Messages.MESSAGE_PATIENT_DOES_NOT_EXIST;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppointments.APPOINTMENT1;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.appointment.AppointmentDescription;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.person.Name;
import seedu.address.model.tag.PriorityTag;
import seedu.address.testutil.AddressBookBuilder;

public class JsonAdaptedAppointmentTest {
    private static final AddressBook ADDRESS_BOOK = new AddressBookBuilder()
            .withPerson(ALICE).withPerson(BENSON).build();

    private static final String INVALID_PATIENT = "R@chel";
    private static final String NON_EXISTENT_PATIENT = "Gabriel";
    private static final String INVALID_START = "2023-10-10 10:00";
    private static final String INVALID_END = "2023-10-10 11:00";
    private static final String INVALID_DESCRIPTION = "!@#!";

    private static final String VALID_PATIENT = APPOINTMENT1.getPatientName().fullName;
    private static final String VALID_START = dateTimeToString(APPOINTMENT1.getAppointmentTime().getStart());
    private static final String VALID_END = dateTimeToString(APPOINTMENT1.getAppointmentTime().getEnd());
    private static final String VALID_DESCRIPTION = APPOINTMENT1.getAppointmentDescription().value;

    private static final String VALID_PRIORITYTAG = APPOINTMENT1.getPriorityTag().toString();

    @Test
    public void toModelType_validAppointmentDetails_returnsAppointment() throws Exception {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(APPOINTMENT1);
        assertEquals(APPOINTMENT1, appointment.toModelType(ADDRESS_BOOK));
    }

    @Test
    public void toModelType_invalidPatient_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment1 =
                new JsonAdaptedAppointment(INVALID_PATIENT, VALID_START, VALID_END, VALID_DESCRIPTION,
                        VALID_PRIORITYTAG);
        String expectedMessage1 = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage1, () -> appointment1.toModelType(ADDRESS_BOOK));

        JsonAdaptedAppointment appointment2 =
                new JsonAdaptedAppointment(NON_EXISTENT_PATIENT, VALID_START, VALID_END, VALID_DESCRIPTION,
                        VALID_PRIORITYTAG);
        assertThrows(IllegalValueException.class,
                MESSAGE_PATIENT_DOES_NOT_EXIST, () -> appointment2.toModelType(ADDRESS_BOOK));
    }

    @Test
    public void toModelType_nullPatient_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment =
                new JsonAdaptedAppointment(null, VALID_START, VALID_END, VALID_DESCRIPTION,
                        VALID_PRIORITYTAG);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> appointment.toModelType(ADDRESS_BOOK));
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment1 =
                new JsonAdaptedAppointment(VALID_PATIENT, INVALID_START, VALID_END, VALID_DESCRIPTION,
                        VALID_PRIORITYTAG);
        String expectedMessage = AppointmentTime.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> appointment1.toModelType(ADDRESS_BOOK));

        JsonAdaptedAppointment appointment2 =
                new JsonAdaptedAppointment(VALID_PATIENT, VALID_START, INVALID_END, VALID_DESCRIPTION,
                        VALID_PRIORITYTAG);
        assertThrows(IllegalValueException.class, expectedMessage, () -> appointment2.toModelType(ADDRESS_BOOK));
    }

    @Test
    public void toModelType_nullTime_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment1 =
                new JsonAdaptedAppointment(VALID_PATIENT, null, VALID_END, VALID_DESCRIPTION,
                        VALID_PRIORITYTAG);
        String expectedMessage =
                String.format(MISSING_FIELD_MESSAGE_FORMAT, AppointmentTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> appointment1.toModelType(ADDRESS_BOOK));

        JsonAdaptedAppointment appointment2 =
                new JsonAdaptedAppointment(VALID_PATIENT, VALID_START, null, VALID_DESCRIPTION,
                        VALID_PRIORITYTAG);
        assertThrows(IllegalValueException.class, expectedMessage, () -> appointment2.toModelType(ADDRESS_BOOK));
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment =
                new JsonAdaptedAppointment(VALID_PATIENT, VALID_START, VALID_END, INVALID_DESCRIPTION,
                        VALID_PRIORITYTAG);
        String expectedMessage = AppointmentDescription.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> appointment.toModelType(ADDRESS_BOOK));
    }

    @Test
    public void toModelType_nullPriorityTag_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(VALID_PATIENT, VALID_START, VALID_END,
                VALID_DESCRIPTION, null);
        String expectedMessage =
                String.format(MISSING_FIELD_MESSAGE_FORMAT, PriorityTag.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> appointment.toModelType(ADDRESS_BOOK));
    }

}

