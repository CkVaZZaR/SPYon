package com.example.spyon

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton

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

        val dbSet = DbSettings(this, null)

        if (!dbSet.getSetting("language")) {

            dbSet.setSetting("language", "English")
        }

        LocaleHelper.applySavedLocale(this)

        val settingsButton: MaterialButton = findViewById(R.id.setting_button)
        val playersButton: MaterialButton = findViewById(R.id.players_button)
        val gameButton: MaterialButton = findViewById(R.id.game_button)

        val settingFragment = SettingsFragment()
        val playerFragment = PlayersFragment()
        val startFragment = StartFragment()

        fun setNewFragment(frag: Fragment) {
            supportFragmentManager.beginTransaction().run {
                replace(R.id.frame_layout, frag)
                commit()
            }
        }

        fun setAllClear() {
            settingsButton.setIconResource(R.drawable.settings_clear)
            playersButton.setIconResource(R.drawable.players_clear)
            gameButton.setIconResource(R.drawable.game_clear)
        }

        setNewFragment(startFragment)

        settingsButton.setOnClickListener {
            setAllClear()
            settingsButton.setIconResource(R.drawable.settings_filled)
            setNewFragment(settingFragment)
        }

        gameButton.setOnClickListener {
            setAllClear()
            gameButton.setIconResource(R.drawable.game_filled)
            setNewFragment(startFragment)
        }

        playersButton.setOnClickListener {
            setAllClear()
            playersButton.setIconResource(R.drawable.players_filled)
            setNewFragment(playerFragment)
        }
    }
}

