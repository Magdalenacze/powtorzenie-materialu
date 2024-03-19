package pl.akademiaspecjalistowit.powtorzeniematerialu.meeting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class MeetingRepositoryTest {

    private MeetingRepository meetingRepository;

    @BeforeEach
    void setUp() {
        meetingRepository = MeetingRepository.getInstance();
    }

    @Test
    void should_find_existing_meetings_for_the_given_participantEmails_succesfully() {
        // GIVEN
        meetingRepository.save(new Meeting(
                "First Meeting",
                "01:03:2024 11:00",
                Set.of("user11@example.com", "user12@example.com", "user13@example.com"),
                "01:00"));
        meetingRepository.save(new Meeting(
                "Second Meeting",
                "02:03:2024 12:00",
                Set.of("user21@example.com", "user22@example.com", "user23@example.com"),
                "02:00"));
        meetingRepository.save(new Meeting(
                "Third Meeting",
                "03:03:2024 13:00",
                Set.of("user31@example.com", "user32@example.com", "user33@example.com"),
                "03:00"));
        Set<String> participantEmails = Set.of("user11@example.com", "user22@example.com", "user3@example.com");

        // WHEN
        List<Meeting> result = meetingRepository.findAllByParticipantEmails(participantEmails);

        // THEN
        assertThat(result).hasSize(2);
    }

    @Test
    void should_not_allow_to_find_meetings_if_they_are_missing_for_the_given_participantEmails() {
        // GIVEN
        meetingRepository.save(new Meeting(
                "First Meeting",
                "01:03:2024 11:00",
                Set.of("user11@example.com", "user12@example.com", "user13@example.com"),
                "01:00"));
        meetingRepository.save(new Meeting(
                "Second Meeting",
                "02:03:2024 12:00",
                Set.of("user21@example.com", "user22@example.com", "user23@example.com"),
                "02:00"));
        meetingRepository.save(new Meeting(
                "Third Meeting",
                "03:03:2024 13:00",
                Set.of("user31@example.com", "user32@example.com", "user33@example.com"),
                "03:00"));
        Set<String> participantEmails = Set.of("user1@example.com", "user2@example.com", "user3@example.com");

        // WHEN
        List<Meeting> result = meetingRepository.findAllByParticipantEmails(participantEmails);

        // THEN
        assertThat(result).hasSize(0);
    }
}
