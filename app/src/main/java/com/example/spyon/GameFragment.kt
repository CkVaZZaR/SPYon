package com.example.spyon

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.toColor
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GameFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        val roleCardView: ImageView = view.findViewById(R.id.role_card_view)
        val playerNikname: TextView = view.findViewById(R.id.player_nikname_in_game)
        val playerRole: TextView = view.findViewById(R.id.player_role)
        val gamePlace: TextView = view.findViewById(R.id.game_place)
        val reroleButton: Button = view.findViewById(R.id.rerole_button)
        val previousButton: Button = view.findViewById(R.id.previous_button)
        val showRoleButton: MaterialButton = view.findViewById(R.id.show_role_button)
        val nextButton: Button = view.findViewById(R.id.next_button)

        val ge = GameEngine(requireContext())
        val playerList = ge.getAllPlayers()
        var placeString = ge.getRandomPlace()
        var playerIndex = 0
        var spy = ge.getRandomPlayer()

        playerNikname.text = playerList[playerIndex]

        fun upgradeIndex(index: Int): Int {
            if (index + 1 == playerList.count()) return 0
            return index + 1
        }

        fun minusIndex(index: Int): Int {
            if (index - 1 == -1) return playerList.count() - 1
            return index - 1
        }

        @SuppressLint("SetTextI18n")
        fun roleShower(roleShowed: Boolean, role: String, place: String): Boolean {
            if (roleShowed) {
                showRoleButton.setIconResource(R.drawable.show)
                roleCardView.setImageResource(R.drawable.spyon_card_2_black_filled)
                playerRole.text = ""
                gamePlace.text = ""
            } else {
                showRoleButton.setIconResource(R.drawable.not_show)
                roleCardView.setImageResource(R.drawable.spyon_card_2_black_empty)
                playerRole.text = getString(R.string.game_frag_role) + ": $role"
                gamePlace.text = getString(R.string.game_frag_place) + ": $place"
            }
            return !roleShowed
        }

        var roleShowed = roleShower(true, "", "")

        showRoleButton.setOnClickListener {
            val place: String
            val role: String
            if (playerList[playerIndex] == spy) {
                role = getString(R.string.game_frag_roleSPY)
                place = "???"
            } else {
                role = getString(R.string.game_frag_rolePeaceful)
                place = placeString.toString()
            }
            roleShowed = roleShower(roleShowed,role, place)
        }

        reroleButton.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.game_frag_rerole_alert)
                .setPositiveButton(R.string.agree) { dialog, _ ->
                    playerIndex = 0
                    spy = ge.getRandomPlayer()
                    val prevPlace = placeString
                    playerNikname.text = playerList[playerIndex]
                    while (prevPlace == placeString) {
                        placeString = ge.getRandomPlace()
                    }
                    roleShowed = roleShower(true, "", "")
                }
                .setNegativeButton(R.string.disagree) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        nextButton.setOnClickListener {
            playerIndex = upgradeIndex(playerIndex)
            roleShowed = roleShower(true, "", "")
            playerNikname.text = playerList[playerIndex]
        }

        previousButton.setOnClickListener {
            playerIndex = minusIndex(playerIndex)
            roleShowed = roleShower(true, "", "")
            playerNikname.text = playerList[playerIndex]
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}