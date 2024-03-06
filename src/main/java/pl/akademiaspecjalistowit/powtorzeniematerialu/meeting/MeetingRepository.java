package pl.akademiaspecjalistowit.powtorzeniematerialu.meeting;

import java.util.*;

public class MeetingRepository {

    private Map<Long, Meeting> meetings;

    public MeetingRepository() {
        meetings = new HashMap<>();
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
}
