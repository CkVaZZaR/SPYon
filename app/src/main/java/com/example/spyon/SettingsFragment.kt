package com.example.spyon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val languageNow: TextView = view.findViewById(R.id.language_text_now)
        val languageLayout: ConstraintLayout = view.findViewById(R.id.language_layout)
        val placesButton: TextView = view.findViewById(R.id.place_list_button)

        val db = DbSettings(requireContext(), null)

        languageNow.text = db.getValue("language")

        val items = arrayOf("Russian", "English")
        val langCode = arrayOf("ru", "en")
        var checkedItem = items.indexOf(db.getValue("language"))

        languageLayout.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.settings_frag_language)
                .setSingleChoiceItems(items, checkedItem) { dialog, which ->
                    checkedItem = which
                }
                .setPositiveButton(R.string.apply) { dialog, which ->
                    db.setSetting("language", items[checkedItem])
                    LocaleHelper.setLocale(requireActivity(), langCode[checkedItem])
                    languageNow.text = items[checkedItem]
                }
                .setNegativeButton(R.string.cancel, null)
                .show()
        }

        placesButton.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.settings_frag_placeList)
                .setNegativeButton(R.string.close) { dialog, _ ->
                    dialog.dismiss()
                }
                .setItems(resources.getStringArray(R.array.places_array).sortedArray(), null)
                .show()
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}