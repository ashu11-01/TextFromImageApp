package com.demo.document2pdf.Room

import androidx.room.*

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNote(note : NoteModel)

    @Delete
    fun deleteNote(note : NoteModel)

    @Update
    fun updateNote(note: NoteModel)

    @Query("SELECT * from notes_table")
    fun getMostRecentModifiedNote() : List<NoteModel>


}