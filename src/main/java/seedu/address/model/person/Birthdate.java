package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Patient's birthdate in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthdate(String)}
 */
public class Birthdate {
    public static final String MESSAGE_CONSTRAINTS = "Birthdates should be of the form YYYY/MM/DD";

    public static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public final String value;

    /**
     * Constructs an {@code Birthdate}.
     *
     * @param birthdate A valid birthdate.
     */
    public Birthdate(String birthdate) {
        requireNonNull(birthdate);
        value = birthdate;
    }

    /**
     * Returns if a given string is a valid birthdate.
     */
    public static boolean isValidBirthdate(String test) {
        try {
            LocalDate.parse(test, FORMAT);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthdate // instanceof handles nulls
                && value.equals(((Birthdate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
