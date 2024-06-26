package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_FRIDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .withAppointments(VALID_APPOINTMENT_FRIDAY)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        List<Appointment> newAppointments = editedAlice.getAppointments().asUnmodifiableObservableList();
        AddressBookStub newData = new AddressBookStub(newPersons, newAppointments);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void addPerson_personInAddressBook_throwDuplicatePersonException() {
        addressBook.addPerson(ALICE);
        assertThrows(DuplicatePersonException.class, () -> addressBook.addPerson(ALICE));
    }

    @Test
    public void addPerson_personNotInAddressBook_success() {
        assertFalse(addressBook.hasPerson(BENSON));
        assertTrue(addressBook.getAppointmentList().isEmpty());
        addressBook.addPerson(BENSON);
        assertTrue(addressBook.hasPerson(BENSON));
        assertFalse(BENSON.getAppointments().isEmpty());
        assertFalse(addressBook.getAppointmentList().isEmpty());
    }

    @Test
    public void removePerson_personInAddressBook_success() {
        addressBook.addPerson(BENSON);
        assertFalse(addressBook.getAppointmentList().isEmpty());

        addressBook.removePerson(BENSON);
        assertFalse(addressBook.hasPerson(BENSON));
        assertTrue(addressBook.getAppointmentList().isEmpty());
    }

    @Test
    public void removePerson_personNotInAddressBook_throwPersonNotFoundException() {
        addressBook.addPerson(BENSON);
        assertTrue(addressBook.hasPerson(BENSON));
        assertThrows(PersonNotFoundException.class, () -> addressBook.removePerson(ALICE));
    }


    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    @Test
    public void appointmentOverlaps_appointmentsInAddressBookDoNotOverlap_returnsFalse() {
        assertFalse(addressBook.appointmentsOverlap(new Appointment(VALID_APPOINTMENT_FRIDAY)));
    }

    @Test
    public void hasAppointment_appointmentInAddressBookOverlap_returnsTrue() {
        addressBook.addAppointment(new Appointment(VALID_APPOINTMENT_FRIDAY));
        assertTrue(addressBook.appointmentsOverlap(new Appointment(VALID_APPOINTMENT_FRIDAY)));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{persons=" + addressBook.getPersonList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons, Collection<Appointment> appointments) {
            this.persons.setAll(persons);
            this.appointments.setAll(appointments);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Appointment> getAppointmentList() {
            return appointments;
        }
    }

}
