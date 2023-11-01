package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tag.PriorityTag;

/**
 * Represents an Appointment in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Appointment implements Comparable<Appointment> {
    private final AppointmentTime appointmentTime;
    private final AppointmentDescription appointmentDescription;
    private final Name patientName;
    private Person patient;
    private PriorityTag priorityTag;

    /**
     * Constructs an {@code Appointment}.
     *
     * @param patientName The name of the patient associated with the appointment.
     * @param appointmentTime The scheduled time for the appointment.
     * @param appointmentDescription The description for the appointment.
     */
    public Appointment(
            Name patientName, AppointmentTime appointmentTime, AppointmentDescription appointmentDescription,
            PriorityTag priorityTag) {
        requireAllNonNull(patientName, appointmentTime, appointmentDescription);
        this.patientName = patientName;
        this.appointmentTime = appointmentTime;
        this.patient = null;
        this.appointmentDescription = appointmentDescription;
        this.priorityTag = priorityTag;
    }

    /**
     * Constructs an {@code Appointment}.
     *
     * @param patient The patient associated with the appointment.
     * @param appointmentTime The scheduled time for the appointment.
     * @param appointmentDescription The description for the appointment.
     */
    public Appointment(Person patient, AppointmentTime appointmentTime, AppointmentDescription appointmentDescription,
                       PriorityTag priorityTag) {
        requireAllNonNull(appointmentTime);
        this.appointmentTime = appointmentTime;
        this.patient = patient;
        this.patientName = patient.getName();
        this.appointmentDescription = appointmentDescription;
        this.priorityTag = priorityTag;
    }

    /**
     * Used in the AddAppointmentCommand
     * @param patient The patient associated with the appointment.
     */
    public void setPatient(Person patient) {
        this.patient = patient;
    }

    public Name getPatientName() {
        return this.patientName;
    }

    public AppointmentTime getAppointmentTime() {
        return this.appointmentTime;
    }

    public LocalDateTime getStartTime() {
        return this.appointmentTime.getStart();
    }

    public LocalDateTime getEndTime() {
        return this.appointmentTime.getEnd();
    }

    public AppointmentDescription getAppointmentDescription() {
        return this.appointmentDescription;
    }

    public Person getPerson() {
        return this.patient;
    }

    public PriorityTag getPriorityTag() {
        return this.priorityTag;
    }

    /**
     * Returns true if both appointments have the same time.
     * This defines a weaker notion of equality between two appointments.
     */
    public boolean isSameAppointment(Appointment otherAppointment) {
        if (otherAppointment == this) {
            return true;
        }

        return otherAppointment != null
                && otherAppointment.getAppointmentTime().equals(getAppointmentTime());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherAppointment = (Appointment) other;
        return patient.equals(otherAppointment.patient)
                && appointmentTime.equals(otherAppointment.appointmentTime)
                && appointmentDescription.equals(otherAppointment.appointmentDescription)
                && priorityTag.equals(otherAppointment.priorityTag);
    }
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(patient, appointmentTime, appointmentDescription);
    }

    @Override
    public int compareTo(Appointment otherAppointment) {
        return appointmentTime.compareTo(otherAppointment.appointmentTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("patient", patient)
                .add("appointmentTime", appointmentTime)
                .add("description", appointmentDescription)
                .add("priorityTag", priorityTag)
                .toString();
    }
}
