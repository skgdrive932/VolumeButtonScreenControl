package com.example.volumescreencontrol

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.os.PowerManager
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

class VolumeAccessibilityService : AccessibilityService() {

    private var wakeLock: PowerManager.WakeLock? = null

    override fun onCreate() {
        super.onCreate()
        // Screen OFF me CPU ko awake rakhne ke liye PARTIAL_WAKE_LOCK
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            "VolumeScreenControl:KeepAlive"
        )
        wakeLock?.acquire()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}
    override fun onInterrupt() {}

    override fun onKeyEvent(event: KeyEvent?): Boolean {
        if (event == null) return false

        if (event.keyCode == KeyEvent.KEYCODE_VOLUME_UP || event.keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            if (event.action == KeyEvent.ACTION_DOWN) {
                toggleScreen()
                return true
            }
        }
        return super.onKeyEvent(event)
    }

    private fun toggleScreen() {
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        val isScreenOn = powerManager.isInteractive

        if (!isScreenOn) {
            @Suppress("DEPRECATION")
            val screenWake = powerManager.newWakeLock(
                PowerManager.FULL_WAKE_LOCK or
                PowerManager.ACQUIRE_CAUSES_WAKEUP or
                PowerManager.ON_AFTER_RELEASE,
                "VolumeScreenControl:WakeScreen"
            )
            screenWake.acquire(3000)
        } else {
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
