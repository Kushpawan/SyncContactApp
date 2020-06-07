package com.kushpawan.synccontactapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kushpawan.synccontactapp.database.ContactData

class MainViewModel : ViewModel() {

    var contactLiveData = MutableLiveData<ContactData>()

    fun getAllContact() {

    }

}