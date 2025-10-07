package com.example.spyon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.widget.SwitchCompat

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val languageSwitcher: SwitchCompat = view.findViewById(R.id.languageSwitcher)
        val db = DbSettings(requireContext(), null)

        languageSwitcher.setOnClickListener {
            if (db.getValue("language") == "en") {
                LocaleHelper.setLocale(requireActivity(), "ru")
                db.setSetting("language", "ru")
                requireActivity().recreate()
            } else {
                LocaleHelper.setLocale(requireActivity(), "en")
                db.setSetting("language", "en")
                requireActivity().recreate()
            }
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}