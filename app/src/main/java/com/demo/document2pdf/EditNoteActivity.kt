package com.demo.document2pdf

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditNoteActivity : AppCompatActivity() {

    lateinit var noteText : String
    lateinit var etNoteTitle : EditText
    lateinit var etNoteText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        val previousScreen : Intent = intent
        noteText = intent.getStringExtra("noteText")
        etNoteText = findViewById(R.id.et_text_input)
        etNoteText.setText(noteText)
        etNoteTitle = findViewById(R.id.et_title_input)
        etNoteTitle.setHint(R.string.note_title)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.note_detail_save_delete_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.note_save_menu -> saveCurrentNote()
            R.id.note_delete_menu -> deleteCurrentNote()
        }
        return true
    }

    private fun deleteCurrentNote(){
        Toast.makeText(this,"Note deleted",Toast.LENGTH_SHORT).show()
    }

    private fun saveCurrentNote(){
        Toast.makeText(this,"Note Saved",Toast.LENGTH_SHORT).show()
    }
}
