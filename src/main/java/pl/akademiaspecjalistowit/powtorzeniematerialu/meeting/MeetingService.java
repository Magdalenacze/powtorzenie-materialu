package pl.akademiaspecjalistowit.powtorzeniematerialu.meeting;

import java.util.List;
import java.util.Set;

public class MeetingService {

    private MeetingRepository meetingRepository;

    public MeetingService() {
        meetingRepository = new MeetingRepository();
    }

    public Meeting createNewMeeting(String meetingName, String meetingDateTimeString,
                                    Set<String> participantEmail, String meetingDuration) {
        for(String email : participantEmail) {
            for (Meeting meeting : meetingRepository.findAll()) {
                 meeting.checkingForConflictingMeetings(meetingDateTimeString, email);
            }
        }
        Meeting meeting = new Meeting(meetingName,meetingDateTimeString, participantEmail, meetingDuration);
        meetingRepository.save(meeting);
        return meeting;
    }

    public List<Meeting> getAllMeetings() {
        return meetingRepository.findAll();
    }


}
