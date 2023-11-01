package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.history.UserHistoryManager;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;

public class UndoCommandTest {
    //Static commands used for testing

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~PATIENT COMMANDS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private static final String SAMPLE_ADD_COMMAND_1 =
            "add name=John Doe gender=MALE birthdate=2000/10/20 phone=98765432 "
                    + "email=johnd@example.com address=311, Clementi Ave 2, #02-25 illnesses=flu, fever";
    private static final String SAMPLE_ADD_COMMAND_2 =
            "add name=Jane Doe gender=FEMALE birthdate=2003/02/20 phone=98765432 "
                    + "email=janed@example.com address=311, Bedok Ave 3, #04-48 illnesses=fever";
    private static final String SAMPLE_DELETE_COMMAND = "delete 1";
    private static final String SAMPLE_EDIT_COMMAND = "edit 1 phone=91272464";
    private static final String SAMPLE_FIND_COMMAND = "find john";
    private static final String SAMPLE_LIST_COMMAND = "list";

    private static final String SAMPLE_DIAGNOSE_COMMAND = "diagnose 1 illnesses=Covid19, Covid20";

    private static final String SAMPLE_UNDIAGNOSE_COMMAND = "undiagnose 1 illnesses=flu, fever";


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~APPOINTMENT COMMANDS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private static final String SAMPLE_APPOINTMENTS_COMMAND = "appointments";
    private static final String SAMPLE_CANCEL_COMMAND = "cancel 1";

    private static final String SAMPLE_RESCHEDULE_COMMAND = "reschedule 1 start=2023/05/02 09:00 "
            + "end=2023/05/02 11:00 ";

    private static final String SAMPLE_SCHEDULE_COMMAND = "schedule patient=John Doe "
            + "start=2023/10/20 12:00 end=2023/10/20 13:00 "
            + "description=Follow up on Chest X-Ray "
            + "priority=high";

    private static final String SAMPLE_SCHEDULE_ANCIENT_APPOINTMENT_COMMAND = "schedule patient=John Doe "
            + "start=1800/10/20 12:00 end=1800/10/20 13:00 "
            + "description=Injuries from getting stoned "
            + "priority=medium";

    private static final String SAMPLE_TODAY_COMMAND = "today";

    private static final String SAMPLE_UPCOMING_COMMAND = "today";
    private static final String SAMPLE_FIND_PATIENT_APPOINTMENT_COMMAND = "find-appointment john";

    private static final String SAMPLE_SORT_COMMAND = "sort";

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~MISC COMMANDS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private static final String SAMPLE_HELP_COMMAND = "help";
    private static final String SAMPLE_UNDO_COMMAND = "undo";
    private static final String SAMPLE_REDO_COMMAND = "redo";

    @TempDir
    public Path testFolder;
    private StorageManager storageManager;
    private Model model = new ModelManager();
    private Logic logic;
    private UserHistoryManager historyManager;


    /**
     * Initializes a dummy ModelManager instance before each test.
     */
    @BeforeEach
    public void setUpDummyModelManager() {
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storageManager);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~General Testing of UndoCommand~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Test basic undo functionality
     */
    @Test
    public void testUndo() throws CommandException, ParseException {
        CommandResult commandResult1 = logic.execute(SAMPLE_ADD_COMMAND_1);
        int sizeBefore = model.getAddressBook().getPersonList().size();
        model.undoHistory();
        int sizeAfter = model.getAddressBook().getPersonList().size();

        //asserts that the size of patient list before undo is not equal to the size after undo is executed.
        assertNotEquals(sizeBefore, sizeAfter);
    }

    /**
     * Test undo after running multiple commands
     */
    @Test
    public void testConsecutiveCommandFollowedByUndo() throws CommandException, ParseException {
        CommandResult commandResult1 = logic.execute(SAMPLE_ADD_COMMAND_1);
        CommandResult commandResult2 = logic.execute(SAMPLE_ADD_COMMAND_2);
        int sizeBefore = model.getAddressBook().getPersonList().size();
        CommandResult undoCommand1 = new UndoCommand().execute(model);
        int sizeAfter = model.getAddressBook().getPersonList().size();

        assertEquals(sizeBefore, sizeAfter + 1);
    }

    /**
     * Test multiple undo after running multiple commands
     */
    @Test
    public void testConsecutiveCommandFollowedByConsecutiveUndo() throws CommandException, ParseException {
        CommandResult commandResult1 = logic.execute(SAMPLE_ADD_COMMAND_1);
        CommandResult commandResult2 = logic.execute(SAMPLE_ADD_COMMAND_2);
        int sizeBefore = model.getAddressBook().getPersonList().size();
        CommandResult undoCommand1 = new UndoCommand().execute(model);
        CommandResult undoCommand2 = new UndoCommand().execute(model);
        int sizeAfter = model.getAddressBook().getPersonList().size();

        assertEquals(sizeBefore, sizeAfter + 2);
    }

    /**
     * Test multiple undo after running a single command
     */
    @Test
    public void testSingleCommandFollowedByConsecutiveUndo() throws CommandException, ParseException {
        CommandResult commandResult1 = logic.execute(SAMPLE_ADD_COMMAND_1);
        try {
            CommandResult undoCommand1 = new UndoCommand().execute(model);
            CommandResult undoCommand2 = new UndoCommand().execute(model);
        } catch (CommandException e) {
            assertEquals(model.getUserHistoryManager().getUndoHistorySize(), 1);
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Integration Testing of UndoCommand with patient commands~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Test undo functionality with add command.
     * Add command can be undone
     */
    @Test
    public void testAddCommandFollowedByUndo() throws CommandException, ParseException {
        CommandResult commandResult1 = logic.execute(SAMPLE_ADD_COMMAND_1);
        model.undoHistory();

        boolean containsSamplePatient = false;

        for (Person patient : model.getAddressBook().getPersonList()) {
            if (patient.getName().toString().equals("John Doe")) {
                containsSamplePatient = true;
            }
        }

        assertFalse(containsSamplePatient);
    }


    /**
     * Test undo functionality with delete command.
     * Delete command can be undone
     */
    @Test
    public void testDeleteCommandFollowedByUndo() throws CommandException, ParseException {
        CommandResult commandResult1 = logic.execute(SAMPLE_ADD_COMMAND_1);
        CommandResult commandResult2 = logic.execute(SAMPLE_DELETE_COMMAND);
        model.undoHistory();

        boolean containsSamplePatient = false;

        for (Person patient : model.getAddressBook().getPersonList()) {
            if (patient.getName().toString().equals("John Doe")) {
                containsSamplePatient = true;
            }
        }

        assertTrue(containsSamplePatient);
    }

    /**
     * Test undo functionality with edit command.
     * Edit command can be undone
     */
    @Test
    public void testEditCommandFollowedByUndo() throws CommandException, ParseException {
        CommandResult commandResult1 = logic.execute(SAMPLE_ADD_COMMAND_1);
        CommandResult commandResult2 = logic.execute(SAMPLE_EDIT_COMMAND);
        model.undoHistory();

        boolean isPhoneEdited = true;

        for (Person person : model.getAddressBook().getPersonList()) {
            if (person.getName().toString().equals("John Doe")
                    && person.getPhone().toString().equals("98765432")) {
                isPhoneEdited = false;
            }
        }

        assertFalse(isPhoneEdited);
    }

    /**
     * Test undo functionality with find command.
     * Find command cannot be undone
     */
    @Test
    public void testFindCommandFollowedByUndo() throws CommandException, ParseException {
        CommandResult commandResult1 = logic.execute(SAMPLE_ADD_COMMAND_1);
        CommandResult commandResult2 = logic.execute(SAMPLE_FIND_COMMAND);

        int sizeBeforeUndo = model.getFilteredPersonList().size();
        model.undoHistory();
        int sizeAfterUndo = model.getFilteredPersonList().size();

        //add command is undone instead of find command
        assertEquals(sizeBeforeUndo, sizeAfterUndo + 1);
    }

    /**
     * Test undo functionality with Patients command.
     * Patients command cannot be undone
     */
    @Test
    public void testPatientsCommandFollowedByUndo() throws CommandException, ParseException {
        CommandResult commandResult1 = logic.execute(SAMPLE_ADD_COMMAND_1);
        CommandResult commandResult2 = logic.execute(SAMPLE_LIST_COMMAND);

        int sizeBeforeUndo = model.getFilteredPersonList().size();
        model.undoHistory();
        int sizeAfterUndo = model.getFilteredPersonList().size();

        //add command is undone instead of list command
        assertEquals(sizeBeforeUndo, sizeAfterUndo + 1);
    }

    /**
     * Test undo functionality with Diagnose command.
     * Diagnose command can be undone
     */
    @Test
    public void testDiagnoseCommandFollowedByUndo() throws CommandException, ParseException {
        logic.execute(SAMPLE_ADD_COMMAND_1);
        Model typicalModel = new ModelManager(model.getAddressBook(), model.getUserPrefs());

        assertTrue(typicalModel.getAddressBook().equals(
                model.getAddressBook()));

        logic.execute(SAMPLE_DIAGNOSE_COMMAND);

        assertFalse(typicalModel.getAddressBook().equals(
                model.getAddressBook()));

        model.undoHistory();

        assertTrue(typicalModel.getAddressBook().equals(
                model.getAddressBook()));
    }

    /**
     * Test undo functionality with Undiagnose command.
     * Undiagnose command can be undone
     */
    @Test
    public void testUndiagnoseCommandFollowedByUndo() throws CommandException, ParseException {
        logic.execute(SAMPLE_ADD_COMMAND_1);
        Model typicalModel = new ModelManager(model.getAddressBook(), model.getUserPrefs());

        assertTrue(typicalModel.getAddressBook().equals(
                model.getAddressBook()));

        logic.execute(SAMPLE_UNDIAGNOSE_COMMAND);

        assertFalse(typicalModel.getAddressBook().equals(
                model.getAddressBook()));

        model.undoHistory();

        assertTrue(typicalModel.getAddressBook().equals(
                model.getAddressBook()));
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Integration Testing of UndoCommand with appointment commands~~~~~~~~~~~~~~~~~~~~~
    /**
     * Test undo functionality with appointments command.
     * Appointments command cannot be undone
     */
    @Test
    public void testAppointmentsCommandFollowedByUndo() throws CommandException, ParseException {
        CommandResult commandResult1 = logic.execute(SAMPLE_APPOINTMENTS_COMMAND);

        try {
            CommandResult undoCommand = new UndoCommand().execute(model);
        } catch (CommandException e) {
            assertEquals(model.getUserHistoryManager().getUndoHistorySize(), 1);
        }
    }

    /**
     * Test undo functionality with cancel command.
     * Cancel command can be undone
     */
    @Test
    public void testCancelCommandFollowedByUndo() throws CommandException, ParseException {
        CommandResult commandResult1 = logic.execute(SAMPLE_ADD_COMMAND_1);
        CommandResult commandResult2 = logic.execute(SAMPLE_SCHEDULE_COMMAND);
        CommandResult commandResult3 = logic.execute(SAMPLE_CANCEL_COMMAND);

        model.undoHistory();

        assertEquals(1, model.getAddressBook().getAppointmentList().size());
    }

    /**
     * Test undo functionality with find patient appointment command.
     * Find appointment command cannot be undone
     */
    @Test
    public void testFindAppointmentCommandFollowedByUndo() throws CommandException, ParseException {
        CommandResult commandResult1 = logic.execute(SAMPLE_ADD_COMMAND_1);
        CommandResult commandResult2 = logic.execute(SAMPLE_SCHEDULE_COMMAND);
        CommandResult commandResult3 = logic.execute(SAMPLE_FIND_PATIENT_APPOINTMENT_COMMAND);

        model.undoHistory();

        //schedule command is undone instead of appointment find command
        assertEquals(0, model.getAddressBook().getAppointmentList().size());
    }

    /**
     * Test undo functionality with reschedule command.
     * Reschedule command can be undone
     */
    @Test
    public void testRescheduleCommandFollowedByUndo() throws CommandException, ParseException {
        CommandResult commandResult1 = logic.execute(SAMPLE_ADD_COMMAND_1);
        CommandResult commandResult2 = logic.execute(SAMPLE_SCHEDULE_COMMAND);
        Appointment appointmentBefore = model.getAddressBook().getAppointmentList().get(0);
        CommandResult commandResult3 = logic.execute(SAMPLE_RESCHEDULE_COMMAND);

        model.undoHistory();

        Appointment appointmentAfter = model.getAddressBook().getAppointmentList().get(0);

        assertEquals(appointmentBefore, appointmentAfter);
    }

    /**
     * Test undo functionality with schedule command.
     * Schedule command can be undone
     */
    @Test
    public void testScheduleCommandFollowedByUndo() throws CommandException, ParseException {
        CommandResult commandResult1 = logic.execute(SAMPLE_ADD_COMMAND_1);
        CommandResult commandResult2 = logic.execute(SAMPLE_SCHEDULE_COMMAND);

        model.undoHistory();

        assertEquals(0, model.getAddressBook().getAppointmentList().size());
    }

    /**
     * Test undo functionality with today command.
     * Today command cannot be undone
     */
    @Test
    public void testTodayCommandFollowedByUndo() throws CommandException, ParseException {
        CommandResult commandResult1 = logic.execute(SAMPLE_TODAY_COMMAND);

        try {
            CommandResult undoCommand = new UndoCommand().execute(model);
        } catch (CommandException e) {
            assertEquals(model.getUserHistoryManager().getUndoHistorySize(), 1);
        }
    }

    /**
     * Test undo functionality with upcoming command.
     * Upcoming command cannot be undone
     */
    @Test
    public void testUpcomingCommandFollowedByUndo() throws CommandException, ParseException {
        CommandResult commandResult1 = logic.execute(SAMPLE_UPCOMING_COMMAND);

        try {
            CommandResult undoCommand = new UndoCommand().execute(model);
        } catch (CommandException e) {
            assertEquals(model.getUserHistoryManager().getUndoHistorySize(), 1);
        }
    }

    /**
     * Test undo functionality with Sort command.
     * Sort command can be undone
     */
    @Test
    public void testSortCommandFollowedByUndo() throws CommandException, ParseException {
        logic.execute(SAMPLE_ADD_COMMAND_1);
        logic.execute(SAMPLE_SCHEDULE_COMMAND);
        logic.execute(SAMPLE_SCHEDULE_ANCIENT_APPOINTMENT_COMMAND);
        Model typicalModel = new ModelManager(model.getAddressBook(), model.getUserPrefs());

        assertTrue(typicalModel.getAddressBook().equals(
                model.getAddressBook()));

        logic.execute(SAMPLE_SORT_COMMAND);

        assertFalse(typicalModel.getAddressBook().equals(
                model.getAddressBook()));

        model.undoHistory();

        assertTrue(typicalModel.getAddressBook().equals(
                model.getAddressBook()));
    }
}
