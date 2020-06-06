package com.demo.document2pdf.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
class NoteModel{
    @PrimaryKey (autoGenerate = true)
    var id : Int =0
    var noteTitle : String = ""
    var noteText : String = ""
    /*@TypeConverters(DateConverter::class)
    var noteCreatedDate : Date = Date()
    @TypeConverters(DateConverter::class)
    var noteModifiedDate : Date = Date()*/
}
