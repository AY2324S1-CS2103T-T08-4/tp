package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.AddressBook;
import seedu.address.model.person.Patient;

/**
 * Represents an Appointment in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */

public class Appointment {
    private final AppointmentTime appointmentTime;
    private final AppointmentDescription appointmentDescription;
    private int patientId;
    private Patient patient;

    /**
     * Constructs an {@code Appointment}.
     *
     * @param appointmentTime The scheduled time for the appointment.
     * @param appointmentDescription The description for the appointment.
     */
    public Appointment(int patientId, AppointmentTime appointmentTime, AppointmentDescription appointmentDescription) {
        requireAllNonNull(appointmentTime);
        this.patientId = patientId;
        this.appointmentTime = appointmentTime;
        this.patient = null;
        this.appointmentDescription = appointmentDescription;
    }

    /**
     * Constructs an {@code Appointment}.
     *
     * @param patient The patient associated with the appointment.
     * @param appointmentTime The scheduled time for the appointment.
     * @param appointmentDescription The description for the appointment.
     */
    public Appointment(Patient patient, AppointmentTime appointmentTime,
                       AppointmentDescription appointmentDescription) {
        requireAllNonNull(appointmentTime);
        this.appointmentTime = appointmentTime;
        this.patient = patient;
        this.appointmentDescription = appointmentDescription;
    }

    /**
     * Used in the AddAppointmentCommand
     * @param patient The patient associated with the appointment.
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /**
     * Overloaded method to set patient when reading appointments from json file.
     * @param addressBook The AddressBook model
     */
    public void setPatient(AddressBook addressBook) {
        this.patient = addressBook.getPatientList().get(patientId - 1);
    }

    public int getPatientId() {
        return this.patientId;
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

    public Patient getPerson() {
        return this.patient;
    }

    public String getPatientName() {
        return this.patient.getName().fullName;
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
                && appointmentDescription.equals(otherAppointment.appointmentDescription);
    }
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(patient, appointmentTime, appointmentDescription);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("patient", patient)
                .add("appointmentTime", appointmentTime)
                .add("description", appointmentDescription)
                .toString();
    }
}
