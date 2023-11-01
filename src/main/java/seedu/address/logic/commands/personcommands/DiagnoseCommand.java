package seedu.address.logic.commands.personcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ILLNESSES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;



/**
 * Diagnoses an existing patient in the address book with illnesses.
 */
public class DiagnoseCommand extends Command {

    public static final String COMMAND_WORD = "diagnose";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Diagnoses illnesses of the patient identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will not be overwritten by the input values.\n"
            + "Notes: You can input multiple illnesses with a comma between each illness.\n"
            + "Parameters: INDEX "
            + PREFIX_ILLNESSES + "ILLNESSES\n"
            + "Example: " + COMMAND_WORD
            + " 1 "
            + PREFIX_ILLNESSES + "Fever, Headache";

    public static final String MESSAGE_DIAGNOSE_PERSON_SUCCESS = "Diagnosed Patient: %1$s";
    public static final String MESSAGE_NOT_DIAGNOSED = "At least one field to diagnose must be provided.";
    public static final String MESSAGE_ALREADY_DIAGNOSED = "The following illnesses have already "
            + "been diagnosed before: ";
    private final Index targetIndex;
    private final Set<Tag> illnesses;

    /**
     * @param index of the person in the filtered person list to edit
     * @param illnesses illnesses to diagnose the patient
     */
    public DiagnoseCommand(Index index, Set<Tag> illnesses) {
        requireNonNull(index);
        requireNonNull(illnesses);

        this.targetIndex = index;
        this.illnesses = illnesses;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDiagnose = lastShownList.get(targetIndex.getZeroBased());
        Person diagnosedPerson = createDiagnosedPerson(personToDiagnose, illnesses);

        model.setPerson(personToDiagnose, diagnosedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        String duplicateIllnesses = getDuplicateIllnesses(personToDiagnose, illnesses);

        StringBuilder feedbackToUser = new StringBuilder(String.format(MESSAGE_DIAGNOSE_PERSON_SUCCESS,
                Messages.format(diagnosedPerson)));
        if (!duplicateIllnesses.isEmpty()) {
            feedbackToUser.append("\n");
            feedbackToUser.append(MESSAGE_ALREADY_DIAGNOSED);
            feedbackToUser.append(duplicateIllnesses);
        }
        return new CommandResult(feedbackToUser.toString(), false, false, false, true);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code illnesses}.
     */
    private static Person createDiagnosedPerson(Person personToEdit, Set<Tag> illnesses) throws CommandException {
        assert personToEdit != null;

        Set<Tag> updatedTags = new HashSet<>();
        updatedTags.addAll(personToEdit.getTags());
        updatedTags.addAll(illnesses);

        return new Person(personToEdit.getName(), personToEdit.getGender(), personToEdit.getBirthdate(),
                personToEdit.getPhone(), personToEdit.getEmail(), personToEdit.getAddress(), updatedTags);
    }

    private static String getDuplicateIllnesses(Person personToEdit, Set<Tag> illnesses) throws CommandException {
        assert personToEdit != null;

        StringBuilder duplicateTags = new StringBuilder();

        for (Tag tag: illnesses) {
            if (personToEdit.getTags().contains(tag)) {
                duplicateTags.append(tag);
            }
        }

        return duplicateTags.toString();
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DiagnoseCommand)) {
            return false;
        }

        DiagnoseCommand otherDiagnoseCommand = (DiagnoseCommand) other;
        return targetIndex.equals(otherDiagnoseCommand.targetIndex) && illnesses.equals(otherDiagnoseCommand.illnesses);
    }
}
