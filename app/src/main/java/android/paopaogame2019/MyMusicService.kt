package android.paopaogame2019

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MyMusicService : Service() {

    private var mPlayer: MediaPlayer? = null

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        mPlayer = MediaPlayer.create(applicationContext, R.raw.bcg_melody)

        mPlayer?.setVolume(1f, 1f)

        mPlayer?.start()

        mPlayer?.isLooping = true

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {

        mPlayer?.stop()
        mPlayer?.release()
        mPlayer = null

        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}
