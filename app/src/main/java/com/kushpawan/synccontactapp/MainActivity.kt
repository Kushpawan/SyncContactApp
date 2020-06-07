package com.kushpawan.synccontactapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.testassignment.NewsApp.model.AppDatabase
import com.kushpawan.synccontactapp.database.ContactWorkerClass
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var workManager: WorkManager

    lateinit var contactRecyclerAdapter: ContactRecyclerAdapter
    private var contactList: ArrayList<ContactData>? = null

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = AppDatabase(this)

        val refreshWork = OneTimeWorkRequest.Builder(ContactWorkerClass::class.java)
            .build()
        workManager = WorkManager.getInstance()
        workManager.enqueue(refreshWork)

        getContact()

    }

    private fun setupRecyclerView() {
        contactRecyclerAdapter = contactList?.let { ContactRecyclerAdapter(it) }!!
        contact_recycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        contact_recycler.adapter = contactRecyclerAdapter
    }

    private fun addNewContact() {

    }

    private fun getContact() {
        db.todoDao().getAllContacts().observe(this@MainActivity, Observer {
            contactList = it as ArrayList<ContactData>?
            setupRecyclerView()
        })
    }

    private fun saveContact(contactData: ContactData) {
        GlobalScope.launch {
            db.todoDao().insertContact(contactData)
        }
    }

}
