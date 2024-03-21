package pl.akademiaspecjalistowit.powtorzeniematerialu.notification;

import java.util.Set;

public interface NotificationService {

    void sendNotificationsToParticipants(Set<String> participantEmail);
}
