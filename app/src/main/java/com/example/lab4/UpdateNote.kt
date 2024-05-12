package com.example.lab4

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lab4.databinding.ActivityMainBinding
import com.example.lab4.databinding.ActivityUpdateNoteBinding

class UpdateNote : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var db:NoteDBHelper
    private var noteId:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDBHelper(this)

        noteId = intent.getIntExtra("note_id",-1)
        if (noteId== -1){
            finish()
            return
        }

        val note = db.getNoteById(noteId)
        binding.updateTittle.setText(note.tittle)
        binding.updateDes.setText(note.description)

        binding.updateSaveButton.setOnClickListener(){
            val newTittle = binding.updateTittle.text.toString()
            val newContent = binding.updateDes.text.toString()
            val updateNote = Note(noteId,newTittle,newContent)
            db.updateNote(updateNote)
            finish()
        }


    }
}