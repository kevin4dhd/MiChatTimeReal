package pe.yt.com.piazzoli.kevin.michattimereal.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

import pe.yt.com.piazzoli.kevin.michattimereal.Mensajes.Mensajeria;
import pe.yt.com.piazzoli.kevin.michattimereal.R;

/**
 * Created by KevinPiazzoli on 09/02/2017.
 */

public class FireBaseServiceMensajes extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String datos = remoteMessage.getData().get("Objetos");
        Mensaje(datos);
        showNotification();
    }

    private void Mensaje(String objetos){
        Intent i = new Intent(Mensajeria.MENSAJE);
        i.putExtra("key_objetos",objetos);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
    }

    private void showNotification(){
        Intent i = new Intent(this,Mensajeria.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_ONE_SHOT);

        Uri soundNotification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Builder builder = new Builder(this);
        builder.setAutoCancel(true);
        builder.setContentTitle("Kevin Piazzoli DESDE ANDROID");
        builder.setContentText("Este es el cuerpo");
        builder.setSound(soundNotification);
        builder.setSmallIcon(R.drawable.ic_action_key);
        builder.setTicker("Este es un ticker");
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Random random = new Random();

        notificationManager.notify(random.nextInt(),builder.build());

    }

}
