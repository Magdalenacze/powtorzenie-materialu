package pl.akademiaspecjalistowit.powtorzeniematerialu.notification;

import java.util.Set;

public class NotificationServiceImpl implements NotificationService {

    @Override
    public void sendNotificationsToParticipants(Set<String> participantEmail) {
        participantEmail
                .forEach(p -> System.out.println(p + " was successfully invited to a meeting!"));
    }
}
