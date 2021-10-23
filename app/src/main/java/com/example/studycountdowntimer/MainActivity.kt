package com.example.studycountdowntimer

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timeShow = findViewById(R.id.showTime)
        buttonStart = findViewById(R.id.startButton)
        buttonPause = findViewById(R.id.pauseButton)


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
    }


    private var onRun = true
    private var cdTimer: CountDownTimer? = null
    private var timeInMilliseconds = 0L

    private lateinit var timeShow: TextView
    private lateinit var buttonStart: FloatingActionButton
    private lateinit var buttonPause: FloatingActionButton

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
    }

    private fun pauseTime() {
        cdTimer?.cancel()
        buttonPause.visibility = View.INVISIBLE
        buttonStart.visibility = View.VISIBLE

    }

    private fun updateTextUI() {

        val seconds = (timeInMilliseconds / 1000) % 60
        val minutes = (timeInMilliseconds / 60000) % 60
        val hours = timeInMilliseconds / 3600000
        timeShow.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}






