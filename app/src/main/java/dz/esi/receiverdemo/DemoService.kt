package dz.esi.receiverdemo

import android.R
import android.app.*
import android.content.Intent
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import androidx.core.app.NotificationCompat
import java.text.SimpleDateFormat
import java.util.*

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 *
 *
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
class DemoService : IntentService("DemoService") {

    private var mp: MediaPlayer? = null

    override fun onHandleIntent(intent: Intent?) {
        val array = arrayListOf<String>("01:40 AM","01:45 AM","13:00 PM","16:00 PM","20:00 PM","21:30 PM")
        val handler= Handler(mainLooper)
        handler.postDelayed(object :
        Runnable{
            override fun run() {
                //To change body of created functions use File | Settings | File Templates.
                val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val notificationID = 1001
                val pIntent = PendingIntent.getService(applicationContext, System.currentTimeMillis().toInt(), intent!!, 0)
                val builder = builder(notificationManager, pIntent)
                var noti = builder!!.build()
                try {
                    if(SimpleDateFormat("hh:mm a", Locale.US).format(Date()).toString()==array[0]){
                        notificationManager.notify(notificationID, noti)
                        builder.setContentText(SimpleDateFormat("hh:mm a", Locale.US).format(Date())+"Sobh")
                        noti = builder.build()
                        notificationManager.notify(notificationID, noti)

                    }
                    else if(SimpleDateFormat("hh:mm a", Locale.US).format(Date()).toString()==array[1]){
                        notificationManager.notify(notificationID, noti)
                        builder.setContentText(SimpleDateFormat("hh:mm a", Locale.US).format(Date())+"Dohr")
                        noti = builder.build()
                        notificationManager.notify(notificationID, noti)

                    }
                    else if(SimpleDateFormat("hh:mm a", Locale.US).format(Date()).toString()==array[2]){
                        notificationManager.notify(notificationID, noti)
                        builder.setContentText(SimpleDateFormat("hh:mm a", Locale.US).format(Date())+"ElAsr")
                        noti = builder.build()
                        notificationManager.notify(notificationID, noti)

                    }
                    else if(SimpleDateFormat("hh:mm a", Locale.US).format(Date()).toString()==array[3]){
                        notificationManager.notify(notificationID, noti)
                        builder.setContentText(SimpleDateFormat("hh:mm a", Locale.US).format(Date())+"ElMaghreb")
                        noti = builder.build()
                        notificationManager.notify(notificationID, noti)

                    }
                    else if(SimpleDateFormat("hh:mm a", Locale.US).format(Date()).toString()==array[4]){
                        notificationManager.notify(notificationID, noti)
                        builder.setContentText(SimpleDateFormat("hh:mm a", Locale.US).format(Date())+"ichaa")
                        noti = builder.build()
                        notificationManager.notify(notificationID, noti)

                    }
                    else{
                        /*notificationManager.notify(notificationID, noti)
                        builder.setContentText("il reste ..... pour la prière"+SimpleDateFormat("hh:mm a", Locale.US).format(Date()).toString())
                        builder.setSound(Uri.parse("android.ressource://"+packageName+"/"+dz.esi.receiverdemo.R.raw.eladhen))

                        noti = builder.build()
                        notificationManager.notify(notificationID, noti)*/


                        val sound = Uri.parse("android.resource://" + packageName + "/" + dz.esi.receiverdemo.R.raw.eladhen)
                        val vibre = longArrayOf(500, 1000)
                        val notification = NotificationCompat.Builder(applicationContext,"adhan")
                                .setContentTitle("Prayer time") // title for notification
                                .setContentText("Slat time - " + Calendar.getInstance().time.hours + " : " +
                                        Calendar.getInstance().time.minutes)// message for notification
                                .setSmallIcon(R.drawable.btn_star) //small icon for notification
                                .setAutoCancel(true) // clear notification after click
                                .setSound(sound)
                                .setVibrate(vibre)
                                .build()

                        startForeground(1, notification)  // Services d’Arrière-Plan*/

                        //Notifivation channel
                    }
                } catch (ex: Exception) {

                }
                handler.postDelayed(this,60000)
            }

        },10
        )


    }

    private fun builder(nm: NotificationManager, pi: PendingIntent): Notification.Builder? {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(
                    "ch00", "ch00", NotificationManager.IMPORTANCE_HIGH)
            val audioatt=AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build()
            mChannel.setSound(Uri.parse("android.ressource://"+packageName+"/"+dz.esi.receiverdemo.R.raw.eladhen),audioatt)



            nm.createNotificationChannel(mChannel)
            val builder = Notification.Builder(this,"ch00")
                    .setContentTitle("Adhan")
                    .setContentText("Notification à partir d'un service").setSmallIcon(R.drawable.btn_star)
                    .setContentIntent(pi).setAutoCancel(true)
                    .setOngoing(true)
                    .setProgress(100, 0, false)
                    .setAutoCancel(true)
                    .setSound(Uri.parse("android.ressource://"+packageName+"/"+dz.esi.receiverdemo.R.raw.eladhen),audioatt)

            return builder


        }else{

            val builder = Notification.Builder(this)
                    .setContentTitle("Service")
                    .setContentText("Notification à partir d'un service").setSmallIcon(R.drawable.btn_star)
                    .setContentIntent(pi).setAutoCancel(true)
                    .setOngoing(true)
                    .setProgress(100, 0, false)
                    .setAutoCancel(true)

            return builder

        }

    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionFoo(param1: String, param2: String) {
        // TODO: Handle action Foo
        throw UnsupportedOperationException("Not yet implemented")
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionBaz(param1: String, param2: String) {
        // TODO: Handle action Baz
        throw UnsupportedOperationException("Not yet implemented")
    }
    fun lireAudio(resId: Int) {
        if (mp == null) {        //set up MediaPlayer
            mp = MediaPlayer.create(this, resId)

            try {
                mp!!.prepare()

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        if (!mp!!.isPlaying())
            mp!!.start()
        else
            mp!!.pause()
    }

    companion object {
        private val ACTION_SERVICE = "dz.esi.demonotifsser"

        fun demarrerService(context: Context) {
            val intent = Intent(context, DemoService::class.java)
            intent.action = ACTION_SERVICE
            context.startService(intent)
        }
    }
}
