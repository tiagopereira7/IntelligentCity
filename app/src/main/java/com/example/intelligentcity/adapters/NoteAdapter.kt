package com.example.intelligentcity.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.intelligentcity.R
import com.example.intelligentcity.entities.Note

class NoteAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notes = emptyList<Note>()

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteItemView: TextView = itemView.findViewById(R.id.linha_titulo)
        val textItemView: TextView = itemView.findViewById(R.id.linha_texto)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = inflater.inflate(R.layout.layout_linha, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(viewholder: NoteViewHolder, position: Int) {
        val current = notes[position]
        viewholder.noteItemView.text = current.title
        viewholder.textItemView.text = current.text
    }

    internal fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun getItemCount() = notes.size
}