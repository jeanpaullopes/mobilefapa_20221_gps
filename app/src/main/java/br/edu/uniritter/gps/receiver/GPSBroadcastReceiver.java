package br.edu.uniritter.gps.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import br.edu.uniritter.gps.gps.view.LandActivity;

public class GPSBroadcastReceiver extends BroadcastReceiver {
    public static final String TAG = "GPSBroadcastReceiver";
    private GPSService service;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w(TAG, "onReceive: "+intent.getAction());
        Intent intentPosBoot = new Intent();
        intentPosBoot.setAction("br.edu.uniritter.GPS_START");
        Intent intent1 = new Intent(context,GPSService.class);
        Intent intent2 = new Intent(context, LandActivity.class);
        Toast.makeText(context, "onReceive", Toast.LENGTH_LONG).show();
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            NotificationChannel channel2 = new NotificationChannel(
                    "UniR",
                    "Channel UniRitter",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel2.setDescription("This is channel 2");

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel2);
            PendingIntent pendingIntent =
                    PendingIntent.getForegroundService(context, 0, intent1,
                            PendingIntent.FLAG_IMMUTABLE);
            //PendingIntent pendingIntent =
            //        PendingIntent.getForegroundService(context, 0, intentPosBoot,
            //                PendingIntent.FLAG_IMMUTABLE);


            Notification notification =
                    new Notification.Builder(context,"UniR")
                            .setContentTitle("Projeto de Mobile")
                            .setContentText("Clique aqui para iniciar a app")

                            .setContentIntent(pendingIntent)
                            .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                            .setAutoCancel(true)
                            .build();
            manager.notify(666, notification);
            Log.d(TAG, "onReceive: notification enviada");
        }
        if (intent.getAction().equals("br.edu.uniritter.GPS_START")) {

            intent1.putExtra("boot", false);
            context.startForegroundService(intent1);

        }
    }
}
