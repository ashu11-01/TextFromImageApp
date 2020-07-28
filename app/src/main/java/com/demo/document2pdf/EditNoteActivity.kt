package com.demo.document2pdf

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.demo.document2pdf.Room.NoteModel
import com.demo.document2pdf.Room.NotesDatabase
import kotlinx.coroutines.InternalCoroutinesApi

class EditNoteActivity : AppCompatActivity() {

    lateinit var noteText : String
    var noteId : Int =-1
    lateinit var noteTitle : String
    lateinit var etNoteTitle : EditText
    lateinit var etNoteText: EditText
    lateinit var db : NotesDatabase
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_note)
        db = NotesDatabase.getDatabase(this)
        val previousScreen : Intent = intent
        noteId = previousScreen.getIntExtra("noteId",-1)
        noteTitle = previousScreen.getStringExtra("noteTitle")
        noteText = previousScreen.getStringExtra("noteText")
        etNoteText = findViewById(R.id.et_text_input)
        etNoteText.setText(noteText)
        etNoteTitle = findViewById(R.id.et_title_input)
        etNoteTitle.setText(noteTitle)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_delete_share_note_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_update_saved -> updateNote()
            R.id.menu_delete_saved -> deleteNote()
            R.id.share_saved -> shareNote()

        }
        return true
    }

    private fun shareNote() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,noteText)
        intent.putExtra(Intent.EXTRA_SUBJECT,noteTitle)
        startActivity(intent)
    }

    private fun deleteNote() {
        val builder : AlertDialog.Builder = AlertDialog.Builder(this)
        val deleteAlertDialog : AlertDialog = builder.create()
        builder.setTitle("Confirm Delete Note")
        builder.setMessage("Are you sure you want to delete this note?")
        val positiveListener = {
                dialog: DialogInterface, which:Int ->
            val noteModel : NoteModel = NoteModel()
            noteModel.id = noteId
            noteModel.noteText = noteText
            noteModel.noteTitle = noteTitle
            db.notesDao().deleteNote(noteModel)
            Toast.makeText(this,getString(R.string.note_deleted),Toast.LENGTH_SHORT).show()
            finish()
        }
        val negativeListener = {
            _:DialogInterface,_:Int ->
        }
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener(function= positiveListener))
        builder.setNegativeButton("No",DialogInterface.OnClickListener(function = negativeListener))
        builder.setCancelable(true)
        builder.show()
    }


    private fun updateNote(){
        val newNote = NoteModel()
        newNote.id = noteId
        noteText = etNoteText.text.toString()
        noteTitle = etNoteTitle.text.toString()
        newNote.noteTitle = noteTitle
        newNote.noteText = noteText
        /*newNote.noteCreatedDate = Date()
        newNote.noteModifiedDate = Date()*/
        db.notesDao().updateNote(newNote)
        Toast.makeText(this,getString(R.string.note_updated), Toast.LENGTH_SHORT).show()
        finish()
    }
}
