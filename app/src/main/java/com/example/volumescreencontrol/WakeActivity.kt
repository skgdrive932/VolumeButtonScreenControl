package com.example.volumescreencontrol

import android.app.Activity
import android.os.Bundle
import android.view.WindowManager

class WakeActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Android 10+
        setShowWhenLocked(true)
        setTurnScreenOn(true)

        // Older Android compatibility
        window.addFlags(
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
            WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
            WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        )

        // Transparent activity ko immediately close kar do.
        window.decorView.postDelayed({
            finish()
        }, 150)
    }
}