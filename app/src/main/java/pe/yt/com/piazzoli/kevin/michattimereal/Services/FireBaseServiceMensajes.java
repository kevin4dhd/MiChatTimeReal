package pe.yt.com.piazzoli.kevin.michattimereal.Services;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by KevinPiazzoli on 09/02/2017.
 */

public class FireBaseServiceMensajes extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String datos = remoteMessage.getData().get("Objetos");
    }

}
