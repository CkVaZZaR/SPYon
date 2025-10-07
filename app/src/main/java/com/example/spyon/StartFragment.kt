package com.example.spyon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.android.material.button.MaterialButton

class StartFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start, container, false)

        val startButton: Button = view.findViewById(R.id.start_start_game)
        val addPlayers: Button = view.findViewById(R.id.start_add_players)

        val settingsButton: MaterialButton = requireActivity().findViewById(R.id.setting_button)
        val playersButton: MaterialButton = requireActivity().findViewById(R.id.players_button)
        val gameButton: MaterialButton = requireActivity().findViewById(R.id.game_button)

        val db = DbHelper(requireContext(), null)
        val playerCount = db.getAll().count()

        fun setNewFragment(frag: Fragment) {
            parentFragmentManager.beginTransaction().run {
                replace(R.id.frame_layout, frag)
                commit()
            }
        }

        fun setAllClear() {
            settingsButton.setIconResource(R.drawable.settings_clear)
            playersButton.setIconResource(R.drawable.players_clear)
            gameButton.setIconResource(R.drawable.game_clear)
        }

        startButton.setOnClickListener {
            if (playerCount > 2) {
                setNewFragment(GameFragment())
            } else {
                Toast.makeText(requireContext(), getString(R.string.start_frag_toast_addPlayers), Toast.LENGTH_SHORT).show()
            }
        }

        addPlayers.setOnClickListener {
            setAllClear()
            playersButton.setIconResource(R.drawable.players_filled)
            setNewFragment(PlayersFragment())
        }

        return view
    }
}