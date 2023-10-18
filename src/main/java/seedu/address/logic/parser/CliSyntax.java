package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    public static final Prefix PREFIX_ID = new Prefix("id=");

    /* Patient prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("name=");
    public static final Prefix PREFIX_GENDER = new Prefix("gender=");
    public static final Prefix PREFIX_BIRTHDATE = new Prefix("birthdate=");
    public static final Prefix PREFIX_PHONE = new Prefix("phone=");
    public static final Prefix PREFIX_EMAIL = new Prefix("email=");
    public static final Prefix PREFIX_ADDRESS = new Prefix("address=");
    public static final Prefix PREFIX_TAG = new Prefix("illness=");
    public static final Prefix PREFIX_APPOINTMENT_FROM_START = new Prefix("fromstart=");
    public static final Prefix PREFIX_APPOINTMENT_FROM_END = new Prefix("fromend=");
    public static final Prefix PREFIX_APPOINTMENT_TO_START = new Prefix("tostart=");
    public static final Prefix PREFIX_APPOINTMENT_TO_END = new Prefix("toend=");

    /* Appointment prefix definitions */
    public static final Prefix PREFIX_APPOINTMENT_START = new Prefix("start=");
    public static final Prefix PREFIX_APPOINTMENT_END = new Prefix("end=");
    public static final Prefix PREFIX_APPOINTMENT_DESCRIPTION = new Prefix("description=");
    public static final Prefix PREFIX_APPOINTMENT_PATIENT = new Prefix("patient=");
}
