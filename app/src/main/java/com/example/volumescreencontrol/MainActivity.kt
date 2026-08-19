package com.example.volumepowerapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.view.Gravity

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(32, 32, 32, 32)
        }

        val title = TextView(this).apply {
            text = "Volume Button Screen Control"
            textSize = 22f
            gravity = Gravity.CENTER
        }

        val info = TextView(this).apply {
            text = """
                Enable the Accessibility Service below.

                Volume Up = Wake Screen
                Volume Down = Turn Screen Off
            """.trimIndent()

            textSize = 16f
            gravity = Gravity.CENTER
            setPadding(0, 24, 0, 24)
        }

        val button = Button(this).apply {
            text = "Open Accessibility Settings"

            setOnClickListener {
                startActivity(
                    Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                )
            }
        }

        val aboutButton = Button(this).apply {
            text = "About Developer"

            setOnClickListener {
                startActivity(
                    Intent(this@MainActivity, AboutActivity::class.java)
                )
            }
        }

        layout.addView(title)
        layout.addView(info)
        layout.addView(button)
        layout.addView(aboutButton)

        setContentView(layout)
    }
}