package android.example.com.timecount

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var timerTextView: TextView
    private lateinit var startButton: Button
    private lateinit var stopButton: Button

    private var seconds = 0  // Biến lưu thời gian đã trôi qua
    private var running = false
    private val handler = Handler(Looper.getMainLooper()) // Handler để cập nhật UI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timerTextView = findViewById(R.id.timerTextView)
        startButton = findViewById(R.id.startButton)
        stopButton = findViewById(R.id.stopButton)

        startButton.setOnClickListener {
            running = true
            runTimer()
        }

        stopButton.setOnClickListener {
            running = false
        }
    }

    private fun runTimer() {
        val runnable = object : Runnable {
            override fun run() {
                if (running) {
                    seconds++
                    val hours = seconds / 3600
                    val minutes = (seconds % 3600) / 60
                    val secs = seconds % 60
                    timerTextView.text = String.format("%02d:%02d:%02d", hours, minutes, secs)

                    handler.postDelayed(this, 1000) // Lặp lại sau mỗi giây
                }
            }
        }
        handler.post(runnable)
    }
}
