package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Patient's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class Gender {
    public static final String MESSAGE_CONSTRAINTS = "Gender should be either MALE or FEMALE";

    /*
     * The first character of the birthdate must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "MALE|FEMALE";

    public final String value;

    /**
     * Constructs a {@code Gender}.
     *
     * @param gender A valid gender.
     */
    public Gender(String gender) {
        requireNonNull(gender);
        value = gender;
    }

    /**
     * Returns if a given string is a valid gender.
     */
    public static boolean isValidGender(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Gender // instanceof handles nulls
                && value.equals(((Gender) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
