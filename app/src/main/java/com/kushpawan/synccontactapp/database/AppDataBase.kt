package com.example.testassignment.NewsApp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kushpawan.synccontactapp.ContactData

@Database(entities = [ContactData::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): ToDoDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "contact-db.db"
        ).build()
    }
}