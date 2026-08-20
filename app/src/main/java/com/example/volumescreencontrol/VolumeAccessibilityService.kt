package com.example.volumescreencontrol

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent

class VolumeAccessibilityService : AccessibilityService() {

    private var lastKeyTime = 0L
    private val debounceTime = 300L

    override fun onServiceConnected() {
        super.onServiceConnected()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Nothing required here
    }

    override fun onInterrupt() {
        // Nothing required here
    }

    override fun onKeyEvent(event: KeyEvent): Boolean {

        if (event.action != KeyEvent.ACTION_DOWN) {
            return false
        }

        val now = System.currentTimeMillis()

        if (now - lastKeyTime < debounceTime) {
            return true
        }

        lastKeyTime = now

        when (event.keyCode) {

            KeyEvent.KEYCODE_VOLUME_UP -> {

                wakeScreen()

                // Volume Up ko normal volume button action
                // ke liye pass mat karo.
                return true
            }

            KeyEvent.KEYCODE_VOLUME_DOWN -> {

                // Screen OFF / LOCK
                performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN)

                return true
            }
        }

        return false
    }

    private fun wakeScreen() {

        try {
            val intent = Intent(this, WakeActivity::class.java)

            intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_NO_ANIMATION or
                Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
            )

            startActivity(intent)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}