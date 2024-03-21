package pl.akademiaspecjalistowit.powtorzeniematerialu.meeting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private static MeetingServiceImpl meetingServiceImpl;
    private Map<Long, Meeting> meetings = new HashMap<>();

    private MeetingServiceImpl(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public static MeetingServiceImpl getMeetingServiceImpl(MeetingRepository meetingRepository) {
        if (meetingServiceImpl == null) {
            synchronized (MeetingServiceImpl.class) {
                if (meetingServiceImpl == null) {
                    meetingServiceImpl = new MeetingServiceImpl(meetingRepository);
                }
            }
        }
        return meetingServiceImpl;
    }

    @Override
    public Meeting createNewMeeting(String meetingName, String meetingDateTimeString,
                                    Set<String> participantEmail, String meetingDuration) {
        for (Meeting meeting : MeetingRepository.getMeetingRepository(meetings).findAllByParticipantEmails(participantEmail)) {
            meeting.checkingForConflictingMeetings(meetingDateTimeString, meetingDuration);
        }
        Meeting meeting = new Meeting(meetingName, meetingDateTimeString, participantEmail, meetingDuration);
        meetingRepository.save(meeting);
        return meeting;
    }

    @Override
    public List<Meeting> getAllMeetings() {
        return MeetingRepository.getMeetingRepository(meetings).findAll();
    }

    public MeetingRepository getMeetingRepository() {
        return MeetingRepository.getMeetingRepository(meetings);
    }

    public void deleteExistingMeeting(Meeting meeting) {
        if (!getAllMeetings().contains(meeting)) {
            throw new MeetingException("Niestety nie znaleziono żadnego spotkania!");
        }
        MeetingRepository.getMeetingRepository(meetings).remove(meeting);
    }
}