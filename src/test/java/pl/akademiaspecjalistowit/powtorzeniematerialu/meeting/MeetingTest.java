package pl.akademiaspecjalistowit.powtorzeniematerialu.meeting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class MeetingTest {

    private Meeting meeting;

    @BeforeEach
    void setUp() {
        meeting = new Meeting(
                "Test Meeting",
                "01:01:2024 12:00",
                Set.of("test1234@example.com"),
                "02:00");
    }

    @Test
    public void making_overlapping_meetings_for_the_same_participants_is_not_possible() {
        //given

        //when
        Throwable thrown = catchThrowable(() -> meeting.checkingForConflictingMeetings(
                "01:01:2024 12:00",
                "test1234@example.com"));

        //then
        assertThat(thrown)
                .isInstanceOf(MeetingException.class)
                .hasMessageContaining("The meeting could not be created for the " +
                        "specified user at the expected time!");
    }
}
