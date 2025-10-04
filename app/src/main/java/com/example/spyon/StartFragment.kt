package com.example.spyon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

class StartFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start, container, false)

        val startButton: Button = view.findViewById(R.id.start_start_game)
        val addPlayers: Button = view.findViewById(R.id.start_add_players)

        val db = DbHelper(requireContext(), null)
        val playerCount = db.getAll().count()

        fun setNewFragment(frag: Fragment) {
            parentFragmentManager.beginTransaction().run {
                replace(R.id.frame_layout, frag)
                commit()
            }
        }

        startButton.setOnClickListener {
            if (playerCount > 2) {
                setNewFragment(GameFragment())
            } else {
                Toast.makeText(requireContext(), getString(R.string.start_frag_toast_addPlayers), Toast.LENGTH_SHORT).show()
            }
        }

        addPlayers.setOnClickListener {
            setNewFragment(PlayersFragment())
        }

        return view
    }
}