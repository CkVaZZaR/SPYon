package com.example.spyon

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbSettings(val context: Context, factory: SQLiteDatabase.CursorFactory?):
    SQLiteOpenHelper(context, "settingsDB", factory, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE settings (id INT PRIMARY KEY, setting TEXT, value INT)"
        db!!.execSQL(query)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        p1: Int,
        p2: Int
    ) {
        db!!.execSQL("DROP TABLE IF EXISTS settings")
        onCreate(db)
    }

    fun delSetting(setting: String) {
        val db = this.writableDatabase
        db.delete("settings", "setting = ?", arrayOf(setting))

        db.close()
    }

    fun getSetting(setting: String): Boolean {
        val db = this.readableDatabase

        val result = db.rawQuery("SELECT DISTINCT value FROM settings WHERE setting = '$setting'", null)

        return result.use { it.moveToFirst() }
    }

    fun setSetting(setting: String, value: Int) {
        val db = this.writableDatabase

        if (getSetting(setting)) {
            delSetting(setting)
        }

        val values = ContentValues()

        values.put("setting", setting)
        values.put("value", value)

        db.insert("settings", null, values)

        db.close()
    }
}