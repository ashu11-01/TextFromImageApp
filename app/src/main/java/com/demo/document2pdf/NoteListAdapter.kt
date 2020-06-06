package com.demo.document2pdf

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.demo.document2pdf.Room.NoteModel

class NoteListAdapter(val context: Context?, val noteList: List<NoteModel>?) : RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {

    val activity : ItemClicked = context as ItemClicked

    interface ItemClicked{
        fun onItemClicked(position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNoteTitle : TextView = itemView.findViewById(R.id.tv_note_title)
        val tvNoteText : TextView = itemView.findViewById(R.id.tv_note_text)

        init{
            itemView.setOnClickListener(View.OnClickListener(){
                val clickedNotePosition : Int = itemView.tag as Int
                activity.onItemClicked(clickedNotePosition)
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.note_list_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return noteList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = noteList?.get(position)
        holder.itemView.tag = position
        holder.tvNoteTitle.text = note?.noteTitle
        holder.tvNoteText.text = note?.noteText
    }

}

