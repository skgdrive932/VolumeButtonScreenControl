package com.example.volumescreencontrol

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.os.PowerManager
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

class VolumeAccessibilityService : AccessibilityService() {

    private var lastAction = 0L
    private val debounceMs = 500L

    override fun onServiceConnected() {
        super.onServiceConnected()
        Toast.makeText(this, "Volume Screen Control is active", Toast.LENGTH_SHORT).show()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) = Unit

    override fun onInterrupt() = Unit

    override fun onKeyEvent(event: KeyEvent): Boolean {
        val isVolumeKey = event.keyCode == KeyEvent.KEYCODE_VOLUME_UP ||
                event.keyCode == KeyEvent.KEYCODE_VOLUME_DOWN

        if (!isVolumeKey) return false

        // Consume both DOWN and UP so Android does not also change the volume.
        if (event.action == KeyEvent.ACTION_DOWN) {
            val now = System.currentTimeMillis()
            if (now - lastAction >= debounceMs) {
                lastAction = now
                when (event.keyCode) {
                    KeyEvent.KEYCODE_VOLUME_UP -> wakeScreen()
                    KeyEvent.KEYCODE_VOLUME_DOWN -> lockScreen()
                }
            }
        }
        return true
    }

    private fun wakeScreen() {
        val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        @Suppress("DEPRECATION")
        val lock = pm.newWakeLock(
            PowerManager.SCREEN_BRIGHT_WAKE_LOCK or
                    PowerManager.ACQUIRE_CAUSES_WAKEUP or
                    PowerManager.ON_AFTER_RELEASE,
            "VolumeScreenControl:WakeScreen"
        )
        lock.acquire(3000L)
        lock.release()
    }

    private fun lockScreen() {
        performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN)
    }
}
