package pl.akademiaspecjalistowit.powtorzeniematerialu.meeting;

import java.util.*;

public class MeetingRepository {

    private Map<Long, Meeting> meetings;
    private static MeetingRepository meetingRepository;

    private MeetingRepository(Map<Long, Meeting> meetings) {
        this.meetings = new HashMap<>();
    }

    public static MeetingRepository getMeetingRepository(Map<Long, Meeting> meetings) {
        if (meetingRepository == null) {
            synchronized (MeetingRepository.class) {
                if (meetingRepository == null) {
                    meetingRepository = new MeetingRepository(meetings);
                }
            }
        }
        return meetingRepository;
    }

    public void save(Meeting meeting) {
        meetings.put((long) meetings.size(), meeting);
    }

    public List<Meeting> findAll() {
        return meetings.values().stream().toList();
    }

    public List<Meeting> findAllByParticipantEmails(Set<String> emails) {
        List<Meeting> meetings = this.meetings.values().stream().toList();
        ArrayList<Meeting> meetingsWithParticipants = new ArrayList<>();
        for(Meeting meeting : meetings) {
            if(!Collections.disjoint(meeting.getParticipantEmail(), emails)) {
                meetingsWithParticipants.add(meeting);
            }
        }
        return meetingsWithParticipants;
    }

    public <K, V> K getKey(Map<K, V> map, V value) { // auxiliary method
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void remove(Meeting meeting) {
        meetings.remove(getKey(meetings, meeting));
    }

    public void removeAll() {
        meetings.clear();
    }

}