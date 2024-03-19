package pl.akademiaspecjalistowit.powtorzeniematerialu.meeting;

import java.util.*;

public class MeetingRepository {

    private Map<Long, Meeting> meetings;
    private static MeetingRepository instance;

    private MeetingRepository() {
        meetings = new HashMap<>();
    }

    public static MeetingRepository getInstance() {
        if (instance == null) {
            synchronized (MeetingRepository.class) {
                if (instance == null) {
                    instance = new MeetingRepository();
                }
            }
        }
        return instance;
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
}