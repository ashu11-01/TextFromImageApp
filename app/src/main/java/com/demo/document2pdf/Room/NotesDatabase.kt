package com.demo.document2pdf.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database (entities = arrayOf(NoteModel::class), version = 1,exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun notesDao() : NotesDao

    companion object{
        @Volatile
        private var INSTANCE : NotesDatabase?=null

        @InternalCoroutinesApi
        fun getDatabase(context : Context) : NotesDatabase{
            val tempInstance = INSTANCE
            if(tempInstance !=null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, NotesDatabase::class.java, "notes_database").allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}