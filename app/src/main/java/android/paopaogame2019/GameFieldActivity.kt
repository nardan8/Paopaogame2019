package android.paopaogame2019

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.gridlayout.widget.GridLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_game_field.*
import java.util.HashMap
import kotlin.collections.ArrayList
import kotlin.collections.set

class GameFieldActivity : AppCompatActivity(), View.OnClickListener {

    // region DECLARED VARIABLES
    private var game: Game? = null

    private var firstBtn: View? = null

    private var clickCount = 0

    private lateinit var sharedPref: SharedPreferences

    private var timer: CountDownTimer? = null

    private var sharedPrefSaveGameIndicators = "savedGameIndicators"

    private var timeDlg: Dialog? = null

    private var lvlCompleteDialog: Dialog? = null

    private var gmCompleteDialog: Dialog? = null

    private var defImgIds: HashMap<Int, Int>? = null

    private var searchImgIds: HashMap<Int, Int>? = null

    private var pressedImgIds: HashMap<Int, Int>? = null

    private var audioManager: AudioManager? = null

    private var isGameCompleted = false

    private var sPool: SoundPool? = null

    private var mSoundId: Int = 0

    private var volume: Float = 1.0f

    //endregion

    //region ACTIVITIE'S LIFECYCLE OVERRIDED METHODS
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_game_field)

        audioManager = applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val actVolume = audioManager!!.getStreamVolume(AudioManager.STREAM_MUSIC)
        val maxVolume = audioManager!!.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        volume = (actVolume / maxVolume).toFloat()
    }

    override fun onResume() {

        game = Game()


        if (sPool == null){
            sPool = SoundPool.Builder().setMaxStreams(10).build()
            mSoundId = sPool!!.load(applicationContext, R.raw.pick, 1)
        }

        // Pause click event
        btnPauseGame.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                finish()
            }

        })

        // reshuffle cards by clicking shuffle icon
        ivShuffle.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onShuffleClick()
            }

        })
        tvShuffle.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onShuffleClick()
            }

        })

        // hint two possible cards in field by clicking search icon
        ivSearch.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onSearchClick()
            }

        })
        tvSearch.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onSearchClick()
            }

        })

        if (cardsGL.childCount == 0) addButtonsToGridlayout()

        clickCount = 0

        if (firstBtn != null) firstBtn = null

        if (game != null){

            defImgIds = game?.getDefImgIds()
            searchImgIds = game?.getSearcImgIds()
            pressedImgIds = game?.getPressImgIds()

        }

        sharedPref = getSharedPreferences(sharedPrefSaveGameIndicators, Context.MODE_PRIVATE)

        // new game
        if (sharedPref.getString("gameState", "new") == "new") newGame()

        // continue last game
        else continueGame()

        super.onResume()
    }

    override fun onPause() {

        clickCount = 0
        firstBtn = null

        if (timer != null) {

            timer?.cancel()
            timer = null
        }

        if (timeDlg != null) {
            timeDlg?.dismiss()
            timeDlg = null
        }

        if(lvlCompleteDialog != null){
            lvlCompleteDialog?.dismiss()
            lvlCompleteDialog = null
        }

        if (gmCompleteDialog != null){
            gmCompleteDialog?.dismiss()
            gmCompleteDialog = null
        }

        sharedPref = getSharedPreferences(sharedPrefSaveGameIndicators, Context.MODE_PRIVATE)

        val editor = sharedPref.edit()

        if (isGameCompleted) {

            editor.clear()
            editor.putBoolean("continueGame", false)
            editor.putBoolean("isGameCompleted", true)
        }
        else {
            editor.putBoolean("continueGame", true)
            editor.putBoolean("isGameCompleted", false)
            editor.putString("gameState", "continue")

            editor.putInt("progBar", progBar.progress)
            editor.putString("curScore", tvScore.text.toString())
            editor.putString("searchCount", tvSearch.text.toString())
            editor.putString("shuffleCount", tvShuffle.text.toString())

            if (game != null){

                editor.putInt("currentLevel", game!!.currentLevel)
                editor.putInt("hidedCardsCount", game!!.hidedCardsCount)

                val gson = Gson()

                editor.putString("keys", gson.toJson(game!!.cardIdImgId.keys))
                editor.putString("values", gson.toJson(game!!.cardIdImgId.values))
            }
        }

        editor.apply()

        if (game != null) game!!.resetGameVars()

        btnPauseGame.setOnClickListener(null)
        ivShuffle.setOnClickListener(null)
        tvShuffle.setOnClickListener(null)
        ivSearch.setOnClickListener(null)
        tvSearch.setOnClickListener(null)

        for (i in 0 until cardsGL.childCount){
            cardsGL.getChildAt(i).setOnClickListener(null)
            cardsGL.getChildAt(i).setBackgroundResource(0)
        }

        cardsGL?.removeAllViews()

        clearVariables()

        game = null

        super.onPause()
    }

    override fun onStop() {

        sPool!!.release()
        sPool = null

        super.onStop()
    }

    override fun onDestroy() {

        audioManager = null

        super.onDestroy()
    }

    //endregion

    //region Click Or Press Events

    override fun onClick(v: View?) {

        if (progBar.progress > 0.5f){

            if (clickCount == 0) {

                firstBtn = v

                // set pressed bcg image (with orange border) to first btn
                (firstBtn as ImageButton).setBackgroundResource(
                    pressedImgIds!![game!!.cardIdImgId[cardsGL.indexOfChild(firstBtn)]]!!
                )

                clickCount = 1


            } else if (clickCount == 1 && firstBtn != v) {

                var secondBtn = v

                val isIdentityCards = game!!.checkIdentity(
                    cardsGL.indexOfChild(firstBtn),
                    cardsGL.indexOfChild(secondBtn)
                )

                // if two cards identity...
                if (isIdentityCards) {

                    // ..., if search (search icon) was clicked before ...
                    if (!ivSearch.isEnabled) {

                        // ... reset default image to first button_style, ...
                        cardsGL.getChildAt(game!!.possibleNextFirstBtnIndex).setBackgroundResource(
                            defImgIds!![game!!.cardIdImgId[game!!.possibleNextFirstBtnIndex]]!!
                        )

                        // ,... reset default image to second button_style and ...
                        cardsGL.getChildAt(game!!.possibleNextSecondBtnIndex).setBackgroundResource(
                            defImgIds!![game!!.cardIdImgId[game!!.possibleNextSecondBtnIndex]]!!
                        )
                    }

                    ivSearch.isEnabled = true
                    tvSearch.isEnabled = true

                    tvScore.text = game!!.addScore(tvScore.text.toString().toInt())

                    hideCards(firstBtn, secondBtn)

                    sPool!!.play(mSoundId, 1f, 1f, 2,0, 1f)

                    // and set the hided first clicked button to null,
                    firstBtn = null

                    // Finally reset the click counter to 0
                    clickCount = 0
                }

                // if two cards aren't identity...
                else {
                    // ... then reset first btn's bcg to default image (image with lightblue border), ...
                    (firstBtn as ImageButton).setBackgroundResource(
                        defImgIds!![game!!.cardIdImgId[cardsGL.indexOfChild(firstBtn)]]!!
                    )

                    // ,... reassign first btn to second button_style, ...
                    firstBtn = secondBtn

                    // , ... reset first btn's bcg to pressed image (image with orange border), ...
                    (firstBtn as ImageButton).setBackgroundResource(
                        pressedImgIds!![game!!.cardIdImgId[cardsGL.indexOfChild(firstBtn)]]!!
                    )

                    // , ... reset second btn to null, ...
                    secondBtn = null

                    // , ... reset clickCount to 1 which means that first btn was clicked and ready to compare with next btn will be clicked
                    clickCount = 1

                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            this.finish()
            //super.onBackPressed()
        }
        else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP){

            audioManager!!.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND)
            return true
        }
        else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){

            audioManager!!.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND)
            return true
        }

        return true
    }

    private fun onShuffleClick(){

        if (tvShuffle.text.toString().toInt() > 0) {

            tvShuffle.text = (tvShuffle.text.toString().toInt() - 1).toString()

            // reset search checker to false
            game!!.isSearchClicked = false

            ivSearch.isEnabled = true
            tvSearch.isEnabled = true

            // reset default image to first button_style if search was clicked before reshuffle function
            cardsGL.getChildAt(game!!.possibleNextFirstBtnIndex)
                .setBackgroundResource(defImgIds!![game!!.cardIdImgId[game!!.possibleNextFirstBtnIndex]]!!)

            // reset default image to second button_style if search was clicked before reshuffle function
            cardsGL.getChildAt(game!!.possibleNextSecondBtnIndex)
                .setBackgroundResource(defImgIds!![game!!.cardIdImgId[game!!.possibleNextSecondBtnIndex]]!!)

            // shuffle remaining cards in the field while find next possible cards
            do {
                reshuffle()
            } while (!game!!.isTherePair())

            // reset first button_style to null.
            if (firstBtn != null) {
                (firstBtn as ImageButton).setBackgroundResource(
                    defImgIds!![game!!.cardIdImgId[cardsGL.indexOfChild(firstBtn)]]!!
                )
                firstBtn = null
                clickCount = 0
            }
        }
    }

    private fun onSearchClick(){

        if (tvSearch.text.toString().toInt() > 0) {

            cardsGL.getChildAt(game!!.possibleNextFirstBtnIndex)
                .setBackgroundResource(searchImgIds!![game!!.cardIdImgId[game!!.possibleNextFirstBtnIndex]]!!)

            cardsGL.getChildAt(game!!.possibleNextSecondBtnIndex)
                .setBackgroundResource(searchImgIds!![game!!.cardIdImgId[game!!.possibleNextSecondBtnIndex]]!!)

            game!!.isSearchClicked = true

            ivSearch.isEnabled = false
            tvSearch.isEnabled = false

            tvSearch.text = (tvSearch.text.toString().toInt() - 1).toString()
        }
    }

    //endregion

    //region FUNCTIONS
    private fun newGame() {

        progBar.progress = 720
        timer = newTimer(progBar.progress * 1000L)

        game!!.currentLevel = 1
        game!!.hidedCardsCount = 0

        tvLevel2.text = game!!.currentLevel.toString()
        tvScore.text = R.string.score_value.toString()
        tvSearch.text = "9"
        tvShuffle.text = "5"

        game!!.fillFull()

        reDrawField()

        timer?.start()
    }

    private fun continueGame() {

        progBar.progress = sharedPref.getInt("progBar", 720)

        timer = newTimer(progBar.progress * 1000L)

        game!!.currentLevel = sharedPref.getInt("currentLevel", 1)
        game!!.hidedCardsCount = sharedPref.getInt("hidedCardsCount", 0)

        tvLevel2.text = game!!.currentLevel.toString()
        tvScore.text = sharedPref.getString("curScore", "000000")
        tvSearch.text = sharedPref.getString("searchCount", "9")
        tvShuffle.text = sharedPref.getString("shuffleCount", "5")

        isGameCompleted = sharedPref.getBoolean("isGameCompleted", false)

        if (isGameCompleted) {

            gameCompleteDialog()
        }

        val gson = Gson()

        // retrieving keys of mapBtnImgIdIndexes from SharedPreferences
        var arrList = gson.fromJson(sharedPref.getString("keys", ""), ArrayList::class.java)

        val keysInt = ArrayList<Int>()

        for (i in 0 until arrList.size) {
            keysInt.add((arrList[i] as Double).toInt())
        }

        // retrieving keys of mapBtnImgIdIndexes from SharedPreferences
        arrList = gson.fromJson(sharedPref.getString("values", ""), ArrayList::class.java)

        val valuesInt = ArrayList<Int>()

        for (i in 0 until arrList.size) {
            valuesInt.add((arrList[i] as Double).toInt())
        }

        for (i in 0 until keysInt.size) {

            Log.i("hhh", "card indexes: ${keysInt[i]}, imgIndex: ${valuesInt[i]}")
            game!!.cardIdImgId[keysInt[i]] = valuesInt[i]
        }

        reDrawField()

        sharedPref.edit().clear().apply()

        timer?.start()
    }

    private fun newTimer(timeAmount: Long): CountDownTimer {

        val tmpTimer = object : CountDownTimer(timeAmount, 1000) {

            override fun onFinish() {

                progBar.progress = 0

                if (!game!!.isLevelComplete()) {

                    timeOverDialog()
                }
            }

            override fun onTick(millisUntilFinished: Long) {

                if (game!!.isLevelComplete()) cancel()

                progBar.progress -= 1

            }
        }

        return tmpTimer
    }

    // fading out animation of cards
    private fun hideCards(firstCard: View?, secondCard: View?) {

        game!!.cardIdImgId[cardsGL.indexOfChild(firstCard)] = 0
        game!!.cardIdImgId[cardsGL.indexOfChild(secondCard)] = 0
        firstCard?.setBackgroundResource(0)
        firstCard?.visibility = View.INVISIBLE

        if (secondCard != null) {
            secondCard.setBackgroundResource(0)
            secondCard.visibility = View.INVISIBLE
        }

        game!!.hidedCardsCount += 2

        // if current level complete
        if (game!!.isLevelComplete()) {

            if (game!!.currentLevel == 30){

                isGameCompleted = true
                gameCompleteDialog()

                return
            }

            timer?.cancel()

            tvScore.text = game!!.addScore(tvScore.text.toString().toInt() + progBar.progress * 10)

            tvShuffle.text = (tvShuffle.text.toString().toInt() + 1).toString()

            tvSearch.text = (tvSearch.text.toString().toInt() + 1).toString()

            progBar.progress += 720

            timer = newTimer(progBar.progress * 1000L)



            // show dialog
            levelCompleteDialog()

            if (game!!.currentLevel < 30){

                // start next level
                startNextLevel()
            }

            return
        }

        // Refresh field after hiding. Apply from level 2 to 20.
        if (game!!.currentLevel > 1){

            game!!.moveCards(
                game!!.currentLevel,
                cardsGL.indexOfChild(firstCard),
                cardsGL.indexOfChild(secondCard)
            )

            reDrawField()
        }

        // if there aren't any pair then reshuffle remain visible cards
        while (!game!!.isTherePair()) reshuffle()
    }

    // Set image to each btnCard in field
    private fun reDrawField() {

        for (i in 0..143) {

            if (game!!.cardIdImgId[i] != 0) {

                cardsGL.getChildAt(i).setBackgroundResource(defImgIds!![game!!.cardIdImgId[i]]!!)

                cardsGL.getChildAt(i).visibility = View.VISIBLE
            }

            else {

                cardsGL.getChildAt(i).setBackgroundResource(0)

                cardsGL.getChildAt(i).visibility = View.INVISIBLE
            }
        }

        while (!game!!.isTherePair()) reshuffle()
    }

    private fun startNextLevel() {

        // increase level
        game!!.currentLevel += 1

        tvLevel2.text = game!!.currentLevel.toString()

        // reset game variables
        game!!.resetGameVars()

        // fill cardIdImgId for new level
        game!!.fillFull()

        // fill field with cards
        reDrawField()
    }

    // reshuffled all cards
    private fun reshuffle() {

        val shuffledKeys = (game!!.cardIdImgId.filter { it.value > 0 } as HashMap<Int, Int>).keys.sorted()

        val shuffledValues = (game!!.cardIdImgId.filterValues { it > 0 } as HashMap<Int, Int>).values.shuffled()

        for (i in 0 until shuffledKeys.size) {

            cardsGL.getChildAt(shuffledKeys[i]).setBackgroundResource(defImgIds!![shuffledValues[i]]!!)

            game!!.cardIdImgId[shuffledKeys[i]] = shuffledValues[i]

        }
    }

    // setting variables to null
    private fun clearVariables() {

        timeDlg = null
        defImgIds = null
        pressedImgIds = null
        searchImgIds = null
    }

    // adding buttons to gridlayout
    private fun addButtonsToGridlayout() {

        var imgBtn: ImageButton
        var layoutParams: GridLayout.LayoutParams

        for (i in 0 .. 8) {

            for (j in 0 .. 15) {

                imgBtn = ImageButton(this)

                layoutParams = GridLayout.LayoutParams()

                layoutParams.height = 0
                layoutParams.width = 0

                layoutParams.columnSpec = GridLayout.spec(j, 1f)
                layoutParams.rowSpec = GridLayout.spec(i, 1f)

                layoutParams.setMargins(1,1,1,1)

                imgBtn.layoutParams = layoutParams

                imgBtn.visibility = View.VISIBLE

                imgBtn.setOnClickListener(this)

                cardsGL?.addView(imgBtn, layoutParams)
            }
        }
    }

    //endregion

    // region Dialog Boxes
    // Level Complete Dialog Box
    private fun levelCompleteDialog() {

        lvlCompleteDialog = Dialog(this@GameFieldActivity)

        val layoutInflater = this@GameFieldActivity.layoutInflater.inflate(R.layout.level_complete_dialog, null)

        val tvScoreDB = layoutInflater.findViewById<TextView>(R.id.tvScoreDB)
        tvScoreDB.text = tvScore.text

        val tvLevel = layoutInflater.findViewById<TextView>(R.id.tvLevelDB)
        tvLevel.text = game!!.currentLevel.toString()

        val btnClose = layoutInflater.findViewById<Button>(R.id.lcdBtnClose)

        btnClose.setOnClickListener{

            lvlCompleteDialog?.dismiss()

            if (game!!.currentLevel < 30) timer?.start()
        }

        lvlCompleteDialog?.setCancelable(false)

        lvlCompleteDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        lvlCompleteDialog?.setContentView(layoutInflater)

        lvlCompleteDialog?.create()

        lvlCompleteDialog?.show()
    }

    // Game Complete Dialog Box
    private fun gameCompleteDialog() {

        gmCompleteDialog = Dialog(this@GameFieldActivity)

        val layoutInflater = this@GameFieldActivity.layoutInflater.inflate(R.layout.game_complete_dialog, null)

        val tvFinalScore = layoutInflater.findViewById<TextView>(R.id.tvScoreValue)
        tvFinalScore.text = this@GameFieldActivity.tvScore.text

        val btnOk = layoutInflater.findViewById<Button>(R.id.btnOk)

        btnOk.setOnClickListener{

            gmCompleteDialog?.dismiss()

            if (game!!.currentLevel < 30) timer?.start()

            finish()
        }

        gmCompleteDialog?.setCancelable(false)

        gmCompleteDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        gmCompleteDialog?.setContentView(layoutInflater)

        gmCompleteDialog?.create()

        gmCompleteDialog?.show()
    }

    // time over dialog box
    private fun timeOverDialog() {

        timeDlg = Dialog(this@GameFieldActivity)

        val layoutInflater = layoutInflater.inflate(R.layout.time_over_dialog, null)

        val btnNG = layoutInflater.findViewById<Button>(R.id.btnNewGame1)

        btnNG.setOnClickListener {
            timeDlg!!.dismiss()
            newGame()
        }

        timeDlg!!.setCancelable(false)

        timeDlg!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        timeDlg!!.setContentView(layoutInflater)

        timeDlg!!.create()

        timeDlg!!.show()
    }
    //endregion
}
