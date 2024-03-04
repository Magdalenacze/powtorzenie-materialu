package pl.akademiaspecjalistowit.powtorzeniematerialu.meeting;

import java.util.List;
import java.util.Set;

public class MeetingService {

    private MeetingRepository meetingRepository;

    public MeetingService() {
        meetingRepository = new MeetingRepository();
    }

    public Meeting createNewMeeting(String meetingName, String meetingDateTimeString, Set<String> participantEmail,
                                   String meetingDuration) {
        boolean test = false;
        for(String email: participantEmail) {
            test = checkingForConflictingMeetings(meetingDateTimeString, email);
            if(test == true)
                break;
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

    public boolean checkingForConflictingMeetings(String meetingDateTimeString, String email) {
        for (Meeting meeting: meetingRepository.findAll()) {
            for (String email2: meeting.getParticipantEmail())
            {
                if (email2.equals(email)&&meeting.getDateAndTime().equals(meetingDateTimeString)) {
                    return true;
                }
            }
        }
       return false;
    }
}
