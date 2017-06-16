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
import pe.yt.com.piazzoli.kevin.michattimereal.Preferences;
import pe.yt.com.piazzoli.kevin.michattimereal.R;

/**
 * Created by KevinPiazzoli on 09/02/2017.
 */

public class FireBaseServiceMensajes extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String type = remoteMessage.getData().get("type");
        String cabezera =  remoteMessage.getData().get("cabezera");
        String cuerpo =  remoteMessage.getData().get("cuerpo");
        switch (type){
            case "mensaje":
                String mensaje = remoteMessage.getData().get("mensaje");
                String hora = remoteMessage.getData().get("hora");
                String receptor = remoteMessage.getData().get("receptor");
                String emisorPHP = remoteMessage.getData().get("emisor");
                String emisor = Preferences.obtenerPreferenceString(this,Preferences.PREFERENCE_USUARIO_LOGIN);
                if(emisor.equals(receptor)){
                    Mensaje(mensaje,hora,emisorPHP);
                    showNotification(cabezera,cuerpo);
                }
                break;
            case "solicitud":
                String usuario_envio_solicitud = remoteMessage.getData().get("user_envio_solicitud");
                showNotification(cabezera,cuerpo);
                break;
        }

    }

    private void Mensaje(String mensaje,String hora,String emisor){
        Intent i = new Intent(Mensajeria.MENSAJE);
        i.putExtra("key_mensaje",mensaje);
        i.putExtra("key_hora",hora);
        i.putExtra("key_emisor_PHP",emisor);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
    }

    private void showNotification(String cabezera, String cuerpo){
        Intent i = new Intent(this,Mensajeria.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_ONE_SHOT);

        Uri soundNotification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Builder builder = new Builder(this);
        builder.setAutoCancel(true);
        builder.setContentTitle(cabezera);
        builder.setContentText(cuerpo);
        builder.setSound(soundNotification);
        builder.setSmallIcon(R.drawable.ic_action_key);
        builder.setTicker(cuerpo);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Random random = new Random();

        notificationManager.notify(random.nextInt(),builder.build());

    }


}
