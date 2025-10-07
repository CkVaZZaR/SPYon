package com.example.spyon

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbSettings(val context: Context, factory: SQLiteDatabase.CursorFactory?):
    SQLiteOpenHelper(context, "settingsDB", factory, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE settings (id INT PRIMARY KEY, setting TEXT, value TEXT)"
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
    }

    fun getSetting(setting: String): Boolean {
        val db = this.readableDatabase

        val result = db.rawQuery("SELECT DISTINCT value FROM settings WHERE setting = '$setting'", null)

        return result.use { it.moveToFirst() }
    }

    fun getValue(setting: String): String? {
        val db = this.readableDatabase

        val cursor = db.query(
            "settings",
            arrayOf("value"),
            "setting = ?",
            arrayOf(setting),
            null, null, null
        )

        val result = if (cursor.moveToFirst()) cursor.getString(0) else null
        cursor.close()
        return result
    }

    fun setSetting(setting: String, value: String) {
        val db = this.writableDatabase

        if (getSetting(setting)) {
            delSetting(setting)
        }

        val values = ContentValues()

        values.put("setting", setting)
        values.put("value", value)

        db.insert("settings", null, values)
    }
}