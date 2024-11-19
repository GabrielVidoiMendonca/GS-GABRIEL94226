package com.example.gabrielmendonca_rm94226.data

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DicasDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dicas_energia_verde.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_DICAS = "dicas"
        const val COLUMN_ID = "id"
        const val COLUMN_TITULO = "titulo"
        const val COLUMN_DESCRICAO = "descricao"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_DICAS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_TITULO TEXT, " +
                "$COLUMN_DESCRICAO TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_DICAS")
        onCreate(db)
    }

    fun insertDica(titulo: String, descricao: String) {
        val db = writableDatabase
        val insertQuery = "INSERT INTO $TABLE_DICAS ($COLUMN_TITULO, $COLUMN_DESCRICAO) VALUES (?, ?)"
        db.execSQL(insertQuery, arrayOf(titulo, descricao))
        db.close()
    }

    @SuppressLint("Range")
    fun getAllDicas(): List<Dica> {
        val dicas = mutableListOf<Dica>()
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_DICAS", null)

        if (cursor.moveToFirst()) {
            do {
                val titulo = cursor.getString(cursor.getColumnIndex(COLUMN_TITULO))
                val descricao = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRICAO))
                dicas.add(Dica(id = 0, titulo, descricao))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return dicas

    }
    @SuppressLint("Range")
    fun searchDicas(query: String): List<Dica> {
        val db = readableDatabase
        val cursor: Cursor = db.query(
            TABLE_DICAS, null,
            "titulo LIKE ? OR descricao LIKE ?", arrayOf("%$query%", "%$query%"),
            null, null, null
        )
        val dicas = mutableListOf<Dica>()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndex("id"))
                val titulo = cursor.getString(cursor.getColumnIndex("titulo"))
                val descricao = cursor.getString(cursor.getColumnIndex("descricao"))
                dicas.add(Dica(id, titulo, descricao))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return dicas
    }
}
