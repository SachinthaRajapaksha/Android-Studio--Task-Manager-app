package com.example.lab4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.lab4.databinding.ActivityMainBinding
import kotlin.math.log

class AddNote : AppCompatActivity() {
    private lateinit var db:NoteDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        db = NoteDBHelper(this)
        val saveBtn = findViewById<ImageView>(R.id.saveButton)
        val title = findViewById<EditText>(R.id.tittleText)
        val des = findViewById<EditText>(R.id.desText)
        saveBtn.setOnClickListener(){
            val tit = title.text.toString()
            val dess = des.text.toString()
            val note = Note(0,tit,dess)
            db.insertNote(note)
            finish()
            Toast.makeText(this,"Note Saved",Toast.LENGTH_SHORT).show()
        }


    }
}