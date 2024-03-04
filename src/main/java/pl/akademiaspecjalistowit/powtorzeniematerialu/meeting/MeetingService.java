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
        boolean test = false;
        for(String email : participantEmail) {
            for (Meeting meeting : meetingRepository.findAll()) {
                test = meeting.checkingForConflictingMeetings(meetingDateTimeString, email);
                if (test == true)
                    break;
            }
        }
        if(test == false) {
        Meeting meeting = new Meeting(meetingName,meetingDateTimeString, participantEmail, meetingDuration);
        meetingRepository.save(meeting);
        return meeting;
        }
        return null;
    }

    public List<Meeting> getAllMeetings() {
        return meetingRepository.findAll();
    }


}
