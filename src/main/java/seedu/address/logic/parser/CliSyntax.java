package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Person prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("name=");
    public static final Prefix PREFIX_GENDER = new Prefix("gender=");
    public static final Prefix PREFIX_BIRTHDATE = new Prefix("birthdate=");
    public static final Prefix PREFIX_PHONE = new Prefix("phone=");
    public static final Prefix PREFIX_EMAIL = new Prefix("email=");
    public static final Prefix PREFIX_ADDRESS = new Prefix("address=");
    public static final Prefix PREFIX_ILLNESSES = new Prefix("illnesses=");

    /* Appointment prefix definitions */
    public static final Prefix PREFIX_APPOINTMENT_START = new Prefix("start=");
    public static final Prefix PREFIX_APPOINTMENT_END = new Prefix("end=");
    public static final Prefix PREFIX_APPOINTMENT_DESCRIPTION = new Prefix("description=");
    public static final Prefix PREFIX_APPOINTMENT_PATIENT = new Prefix("patient=");
    public static final Prefix PREFIX_APPOINTMENT_PRIORITY_TAG = new Prefix("priority=");

}
