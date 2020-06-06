package com.demo.document2pdf

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.demo.document2pdf.Room.NoteModel
import com.demo.document2pdf.Room.NotesDatabase
import kotlinx.coroutines.InternalCoroutinesApi

class AddNoteActivity : AppCompatActivity() {

    lateinit var noteText : String
    lateinit var etNoteTitle : EditText
    lateinit var etNoteText: EditText
    lateinit var db : NotesDatabase
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_note)
        db = NotesDatabase.getDatabase(this)
        val previousScreen : Intent = intent
        noteText = previousScreen.getStringExtra("noteText")
        etNoteText = findViewById(R.id.et_text_input)
        etNoteText.setText(noteText)
        etNoteTitle = findViewById(R.id.et_title_input)
        etNoteTitle.setHint(R.string.note_title)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.note_detail_save_share_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.note_save_menu -> saveCurrentNote()
        }
        return true
    }


    private fun saveCurrentNote(){
        val newNote = NoteModel()
        newNote.noteTitle = etNoteTitle.text.toString()
        newNote.noteText = etNoteText.text.toString()/*
        newNote.noteCreatedDate = Date()
        newNote.noteModifiedDate = Date()*/
        db.notesDao().insertNote(newNote)
        Toast.makeText(this,"Note Saved",Toast.LENGTH_SHORT).show()
        finish()
    }
}
