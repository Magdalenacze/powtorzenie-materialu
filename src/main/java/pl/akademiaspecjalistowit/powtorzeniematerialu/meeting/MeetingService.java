package pl.akademiaspecjalistowit.powtorzeniematerialu.meeting;

import java.util.List;
import java.util.Set;

public class MeetingService {

    private final MeetingRepository meetingRepository;

    public MeetingService() {
        meetingRepository = new MeetingRepository();
    }

    public Meeting createNewMeeting(String meetingName, String meetingDateTimeString,
                                    Set<String> participantEmail, String meetingDuration) {
        for (Meeting meeting : meetingRepository.findAllByParticipantEmails(participantEmail)) {
            meeting.checkingForConflictingMeetings(meetingDateTimeString, meetingDuration);
        }
        Meeting meeting = new Meeting(meetingName, meetingDateTimeString, participantEmail, meetingDuration);
        meetingRepository.save(meeting);
        return meeting;
    }

    public List<Meeting> getAllMeetings() {
        return meetingRepository.findAll();
    }

    public MeetingRepository getMeetingRepository() {
        return meetingRepository;
    }

    public void deleteExistingMeeting(Meeting meeting) {
        if (!getAllMeetings().contains(meeting)) {
            throw new MeetingException("Niestety nie znaleziono żadnego spotkania!");
        }
        meetingRepository.remove(meeting);
    }
}
