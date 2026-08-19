package com.example.volumepowerapp

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.os.PowerManager
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent

class VolumeScreenService : AccessibilityService() {

    private var wakeLock: PowerManager.WakeLock? = null

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}

    override fun onInterrupt() {}

    override fun onKeyEvent(event: KeyEvent?): Boolean {
        if (event == null) return super.onKeyEvent(event)

        val action = event.action
        val keyCode = event.keyCode

        if (action == KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_VOLUME_DOWN -> {
                    toggleScreen()
                    return true // Key press consume ho gayi
                }
            }
        }
        return super.onKeyEvent(event)
    }

    private fun toggleScreen() {
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        val isScreenOn = powerManager.isInteractive

        if (!isScreenOn) {
            // Screen OFF hai -> ON karein
            wakeLock = powerManager.newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK or 
                PowerManager.ACQUIRE_CAUSES_WAKEUP or 
                PowerManager.ON_AFTER_RELEASE,
                "VolumeScreenControl:WakeLock"
            )
            wakeLock?.acquire(5000) // 5 sec ke liye screen ON rakhega
        } else {
            // Screen ON hai -> OFF (Lock) karein
            performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (wakeLock?.isHeld == true) {
            wakeLock?.release()
        }
    }
}
