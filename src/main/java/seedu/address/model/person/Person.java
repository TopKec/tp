package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Note note;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Appointment> appointments = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(
        Name name, Phone phone, Email email, Address address, Note note, Set<Tag> tags, Set<Appointment> appointments
    ) {
        requireAllNonNull(name, phone, email, address, tags, appointments);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.note = note;
        this.tags.addAll(tags);
        this.appointments.addAll(appointments);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Note getNote() {
        return note;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }
    /**
     * Returns an immutable appointment set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Appointment> getAppointments() {
        return Collections.unmodifiableSet(appointments);
    }

    /**
     * Returns a formatted string that contains all the details of the Person object for the
     * View command.
     */
    public List<String> getViewDetails() {
        List<String> detailList = new ArrayList<>();

        assert this.getName() != null;
        
        detailList.add(this.getName().fullName.toUpperCase() + "\n");
        detailList.add("\nTAGS: " + (this.getTags().isEmpty()
                ? "-"
                : this.getTags().stream()
                .map(Object::toString)
                .map(str -> str + " ")
                .collect(Collectors.joining())
                .trim()) + "\n"
        );
        detailList.add(StringUtil.SEPARATOR);
        detailList.add("\nDETAILS:\n");
        detailList.add(this.getPhone().isEmpty() ? "-" : this.getPhone().value + "\n");
        detailList.add(this.getEmail().isEmpty() ? "-" : this.getEmail().value + "\n");
        detailList.add(this.getAddress().isEmpty() ? "-" : this.getAddress().value + "\n");
        detailList.add(StringUtil.SEPARATOR);
        detailList.add("\nAPPOINTMENTS:\n");
        detailList.add(this.getAppointments().isEmpty()
                ? "-\n"
                : this.getAppointments().stream()
                .map(Object::toString)
                .map(str -> str + "\n")
                .collect(Collectors.joining()));
        detailList.add(StringUtil.SEPARATOR);
        detailList.add("\nNOTES:\n" + (this.getNote().isEmpty() ? "-" : this.getNote().value));

        return detailList;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && note.equals(otherPerson.note)
                && tags.equals(otherPerson.tags)
                && appointments.equals(otherPerson.appointments);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, appointments);
    }

    @Override
    public String toString() {
        ToStringBuilder returnedString = new ToStringBuilder(this)
                .add("name", name);
        if (!phone.isEmpty()) {
            returnedString.add("phone", phone);
        }
        if (!email.isEmpty()) {
            returnedString.add("email", email);
        }
        if (!address.isEmpty()) {
            returnedString.add("address", address);
        }
        if (!note.isEmpty()) {
            returnedString.add("note", note);
        }
        return returnedString.add("tags", tags).add("appointments", appointments).toString();
    }
}
