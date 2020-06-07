package com.kushpawan.synccontactapp.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.testassignment.NewsApp.model.AppDatabase
import com.kushpawan.synccontactapp.database.ContactData
import com.kushpawan.synccontactapp.ContactRecyclerAdapter
import com.kushpawan.synccontactapp.R
import com.kushpawan.synccontactapp.sync.ContactWorkerClass
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    private lateinit var workManager: WorkManager

    lateinit var contactRecyclerAdapter: ContactRecyclerAdapter
    private var contactList: ArrayList<ContactData>? = null

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = AppDatabase(this)

        initWorkMangerForContacts()

    }

    private fun initWorkMangerForContacts() {
        showProgressDialog("Syncing Contacts..")
        val refreshWork = OneTimeWorkRequestBuilder<ContactWorkerClass>()
            .build()
        workManager = WorkManager.getInstance()
        workManager.enqueue(refreshWork)
        WorkManager.getInstance().getWorkInfoByIdLiveData(refreshWork.id)
            .observe(this, Observer { workInfo ->
                if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                    getAllContact()
                    hideProgressDialog()
                }
            })
    }

    private fun setupRecyclerView() {
        contactRecyclerAdapter = contactList?.let {
            ContactRecyclerAdapter(
                it
            )
        }!!
        contact_recycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        contact_recycler.adapter = contactRecyclerAdapter
    }

    private fun getAllContact() {
        db.todoDao().getAllContacts().observe(this@MainActivity, Observer {
            contactList = it as ArrayList<ContactData>?
            setupRecyclerView()

        })
    }

}
