package com.example.lab4

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private var notes: List<Note>, context: Context) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    val noteDbHelper = NoteDBHelper(context)


    class NoteViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val titleText:TextView = itemView.findViewById(R.id.tittleText)
        val contentText:TextView = itemView.findViewById(R.id.contentText)
        val delete:ImageView = itemView.findViewById(R.id.delete)
        val update:ImageView = itemView.findViewById(R.id.update)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)


        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {

        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]

        holder.titleText.text = note.tittle
        holder.contentText.text = note.description
        holder.update.setOnClickListener(){
            val intent = Intent(holder.itemView.context,UpdateNote::class.java).apply {
                putExtra("note_id",note.id)

            }
            holder.itemView.context.startActivity(intent)
        }

        holder.delete.setOnClickListener(){
            noteDbHelper.deleteNote(note.id)
            refreshData(noteDbHelper.getAllNote())


        }

    }

    fun refreshData(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }



}