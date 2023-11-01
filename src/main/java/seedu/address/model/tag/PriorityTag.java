package seedu.address.model.tag;

import static java.lang.Math.abs;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents a Priority Tag in the records.
 * Guarantees: immutable; name is valid as declared in {@link #isValidPriorityTag(String)}
 */
public class PriorityTag extends Tag implements Comparable<PriorityTag> {

    public static final String MESSAGE_CONSTRAINTS = "Priority tag can only take in values: 'high', 'medium' or 'low'";


    public final String priority;

    /**
     * Constructs a {@code Tag}.
     *
     * @param priority A valid tag name.
     */
    public PriorityTag(String priority) {
        super(priority);
        requireNonNull(priority);
        checkArgument(isValidPriorityTag(priority), MESSAGE_CONSTRAINTS);
        this.priority = priority;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidPriorityTag(String test) {
        String temp = test.toLowerCase();
        return temp.equals("high") || temp.equals("medium") || temp.equals("low");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tag)) {
            return false;
        }

        PriorityTag otherTag = (PriorityTag) other;
        return priority.equals(otherTag.priority);
    }

    @Override
    public int hashCode() {
        return priority.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return priority;
    }

    @Override
    public int compareTo(PriorityTag otherTag) {
        int numerator = priority.charAt(priority.length() - 1)
                - otherTag.priority.charAt(otherTag.priority.length() - 1);
        int denominator = numerator == 0
                ? 1
                : abs(numerator);
        return numerator / denominator;
    }
}
