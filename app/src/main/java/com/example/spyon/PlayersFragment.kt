package com.example.spyon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PlayersFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_players, container, false)

        val addBtn: Button = view.findViewById(R.id.add_player_button)
        val editPlayer: EditText = view.findViewById(R.id.add_player_field)
        val playersListView: RecyclerView = view.findViewById(R.id.added_players_list)

        val db = DbHelper(requireContext(), null)

        val playersList: MutableList<String> = db.getAll()
        val playersAdapter = PlayersAdapter(playersList, requireContext())

        playersListView.layoutManager = LinearLayoutManager(requireContext())
        playersListView.adapter = playersAdapter

        addBtn.setOnClickListener {
            val text = editPlayer.text.toString().trim()
            if (text == "") {
                Toast.makeText(requireContext(), getString(R.string.players_frag_toast_enterNick), Toast.LENGTH_SHORT).show()
            } else {
                if (db.getUser(text)) {
                    Toast.makeText(requireContext(), getString(R.string.players_frag_toast_userAlreadyExists), Toast.LENGTH_SHORT).show()
                } else {
                    val player = Player(text)
                    db.addPlayer(player)
                    playersAdapter.addPlayer(text)
                }
                editPlayer.text.clear()
            }
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}