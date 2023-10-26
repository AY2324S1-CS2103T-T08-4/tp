package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDescription;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tag.PriorityTag;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static seedu.address.commons.util.DateUtil.dateTimeToString;
import static seedu.address.logic.Messages.MESSAGE_PATIENT_DOES_NOT_EXIST;

/**
 * Jackson-friendly version of {@link Appointment}.
 */
class JsonAdaptedAppointment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Patient's %s field is missing!";

    private final String patientName;
    private final String start;
    private final String end;
    private final String description;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();


    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given appointment details.
     */
    @JsonCreator
    public JsonAdaptedAppointment(@JsonProperty("patientName") String patientName,
                                  @JsonProperty("start") String start,
                                  @JsonProperty("end") String end,
                                  @JsonProperty("description") String description),
                                  @JsonProperty("tagged") List<JsonAdaptedTag> tagged {


        this.patientName = patientName;
        this.start = start;
        this.end = end;
        this.description = description;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }
        /**
         * Converts a given {@code Appointment} into this class for Jackson use.
         */
    public JsonAdaptedAppointment(Appointment source) {
        patientName = String.valueOf(source.getPatientName());
        start = dateTimeToString(source.getStartTime());
        end = dateTimeToString(source.getEndTime());
        description = source.getAppointmentDescription().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

        /**
         * Converts this Jackson-friendly adapted appointment object into the model's {@code Appointment} object.
         *
         * @throws IllegalValueException if there were any data constraints violated in the adapted appointment.
         */
        public Appointment toModelType(AddressBook addressBook) throws IllegalValueException {

        final List<PriorityTag> appointmentTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            appointmentTags.add((PriorityTag) tag.toModelType());
        }

        if (patientName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(patientName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        if (!addressBook.hasPerson(new Name(patientName))) {
            throw new IllegalValueException(MESSAGE_PATIENT_DOES_NOT_EXIST);
        }
        final Person patient = addressBook
                .getPersonList().stream().filter(person -> person.getName().fullName.equals(patientName))
                .collect(Collectors.toList()).get(0);

        if (start == null || end == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, AppointmentTime.class.getSimpleName()));
        }
        LocalDateTime startDateTime;
        LocalDateTime endDateTime;
        try {
            startDateTime = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
            endDateTime = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(AppointmentTime.MESSAGE_CONSTRAINTS);
        }
        if (!AppointmentTime.isValidAppointmentTime(startDateTime, endDateTime)) {
            throw new IllegalValueException(AppointmentTime.MESSAGE_CONSTRAINTS);
        }
        final AppointmentTime modelAppointmentTime = new AppointmentTime(startDateTime, endDateTime);

        if (description == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, AppointmentDescription.class.getSimpleName()));
        }
        if (!AppointmentDescription.isValidAppointmentDescription(description)) {
            throw new IllegalValueException(AppointmentDescription.MESSAGE_CONSTRAINTS);
        }
        final AppointmentDescription modelAppointmentDescription = new AppointmentDescription(description);

        final Set<PriorityTag> modelTags = new HashSet<>(appointmentTags);

        PriorityTag firstEl = modelTags.iterator().next();
        return new Appointment(
                patient.getName(), modelAppointmentTime, modelAppointmentDescription, firstEl);
    }

    }
}
