package com.example.lab4

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf

class NoteDBHelper(context:Context): SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "notesapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allnotes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITTLE = "tittle"
        private const val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase?) {
       val createTableQuery = "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_TITTLE TEXT,$COLUMN_DESCRIPTION TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }
    fun insertNote(note:Note){
        val db = writableDatabase //writable database can be modified
        val values = ContentValues().apply {
            put(COLUMN_TITTLE, note.tittle)
            put(COLUMN_DESCRIPTION, note.description)
        }
        db.insert(TABLE_NAME,null,values)
        db.close()

    }

    fun getAllNote():List<Note>{
        val noteList = mutableListOf<Note>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query,null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITTLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))

            val note = Note(id,title,content)
            noteList.add(note)


        }
        cursor.close()
        db.close()
        return noteList
    }

    fun updateNote(note:Note){
        val db = readableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITTLE,note.tittle)
            put(COLUMN_DESCRIPTION,note.description)

        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(note.id.toString())
        db.update(TABLE_NAME,values,whereClause,whereArgs)
        db.close()
    }
    fun getNoteById(noteId:Int): Note {
        val db = readableDatabase
        val query =  "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID=$noteId"
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITTLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))

        cursor.close()
        db.close()
        return Note(id,title,content)

    }

    fun deleteNote(noteId:Int){
        val db = readableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(noteId.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()
    }

    fun searchNotes(query: String): List<Note> {
        val noteList = mutableListOf<Note>()
        val db = readableDatabase
        val selection = "$COLUMN_TITTLE LIKE ?"
        val selectionArgs = arrayOf("%$query%")
        val cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITTLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))

            val note = Note(id, title, content)
            noteList.add(note)
        }
        cursor.close()
        db.close()
        return noteList
    }


}