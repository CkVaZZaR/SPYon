package com.example.spyon

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(val context: Context, factory: SQLiteDatabase.CursorFactory?):
    SQLiteOpenHelper(context, "gameDB", factory, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE players (id INT PRIMARY KEY, nikname TEXT)"
        db!!.execSQL(query)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        p1: Int,
        p2: Int
    ) {
        db!!.execSQL("DROP TABLE IF EXISTS players")
        onCreate(db)
    }

    fun addPlayer(player: Player) {
        val values = ContentValues()
        values.put("nikname", player.nikname)

        val db = this.writableDatabase
        db.insert("players", null, values)

        db.close()
    }

    fun delPlayer(nikname: String) {
        val db = this.writableDatabase
        db.delete("players", "nikname = ?", arrayOf(nikname))

        db.close()
    }

    fun getUser(login: String): Boolean {
        val db = this.readableDatabase

        val result = db.rawQuery("SELECT * FROM players WHERE nikname = '$login'", null)

        return result.use { it.moveToFirst() }
    }


    @SuppressLint("Range")
    fun getAll(): MutableList<String> {
        val userList = mutableListOf<String>()
        val db = this.readableDatabase

        val cursor: Cursor = db.rawQuery("SELECT * FROM players", null)

        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val nikname = it.getString(it.getColumnIndex("nikname"))

//                    val user = User(id, nikname, email)
                    userList.add(nikname.toString())
                } while (it.moveToNext())
            }
        }

        return userList
    }

}