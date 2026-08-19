package com.example.volumepowerapp

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.os.PowerManager
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent

class VolumeAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Accessibility events are not required here.
    }

    override fun onInterrupt() {
        // Called when the accessibility service is interrupted.
    }

    override fun onKeyEvent(event: KeyEvent?): Boolean {

        if (event == null) {
            return super.onKeyEvent(event)
        }

        val action = event.action
        val keyCode = event.keyCode

        if (action == KeyEvent.ACTION_DOWN) {

            when (keyCode) {

                KeyEvent.KEYCODE_VOLUME_UP,
                KeyEvent.KEYCODE_VOLUME_DOWN -> {

                    toggleScreen()

                    // Consume the volume key event.
                    return true
                }
            }
        }

        return super.onKeyEvent(event)
    }

    private fun toggleScreen() {

        val powerManager =
            getSystemService(Context.POWER_SERVICE) as PowerManager

        if (!powerManager.isInteractive) {

            // Screen is OFF.
            // Wake the screen.

            val wakeLock = powerManager.newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK or
                        PowerManager.ACQUIRE_CAUSES_WAKEUP,
                "VolumeScreenControl:WakeLock"
            )

            wakeLock.acquire(3000)

        } else {

            // Screen is ON.
            // Lock the device / turn screen off.

            performGlobalAction(
                GLOBAL_ACTION_LOCK_SCREEN
            )
        }
    }
}