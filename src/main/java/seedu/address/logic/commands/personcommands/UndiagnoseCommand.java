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
 * Undiagnoses an existing patient in the address book with illnesses.
 */
public class UndiagnoseCommand extends Command {

    public static final String COMMAND_WORD = "undiagnose";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undiagnoses illnesses of the patient identified "
            + "by the index number used in the displayed person list. \n"
            + "Notes: You can input multiple illnesses with a comma between each illness.\n"
            + "Parameters: INDEX "
            + PREFIX_ILLNESSES + "ILLNESSES\n"
            + "Example: " + COMMAND_WORD
            + " 1 "
            + PREFIX_ILLNESSES + "Fever, Headache";

    public static final String MESSAGE_UNDIAGNOSE_PERSON_SUCCESS = "Undiagnosed Patient: %1$s";
    public static final String MESSAGE_NOT_DIAGNOSED = "At least one field to edit must be provided.";
    public static final String MESSAGE_ILLNESS_NOT_THERE = "The following illnesses were not diagnosed before";
    private final Index targetIndex;
    private final Set<Tag> illnesses;

    /**
     * @param index of the person in the filtered person list to edit
     * @param illnesses illnesses to undiagnose the patient
     */
    public UndiagnoseCommand(Index index, Set<Tag> illnesses) {
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

        Person personToUndiagnose = lastShownList.get(targetIndex.getZeroBased());
        Person undiagnosedPerson = createUndiagnosedPerson(personToUndiagnose, illnesses);

        model.setPerson(personToUndiagnose, undiagnosedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        String illnessesNotOriginallyThere = getIllnessesNotOriginallyThere(personToUndiagnose, illnesses);
        StringBuilder feedbackToUser = new StringBuilder(String.format(MESSAGE_UNDIAGNOSE_PERSON_SUCCESS,
                Messages.format(undiagnosedPerson)));

        if (!illnessesNotOriginallyThere.isEmpty()) {
            feedbackToUser.append("\n");
            feedbackToUser.append(MESSAGE_ILLNESS_NOT_THERE);
            feedbackToUser.append(illnessesNotOriginallyThere);
        }
        return new CommandResult(feedbackToUser.toString(), false, false, false, true);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code illnesses}.
     */
    private static Person createUndiagnosedPerson(Person personToEdit, Set<Tag> illnesses) throws CommandException {
        assert personToEdit != null;

        Set<Tag> updatedTags = new HashSet<>();
        updatedTags.addAll(personToEdit.getTags());
        updatedTags.removeAll(illnesses);

        return new Person(personToEdit.getName(), personToEdit.getGender(), personToEdit.getBirthdate(),
                personToEdit.getPhone(), personToEdit.getEmail(), personToEdit.getAddress(), updatedTags);
    }

    private static String getIllnessesNotOriginallyThere(Person personToEdit,
                                                         Set<Tag> illnesses) throws CommandException {
        assert personToEdit != null;

        StringBuilder tagsNotOriginallyInside = new StringBuilder();

        for (Tag tag: illnesses) {
            if (!personToEdit.getTags().contains(tag)) {
                tagsNotOriginallyInside.append(tag);
            }
        }

        return tagsNotOriginallyInside.toString();
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UndiagnoseCommand)) {
            return false;
        }

        UndiagnoseCommand otherUndiagnoseCommand = (UndiagnoseCommand) other;
        return targetIndex.equals(otherUndiagnoseCommand.targetIndex)
                && illnesses.equals(otherUndiagnoseCommand.illnesses);
    }
}
