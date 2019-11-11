package android.paopaogame2019

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var isSoundOn = true
    private var msIntent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        Log.i("gamegame", "MainActivity. In OnCreate()")

        msIntent = Intent(applicationContext, MyMusicService::class.java)

    }

    // start GameFieldActivity
    private fun startGame() {

        val intent = Intent(this, GameFieldActivity::class.java)
        startActivity(intent)
    }

    // override onStop method
    override fun onStop() {

        Log.i("gamegame", "MainActivity. In OnStop()")
        btnNewGame.setOnClickListener(null)
        btnContinue.setOnClickListener(null)
        btnExit.setOnClickListener(null)
        ivSound.setOnClickListener(null)

        super.onStop()
    }

    // override onResume method
    override fun onResume() {

        // startService if isSoundOn equal to true
        if (isSoundOn) startService(msIntent)

        Log.i("gamegame", "Main Activity. In OnResume()")

        val sharedPref = getSharedPreferences("savedGameIndicators", Context.MODE_PRIVATE)

        ivSound.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                if (!isSoundOn) {
                    startService(msIntent)

                    isSoundOn = !isSoundOn
                }
                else {
                    stopService(msIntent)
                    isSoundOn = !isSoundOn
                }
            }

        })

        btnNewGame.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                val editor = sharedPref.edit()
                editor.clear()
                editor.putString("gameState", "new")
                editor.putBoolean("isMelodyOn", isSoundOn)
                editor.apply()

                startGame()
            }

        })

        btnContinue.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                val editor = sharedPref.edit()
                editor.putString("gameState", "continue")
                editor.putBoolean("isMelodyOn", isSoundOn)
                editor.apply()

                startGame()
            }
        })

        btnExit.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                btnNewGame.setOnClickListener(null)
                btnContinue.setOnClickListener(null)
                btnExit.setOnClickListener(null)
                ivSound.setOnClickListener(null)

                finish()
            }


        })

        if (!sharedPref.getBoolean("continueGame",  false)){

            btnContinue.isEnabled = false
            btnContinue.background = ContextCompat.getDrawable(this, R.drawable.button_transparent)
        }

        else {

            btnContinue.isEnabled = true
            btnContinue.background = ContextCompat.getDrawable(this, R.drawable.button_bcg)
        }

        super.onResume()
    }

    override fun onPause() {

        Log.i("gamegame", "MainActivity. In OnPause()")

        stopService(msIntent)
        super.onPause()
    }

}
