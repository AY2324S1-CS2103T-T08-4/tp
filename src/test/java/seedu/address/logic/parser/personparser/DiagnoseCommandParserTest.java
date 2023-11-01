package seedu.address.logic.parser.personparser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC_TWO_INVALID_ILLNESSES;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_DUPLICATE_ILLNESS;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_ONE_ILLNESS;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_TWO_ILLNESSES;
import static seedu.address.logic.commands.CommandTestUtil.createTypicalIllnessSet;
import static seedu.address.logic.commands.CommandTestUtil.createTypicalIllnessesSet;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ILLNESSES;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.personcommands.DiagnoseCommand;
import seedu.address.model.tag.Tag;



public class DiagnoseCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_ILLNESSES;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DiagnoseCommand.MESSAGE_USAGE);

    private DiagnoseCommandParser parser = new DiagnoseCommandParser();
    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, TAG_DESC_ONE_ILLNESS, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValues_failure() {
        assertParseFailure(parser, " 1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid illness
        assertParseFailure(parser, " 1" + INVALID_TAG_DESC_TWO_INVALID_ILLNESSES,
                Tag.MESSAGE_CONSTRAINTS); // invalid illnesses
    }

    @Test
    public void parse_allFieldsSpecifiedOneIllness_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_DESC_ONE_ILLNESS;

        DiagnoseCommand expectedCommand = new DiagnoseCommand(targetIndex, createTypicalIllnessSet());

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_allFieldsSpecifiedMultipleIllnesses_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_DESC_TWO_ILLNESSES;

        DiagnoseCommand expectedCommand = new DiagnoseCommand(targetIndex, createTypicalIllnessesSet());

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_allFieldsSpecifiedDuplicateIllness_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_DESC_DUPLICATE_ILLNESS;

        DiagnoseCommand expectedCommand = new DiagnoseCommand(targetIndex, createTypicalIllnessSet());

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
