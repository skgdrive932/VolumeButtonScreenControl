package com.example.volumescreencontrol

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

class VolumeAccessibilityService : AccessibilityService() {

    private var lastAction = 0L

    // Prevent accidental double triggering
    private val debounceMs = 300L

    override fun onServiceConnected() {
        super.onServiceConnected()

        Toast.makeText(
            this,
            "Volume Screen Control is active",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Not required
    }

    override fun onInterrupt() {
        // Not required
    }

    override fun onKeyEvent(event: KeyEvent): Boolean {

        // We only care about volume buttons
        val isVolumeKey =
            event.keyCode == KeyEvent.KEYCODE_VOLUME_UP ||
            event.keyCode == KeyEvent.KEYCODE_VOLUME_DOWN

        if (!isVolumeKey) {
            return false
        }

        /*
         * We handle the action only when the key is pressed DOWN.
         */
        if (event.action == KeyEvent.ACTION_DOWN) {

            val now = System.currentTimeMillis()

            if (now - lastAction >= debounceMs) {

                lastAction = now

                when (event.keyCode) {

                    KeyEvent.KEYCODE_VOLUME_UP -> {
                        wakeScreen()
                    }

                    KeyEvent.KEYCODE_VOLUME_DOWN -> {
                        lockScreen()
                    }
                }
            }
        }

        /*
         * Return true so Android does not also process
         * the volume button as a normal volume action.
         */
        return true
    }

    /**
     * Wake the screen by launching a transparent Activity.
     *
     * WakeActivity uses:
     * setTurnScreenOn(true)
     * setShowWhenLocked(true)
     */
    private fun wakeScreen() {

        try {

            val intent = Intent(
                this,
                WakeActivity::class.java
            )

            intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_NO_ANIMATION or
                        Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
            )

            startActivity(intent)

        } catch (e: Exception) {

            e.printStackTrace()

            Toast.makeText(
                this,
                "Unable to wake screen",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * Lock / turn screen off.
     *
     * AccessibilityService has permission to perform
     * the global lock-screen action.
     */
    private fun lockScreen() {

        performGlobalAction(
            GLOBAL_ACTION_LOCK_SCREEN
        )
    }
}