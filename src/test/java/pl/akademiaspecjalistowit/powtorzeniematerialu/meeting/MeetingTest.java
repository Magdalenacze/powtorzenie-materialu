package pl.akademiaspecjalistowit.powtorzeniematerialu.meeting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MeetingTest {

    @Test
    void should_throw_exception_for_overlapping_meetings() {
        // GIVEN
        Meeting meeting = new Meeting(
                "Existing Meeting",
                "05:03:2024 09:00",
                Set.of("user@example.com"),
                "01:00");

        // WHEN
        Throwable thrown = catchThrowable(() -> meeting.checkingForConflictingMeetings(
                "05:03:2024 09:30",
                "01:00"));

        // THEN
        assertThat(thrown)
                .isInstanceOf(MeetingException.class)
                .hasMessageContaining("Nie można utworzyć spotkania dla podanego użytkownika w oczekiwanym" +
                        "czasie! Podany termin nakłada się na już istniejące spotkanie!");
    }

    @Test
    void should_not_throw_exception_for_meetings_that_do_not_overlap() {
        // GIVEN
        Meeting meeting = new Meeting(
                "Existing Meeting",
                "05:03:2024 09:00",
                Set.of("user@example.com"),
                "01:00");

        // WHEN
        Executable action = () ->
                meeting.checkingForConflictingMeetings(
                        "05:03:2024 10:00",
                        "01:00");

        // THEN
        assertDoesNotThrow(action);
    }
}
