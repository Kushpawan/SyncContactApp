package com.kushpawan.synccontactapp.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.testassignment.NewsApp.model.AppDatabase
import com.kushpawan.synccontactapp.ContactRecyclerAdapter
import com.kushpawan.synccontactapp.R
import com.kushpawan.synccontactapp.database.ContactData
import com.kushpawan.synccontactapp.sync.ContactWorkerClass
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val TAG = "MainactivityClass"
    private val RECORD_REQUEST_CODE = 101

    lateinit var contactRecyclerAdapter: ContactRecyclerAdapter
    private lateinit var contactList: ArrayList<ContactData>
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = AppDatabase(this)

        setupPermissions()
    }

    private fun initWorkMangerForContacts() {
        showProgressDialog("Syncing Contacts..")
        // this is only one time work request, we can schedule it how we wanted..
        val refreshWork = OneTimeWorkRequestBuilder<ContactWorkerClass>()
            .build()
        WorkManager.getInstance().enqueue(refreshWork)
        WorkManager.getInstance().getWorkInfoByIdLiveData(refreshWork.id)
            .observe(this, Observer { workInfo ->
                if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                    getAllContact()
                    hideProgressDialog()
                }
            })
    }

    // recyclerview for showing the list of contacts
    private fun setupRecyclerView() {
        contactRecyclerAdapter = ContactRecyclerAdapter(contactList)
        contact_recycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        contact_recycler.itemAnimator = DefaultItemAnimator()
        contact_recycler.adapter = contactRecyclerAdapter
    }

    // get all contacts from local room db .. all queries can define in 'ToDoDao' class
    private fun getAllContact() {
        db.todoDao().getAllContacts().observe(this@MainActivity, Observer { listOfContacts ->
            listOfContacts?.let {
                contactList = it as ArrayList<ContactData>
                setupRecyclerView()
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RECORD_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Permission has been denied by user")
                    setupPermissions()
                } else {
                    Log.i(TAG, "Permission has been granted by user")
                    initWorkMangerForContacts()
                }
            }
        }
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CONTACTS
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                RECORD_REQUEST_CODE
            )
        }
    }

}
