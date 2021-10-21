package com.example.studycountdowntimer

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timeInput = findViewById(R.id.inputTime)
        timeShow = findViewById(R.id.showTime)
        buttonStart = findViewById(R.id.startButton)
        buttonPause = findViewById(R.id.pauseButton)
        buttonStop = findViewById(R.id.stopButton)



        buttonStart.setOnClickListener {
            if (onRun) {
                System.currentTimeMillis()
                val time = String.format("2000")
                timeInMilliseconds = time.toLong() * 3600000L
                startTime(timeInMilliseconds)
                buttonStart.visibility = View.INVISIBLE
                buttonPause.visibility = View.VISIBLE
                timeShow.visibility = View.VISIBLE
                onRun = false
            } else if (!onRun) {
                startTime(timeInMilliseconds)
                cdTimer?.start()
                buttonStart.visibility = View.INVISIBLE
                buttonPause.visibility = View.VISIBLE
            }
        }

        buttonPause.setOnClickListener {
            pauseTime()
        }

        buttonStop.setOnClickListener {
            stopTime()
        }


    }


    private var onRun = true
    private var cdTimer: CountDownTimer? = null
    private var timeInMilliseconds = 0L

    private lateinit var timeInput: EditText
    private lateinit var timeShow: TextView
    private lateinit var buttonStart: FloatingActionButton
    private lateinit var buttonPause: FloatingActionButton
    private lateinit var buttonStop: FloatingActionButton

    override fun onResume() {
        super.onResume()
        onRun
    }
    private fun View.hideKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun pauseTime() {
        cdTimer?.cancel()
        buttonPause.visibility = View.INVISIBLE
        buttonStart.visibility = View.VISIBLE

    }

    private fun startTime(timeInSeconds: Long) {

        cdTimer = object : CountDownTimer(timeInSeconds, 1000) {

            override fun onFinish() {
                timeShow.text = String.format("You did it!")
            }

            override fun onTick(p0: Long) {
                timeInMilliseconds = p0
                updateTextUI()
            }
        }

        (cdTimer as CountDownTimer).start()
        timeInput.hideKeyboard()

    }

    private fun stopTime() {
        cdTimer?.cancel()
        timeInput.visibility = View.VISIBLE
        timeShow.visibility = View.INVISIBLE
        buttonStart.visibility = View.VISIBLE
        buttonPause.visibility = View.INVISIBLE


        /**startActivity(Intent.makeRestartActivityTask(this.intent?.component))
        overridePendingTransition(0, 0)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)**/

    }



    private fun updateTextUI() {

        val seconds = (timeInMilliseconds / 1000) % 60
        val minutes = (timeInMilliseconds / 60000) % 60
        val hours = timeInMilliseconds / 3600000
        timeShow.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        timeInput.visibility = View.INVISIBLE



    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        timeInput.hideKeyboard()
        return super.onTouchEvent(event)
    }
}






