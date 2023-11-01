package seedu.address.model.tag;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PriorityTagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PriorityTag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidPriorityTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new PriorityTag(invalidPriorityTagName));
    }

    @Test
    public void isValidPriorityTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> PriorityTag.isValidTagName(null));
    }

}
