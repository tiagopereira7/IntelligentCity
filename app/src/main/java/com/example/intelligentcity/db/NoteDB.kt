package com.example.intelligentcity.db

import android.content.Context
import android.icu.text.CaseMap
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.intelligentcity.entities.Note
import com.example.intelligentcity.DAO.NoteDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the City class

// Note: When you modify the database schema, you'll need to update the version number and define a migration strategy
//For a sample, a destroy and re-create strategy can be sufficient. But, for a real app, you must implement a migration strategy.

@Database(entities = arrayOf(Note::class), version = 6, exportSchema = false)
public abstract class NoteDB : RoomDatabase() {

    abstract fun noteDAO(): NoteDAO

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var NoteDAO = database.noteDAO()

                    //NoteDAO.deleteAll()

                    //add note
                    var note = Note(1, "Buraco na estrada", "Cuidado na curva" )
                    NoteDAO.insert(note)
                    note = Note(2, "Arvore caida", "Arvore caida na estrada" )
                    NoteDAO.insert(note)

                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: NoteDB? = null

        fun getDatabase(context: Context, scope: CoroutineScope): NoteDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDB::class.java,
                    "notes_database"
                )

                //estratégia de destruição
                .fallbackToDestructiveMigration()
                .addCallback(WordDatabaseCallback(scope))
                .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}