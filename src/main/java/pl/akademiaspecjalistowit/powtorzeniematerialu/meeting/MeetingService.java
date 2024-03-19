package pl.akademiaspecjalistowit.powtorzeniematerialu.meeting;

import java.util.List;
import java.util.Set;

public interface MeetingService {

    Meeting createNewMeeting(String meetingName, String meetingDateTimeString,
                             Set<String> participantEmail, String meetingDuration);

    List<Meeting> getAllMeetings();
}
