package com.kushpawan.synccontactapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testassignment.NewsApp.model.AppDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var contactRecyclerAdapter: ContactRecyclerAdapter
    private var contactList: ArrayList<ContactData>? = null

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = AppDatabase(this)

        setupRecyclerView()
        add_button.setOnClickListener {
            addNewContact()
        }
    }

    private fun setupRecyclerView() {
        contactRecyclerAdapter = contactList?.let { ContactRecyclerAdapter(it) }!!
        contact_recycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        contact_recycler.adapter = contactRecyclerAdapter
    }

    private fun addNewContact() {

    }

    private fun saveContact(contactData: ContactData) {
        GlobalScope.launch {
            db.todoDao().insertContact(contactData)
        }
    }

}
