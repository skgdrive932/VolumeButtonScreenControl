package com.example.volumepowerapp

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.os.PowerManager
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent

class VolumeAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Accessibility events handle karne ke liye
    }

    override fun onInterrupt() {
        // Service interrupt hone par
    }

    override fun onKeyEvent(event: KeyEvent?): Boolean {
        if (event == null) return super.onKeyEvent(event)

        val action = event.action
        val keyCode = event.keyCode

        // Volume Key press hone par Screen Control Handle karein
        if (action == KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_VOLUME_DOWN -> {
                    toggleScreen()
                    return true // Key press consume kar li gayi
                }
            }
        }
        return super.onKeyEvent(event)
    }

    private fun toggleScreen() {
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        
        if (!powerManager.isInteractive) {
            // Screen Off hai, toh turn ON karein (WakeUp fix)
            val wakeLock = powerManager.newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
                "VolumeScreenControl:WakeLock"
            )
            wakeLock.acquire(3000) // 3 seconds ke liye Screen ON
        } else {
            // Screen ON hai, toh turn OFF (Lock) karein
            performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN)
        }
    }
}
