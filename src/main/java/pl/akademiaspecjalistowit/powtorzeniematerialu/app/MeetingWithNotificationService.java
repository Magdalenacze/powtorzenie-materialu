package pl.akademiaspecjalistowit.powtorzeniematerialu.app;

import pl.akademiaspecjalistowit.powtorzeniematerialu.meeting.Meeting;
import pl.akademiaspecjalistowit.powtorzeniematerialu.meeting.MeetingRepository;
import pl.akademiaspecjalistowit.powtorzeniematerialu.meeting.MeetingService;
import pl.akademiaspecjalistowit.powtorzeniematerialu.meeting.MeetingServiceImpl;
import pl.akademiaspecjalistowit.powtorzeniematerialu.notification.NotificationServiceImpl;

import java.util.List;
import java.util.Set;

public class MeetingWithNotificationService implements MeetingService {

    private final MeetingServiceImpl meetingServiceImpl;
    private MeetingRepository meetingRepository;
    private final NotificationServiceImpl notificationServiceImpl;

    public MeetingWithNotificationService(MeetingServiceImpl meetingServiceImpl,
                                          NotificationServiceImpl notificationServiceImpl) {
        this.meetingServiceImpl = MeetingServiceImpl.getMeetingServiceImpl(meetingRepository);
        this.notificationServiceImpl = notificationServiceImpl;
    }

    @Override
    public Meeting createNewMeeting(String meetingName, String meetingDateTimeString,
                                    Set<String> participantEmail, String meetingDuration) {
        Meeting newMeeting = meetingServiceImpl.createNewMeeting(meetingName, meetingDateTimeString,
                participantEmail, meetingDuration);
        notificationServiceImpl.sendNotificationsToParticipants(newMeeting.getParticipantEmail());
        return newMeeting;
    }

    @Override
    public List<Meeting> getAllMeetings() {
        return meetingServiceImpl.getAllMeetings();
    }

    public MeetingServiceImpl getMeetingServiceImpl() {
        return meetingServiceImpl;
    }
}