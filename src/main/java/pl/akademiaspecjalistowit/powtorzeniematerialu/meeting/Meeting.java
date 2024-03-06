package pl.akademiaspecjalistowit.powtorzeniematerialu.meeting;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Meeting {

    private final UUID meetingId;
    private final String name;
    private final LocalDateTime dateAndTime;
    private final Set<String> participantEmail;
    private final Duration meetingDuration;

    public Meeting(String meetingName,
                   String meetingDateTimeString,
                   Set<String> participantEmail,
                   String meetingDuration) {


        this.dateAndTime = parseStringToDate(meetingDateTimeString);
        this.meetingDuration = parseDurationFromString(meetingDuration);
        this.participantEmail = participantEmail;
        this.name = meetingName;
        this.meetingId = UUID.randomUUID();

    }

    private static LocalDateTime parseStringToDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy HH:mm");
        try {
            return LocalDateTime.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            throw new MeetingException("Podana data ma niewłaściwy format");
        }
    }

    public static Duration parseDurationFromString(String durationString) {
        String[] parts = durationString.split(":");
        if (parts.length != 2) {
            throw new MeetingException("Nieprawidłowy format. Oczekiwano formatu HH:MM.");
        }
        long hours = Long.parseLong(parts[0]);
        long minutes = Long.parseLong(parts[1]);

        return Duration.ofHours(hours).plus(Duration.ofMinutes(minutes));
    }

    public boolean checkingForConflictingMeetings(String meetingDateTimeString, String meetingDuration) {
        if (checkingForConflictMeetingByTime(parseStringToDate(meetingDateTimeString),
                parseDurationFromString(meetingDuration))) {
            throw new MeetingException("Nie można utworzyć spotkania dla podanego użytkownika w oczekiwanym" +
                    "czasie! Podany termin nakłada się na już istniejące spotkanie!");
        }
        return false;
    }

    public boolean checkingForConflictMeetingByTime(LocalDateTime date, Duration duration) {
        LocalDateTime thisEndTime = dateAndTime.plus(meetingDuration);
        LocalDateTime otherEndTime = date.plus(duration);
        if (dateAndTime.isBefore(otherEndTime) && thisEndTime.isAfter(date) ||
                date.isBefore(thisEndTime) && otherEndTime.isAfter(dateAndTime)) {
            return true;
    } else {
            return false;
    }
}

    public Set<String> getParticipantEmail() {
        return Set.copyOf(participantEmail);
    }

    @Override
    public String toString() {
        return "Meeting{" +
            "meetingId=" + meetingId +
            ", name='" + name + '\'' +
            ", dateAndTime=" + dateAndTime +
            ", participantEmail=" + participantEmail +
            ", meetingDuration=" + meetingDuration +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Meeting meeting = (Meeting) o;
        return Objects.equals(meetingId, meeting.meetingId) && Objects.equals(name, meeting.name) &&
            Objects.equals(dateAndTime, meeting.dateAndTime) &&
            Objects.equals(participantEmail, meeting.participantEmail) &&
            Objects.equals(meetingDuration, meeting.meetingDuration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingId, name, dateAndTime, participantEmail, meetingDuration);
    }
}
