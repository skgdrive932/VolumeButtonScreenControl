package com.example.volumescreencontrol

import android.accessibilityservice.AccessibilityService
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import android.os.PowerManager
import android.content.Context
import android.os.Build

class VolumeAccessibilityService : AccessibilityService() {

    private var lastVolumeKeyTime = 0L
    private val debounceMs = 350L

    override fun onAccessibilityEvent(event: AccessibilityEvent?) = Unit

    override fun onInterrupt() = Unit

    override fun onKeyEvent(event: KeyEvent): Boolean {
        if (event.action != KeyEvent.ACTION_DOWN) return false

        val now = System.currentTimeMillis()
        if (now - lastVolumeKeyTime < debounceMs) return false
        lastVolumeKeyTime = now

        val power = getSystemService(Context.POWER_SERVICE) as PowerManager

        when (event.keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                    power.wakeUp(now)
                    return true
                }
            }
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                // Android does not allow a normal third-party app to force-lock
                // the device without device-admin/accessibility privileges.
                // AccessibilityService can perform the global lock action.
                performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN)
                return true
            }
        }
        return false
    }
}
