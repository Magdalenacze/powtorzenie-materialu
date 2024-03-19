package pl.akademiaspecjalistowit.powtorzeniematerialu.meeting;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class MeetingServiceTest {

    private MeetingServiceImpl meetingService;

    @BeforeEach
    void setUp() {
        meetingService = MeetingServiceImpl.getInstance();
    }

    @Test
    void should_create_meeting_correctly() {
        // GIVEN
        String meetingName = "Test Meeting";
        String meetingDateTimeString = "01:01:2024 12:00";
        Set<String> participantEmails = new HashSet<>();
        participantEmails.add("test@example.com");
        String meetingDuration = "02:00";

        // WHEN
        Meeting result =
            meetingService.createNewMeeting(meetingName, meetingDateTimeString, participantEmails, meetingDuration);

        // THEN
        List<Meeting> allMeetings = meetingService.getAllMeetings();
        assertThat(allMeetings).contains(result);
    }

    @Test
    void making_overlapping_meetings_for_different_participants_is_possible() {
        // GIVEN
        String meetingName = "Test Meeting";
        String meetingDateTimeString = "01:01:2024 12:00";
        Set<String> participantEmails = Set.of("test123@example.com");
        String meetingDuration = "02:00";
        Meeting existingMeeting =
            meetingService.createNewMeeting(meetingName, meetingDateTimeString, participantEmails, meetingDuration);

        String overlappingMeetingName = "Test Meeting";
        String overlappingMeetingDateTimeString = "01:01:2024 12:10";
        Set<String> overlappingParticipantEmails = Set.of("test1234@example.com");
        String OverlappingMeetingDuration = "01:00";

        // WHEN
        Meeting overlappingMeeting = meetingService
            .createNewMeeting(overlappingMeetingName,
                overlappingMeetingDateTimeString,
                overlappingParticipantEmails,
                OverlappingMeetingDuration);

        // THEN
        List<Meeting> allMeetings = meetingService.getAllMeetings();
        assertThat(allMeetings).hasSize(2);
    }

    @Test
    void making_overlapping_meeting_after_starting_an_existing_meeting_for_these_same_participants_is_not_possible() {
        // GIVEN
        String meetingName = "Test Meeting";
        String meetingDateTimeString = "01:01:2024 12:00";
        Set<String> participantEmails = Set.of("test123@example.com");
        String meetingDuration = "02:00";
        Meeting existingMeeting =
            meetingService.createNewMeeting(meetingName, meetingDateTimeString, participantEmails, meetingDuration);

        String overlappingMeetingName = "Test Meeting";
        String overlappingMeetingDateTimeString = "01:01:2024 12:10";
        Set<String> overlappingParticipantEmails = Set.of("test123@example.com");
        String OverlappingMeetingDuration = "01:00";

        // WHEN
        Executable e = () -> meetingService
            .createNewMeeting(overlappingMeetingName,
                overlappingMeetingDateTimeString,
                overlappingParticipantEmails,
                OverlappingMeetingDuration);

        // THEN
        assertThrows(MeetingException.class, e);
        List<Meeting> allMeetings = meetingService.getAllMeetings();
        assertThat(allMeetings).hasSize(1);
    }

    @Test
    void making_overlapping_meeting_before_starting_an_existing_meeting_for_these_same_participants_is_not_possible() {
        // GIVEN
        String meetingName = "Test Meeting";
        String meetingDateTimeString = "01:01:2024 12:00";
        Set<String> participantEmails = Set.of("test123@example.com");
        String meetingDuration = "01:00";
        Meeting existingMeeting =
                meetingService.createNewMeeting(meetingName, meetingDateTimeString, participantEmails, meetingDuration);

        String overlappingMeetingName = "Test Meeting";
        String overlappingMeetingDateTimeString = "01:01:2024 11:00";
        Set<String> overlappingParticipantEmails = Set.of("test123@example.com");
        String OverlappingMeetingDuration = "02:00";

        // WHEN
        Executable e = () -> meetingService
                .createNewMeeting(overlappingMeetingName,
                        overlappingMeetingDateTimeString,
                        overlappingParticipantEmails,
                        OverlappingMeetingDuration);

        // THEN
        assertThrows(MeetingException.class, e);
        List<Meeting> allMeetings = meetingService.getAllMeetings();
        assertThat(allMeetings).hasSize(1);
    }

    @Test
    void should_delete_meeting_correctly() {
        // GIVEN
        String meetingName = "Existing Meeting";
        String meetingDateTimeString = "05:03:2024 09:00";
        Set<String> participantEmails = Set.of("user@example.com");
        String meetingDuration = "01:00";
        Meeting existingMeeting = meetingService.createNewMeeting(meetingName,
                meetingDateTimeString, participantEmails, meetingDuration);

        // WHEN
        meetingService.deleteExistingMeeting(existingMeeting);

        // THEN
        List<Meeting> allMeetings = meetingService.getAllMeetings();
        assertThat(allMeetings).isEmpty();
    }

    @Test
    void should_throw_exception_if_no_meetings_are_found() {
        // GIVEN
        String meetingName = "Existing Meeting";
        String meetingDateTimeString = "05:03:2024 09:00";
        Set<String> participantEmails = Set.of("user@example.com");
        String meetingDuration = "01:00";
        meetingService.createNewMeeting(meetingName, meetingDateTimeString,
                participantEmails, meetingDuration);
        Meeting newMeeting = new Meeting(
                "New Meeting",
                "05:03:2024 11:00",
                Set.of("user@example.com"),
                "01:00");

        // WHEN
        Throwable thrown = catchThrowable(() -> meetingService.deleteExistingMeeting(newMeeting));

        // THEN
        Assertions.assertThat(thrown)
                .isInstanceOf(MeetingException.class)
                .hasMessageContaining("Niestety nie znaleziono żadnego spotkania!");
    }
}