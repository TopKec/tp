package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_CELINE;
import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_DESC_FRIDAY;
import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_DESC_SUNDAY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_CELINE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_DESC_CELINE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_CELINE;
import static seedu.address.logic.commands.CommandTestUtil.NOTE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NOTE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NOTE_DESC_CELINE;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_CELINE;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SPACE_PRECEDED_PREFIX_ADDRESS;
import static seedu.address.logic.commands.CommandTestUtil.SPACE_PRECEDED_PREFIX_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.SPACE_PRECEDED_PREFIX_EMAIL;
import static seedu.address.logic.commands.CommandTestUtil.SPACE_PRECEDED_PREFIX_LEVEL;
import static seedu.address.logic.commands.CommandTestUtil.SPACE_PRECEDED_PREFIX_NOTE;
import static seedu.address.logic.commands.CommandTestUtil.SPACE_PRECEDED_PREFIX_PHONE;
import static seedu.address.logic.commands.CommandTestUtil.SPACE_PRECEDED_PREFIX_SUBJECT;
import static seedu.address.logic.commands.CommandTestUtil.SPACE_PRECEDED_PREFIX_TAG;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_CELINE;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_MATH;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_MT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_FRIDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_SUNDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_CELINE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_MT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CELINE;
import static seedu.address.testutil.TypicalPersons.NAME_ONLY_CELINE;
import static seedu.address.testutil.TypicalPersons.NO_ADDRESS_AMY;
import static seedu.address.testutil.TypicalPersons.NO_EMAIL_AMY;
import static seedu.address.testutil.TypicalPersons.NO_NOTE_AMY;
import static seedu.address.testutil.TypicalPersons.NO_PHONE_AMY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + NOTE_DESC_BOB + LEVEL_DESC_BOB
                + TAG_DESC_FRIEND + SUBJECT_DESC_BOB, new AddCommand(expectedPerson));


        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + NOTE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND
                        + LEVEL_DESC_BOB + SUBJECT_DESC_MATH,
                new AddCommand(expectedPersonMultipleTags));

        // multiple appointments - all accepted
        Person expectedPersonMultipleAppointments = new PersonBuilder(CELINE)
                .withAppointments(VALID_APPOINTMENT_FRIDAY, VALID_APPOINTMENT_SUNDAY)
                .withSubjects(VALID_SUBJECT_CELINE)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_CELINE + PHONE_DESC_CELINE + EMAIL_DESC_CELINE + ADDRESS_DESC_CELINE
                        + NOTE_DESC_CELINE + APPOINTMENT_DESC_FRIDAY + APPOINTMENT_DESC_SUNDAY
                        + LEVEL_DESC_CELINE + SUBJECT_DESC_CELINE,
                new AddCommand(expectedPersonMultipleAppointments));

        // multiple subjects - all accepted
        Person expectedPersonMultipleSubjects = new PersonBuilder(BOB)
                .withTags(VALID_TAG_FRIEND)
                .withSubjects(VALID_SUBJECT_BOB, VALID_SUBJECT_MT)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + NOTE_DESC_BOB + TAG_DESC_FRIEND
                        + LEVEL_DESC_BOB + SUBJECT_DESC_BOB + SUBJECT_DESC_MT,
                new AddCommand(expectedPersonMultipleSubjects));

        // multiple tags AND appointments - all accepted
        Person expectedPersonMultipleAppointmentsAndTags = new PersonBuilder(BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .withAppointments(VALID_APPOINTMENT_FRIDAY, VALID_APPOINTMENT_SUNDAY)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + NOTE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND
                        + APPOINTMENT_DESC_FRIDAY + APPOINTMENT_DESC_SUNDAY
                        + SUBJECT_DESC_BOB + LEVEL_DESC_BOB,
                new AddCommand(expectedPersonMultipleAppointmentsAndTags));

        // multiple tags, appointments AND subjects - all accepted
        Person expectedPersonMultipleTagsAppointmentsAndSubjects = new PersonBuilder(CELINE)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .withAppointments(VALID_APPOINTMENT_FRIDAY, VALID_APPOINTMENT_SUNDAY)
                .withSubjects(VALID_SUBJECT_CELINE, VALID_SUBJECT_MATH)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_CELINE + PHONE_DESC_CELINE + EMAIL_DESC_CELINE + ADDRESS_DESC_CELINE
                        + NOTE_DESC_CELINE + TAG_DESC_HUSBAND + TAG_DESC_FRIEND
                        + APPOINTMENT_DESC_FRIDAY + APPOINTMENT_DESC_SUNDAY
                        + SUBJECT_DESC_CELINE + SUBJECT_DESC_MATH + LEVEL_DESC_CELINE,
                new AddCommand(expectedPersonMultipleTagsAppointmentsAndSubjects));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + NOTE_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + ADDRESS_DESC_AMY
                        + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags or subjects
        Person expectedPerson = new PersonBuilder(AMY).withTags().removeLevel().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NOTE_DESC_AMY,
                new AddCommand(expectedPerson));
        // empty subject prefix value
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NOTE_DESC_AMY
                + SPACE_PRECEDED_PREFIX_SUBJECT,
                new AddCommand(expectedPerson));
        // empty tag prefix value
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NOTE_DESC_AMY
                + SPACE_PRECEDED_PREFIX_TAG,
                new AddCommand(expectedPerson));
        // empty appointment prefix value
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NOTE_DESC_AMY
                + SPACE_PRECEDED_PREFIX_APPOINTMENT,
                new AddCommand(expectedPerson));

        //No email field
        Person expectedPersonNoEmail = new PersonBuilder(NO_EMAIL_AMY).withTags().removeLevel().build();
        //No prefix
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY + NOTE_DESC_AMY,
                new AddCommand(expectedPersonNoEmail));
        //Empty prefix value
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY + NOTE_DESC_AMY
                + SPACE_PRECEDED_PREFIX_EMAIL,
                new AddCommand(expectedPersonNoEmail));

        //No address field
        Person expectedPersonNoAddress = new PersonBuilder(NO_ADDRESS_AMY).withTags().removeLevel().build();
        //No prefix
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + NOTE_DESC_AMY,
                new AddCommand(expectedPersonNoAddress));
        //Empty prefix value
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + NOTE_DESC_AMY
                + SPACE_PRECEDED_PREFIX_ADDRESS,
                new AddCommand(expectedPersonNoAddress));

        //No phone field
        Person expectedPersonNoPhone = new PersonBuilder(NO_PHONE_AMY).withTags().removeLevel().build();
        //No prefix
        assertParseSuccess(parser, NAME_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NOTE_DESC_AMY,
                new AddCommand(expectedPersonNoPhone));
        //Empty prefix value
        assertParseSuccess(parser, NAME_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NOTE_DESC_AMY
                + SPACE_PRECEDED_PREFIX_PHONE,
                new AddCommand(expectedPersonNoPhone));

        //No Note field
        Person expectedPersonNoNote = new PersonBuilder(NO_NOTE_AMY).withTags().removeLevel().build();
        //No prefix
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCommand(expectedPersonNoNote));
        //Empty prefix value
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + SPACE_PRECEDED_PREFIX_NOTE,
                new AddCommand(expectedPersonNoNote));
        //Multiple empty prefix values
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + SPACE_PRECEDED_PREFIX_NOTE + SPACE_PRECEDED_PREFIX_NOTE,
                new AddCommand(expectedPersonNoNote));

        //No Level field
        Person expectedPersonNoLevel = new PersonBuilder(AMY).withTags().removeLevel().build();
        //No prefix
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + NOTE_DESC_AMY,
                new AddCommand(expectedPersonNoLevel));
        //Empty prefix value
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + NOTE_DESC_AMY + SPACE_PRECEDED_PREFIX_LEVEL,
                new AddCommand(expectedPersonNoLevel));
        //Multiple empty prefix values
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + NOTE_DESC_AMY + SPACE_PRECEDED_PREFIX_LEVEL + SPACE_PRECEDED_PREFIX_LEVEL,
                new AddCommand(expectedPersonNoLevel));

        //Only Name field
        Person expectedPersonNameOnly = new PersonBuilder(NAME_ONLY_CELINE).withTags().removeLevel().build();
        assertParseSuccess(parser, NAME_DESC_CELINE, new AddCommand(expectedPersonNameOnly));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + NOTE_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB
                        + VALID_ADDRESS_BOB + VALID_NOTE_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + NOTE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + NOTE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + NOTE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + NOTE_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + NOTE_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + NOTE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
