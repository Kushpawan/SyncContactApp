package com.example.testassignment.NewsApp.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.kushpawan.synccontactapp.ContactData

@Dao
interface ToDoDao {

    @Insert
    fun insertContacts(articles: List<ContactData>): List<Long>

    @Insert
    fun insertContact(articles: ContactData): Long

    @Query("DELETE FROM contact_data")
    fun clearAllContact()

    @Transaction
    fun clearAndCacheContact(articles: List<ContactData>) {
        clearAllContact()
        insertContacts(articles)
    }

    @Query("SELECT * FROM contact_data")
    fun getAllContacts(): LiveData<List<ContactData>>
}