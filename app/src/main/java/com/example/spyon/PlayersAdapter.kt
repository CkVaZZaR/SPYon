package com.example.spyon

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class PlayersAdapter(val players: MutableList<String>, var context: Context): RecyclerView.Adapter<PlayersAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.player_in_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.name.text = players[position]
    }

    override fun getItemCount(): Int {
        return players.count()
    }

    fun addPlayer(nikname: String) {
        players.add(nikname)
        notifyItemInserted(players.count() - 1)
    }

    fun delPlayer(position: Int) {
        val db = DbHelper(context, null)

        val nikname = players[position]
        Log.i("ADAPTER_DELETION", nikname)
        db.delPlayer(nikname)
        players.remove(nikname)
        notifyItemRemoved(position)
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.player_nikname)
        val delBtn: Button = view.findViewById(R.id.delete_player_button)

        init {
            delBtn.setOnClickListener {
                delPlayer(bindingAdapterPosition)
            }
        }
    }
}