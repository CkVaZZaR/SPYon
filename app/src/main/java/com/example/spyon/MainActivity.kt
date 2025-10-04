package com.example.spyon

import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val settingsButton: Button = findViewById(R.id.setting_button)
        val playersButton: Button = findViewById(R.id.players_button)
        val gameButton: Button = findViewById(R.id.game_button)
        val frameLay: FrameLayout = findViewById(R.id.frame_layout)

        val gameFragment = GameFragment()
        val settingFragment = SettingsFragment()
        val playerFragment = PlayersFragment()
        val startFragment = StartFragment()

        val placesList: Array<String> = resources.getStringArray(R.array.places_array)

        fun setNewFragment(frag: Fragment) {
            supportFragmentManager.beginTransaction().run {
                replace(R.id.frame_layout, frag)
                commit()
            }
        }

        setNewFragment(startFragment)

        settingsButton.setOnClickListener {
            setNewFragment(settingFragment)
        }

        gameButton.setOnClickListener {
            setNewFragment(startFragment)
        }

        playersButton.setOnClickListener {
            setNewFragment(playerFragment)
        }
    }
}

