package com.kushpawan.synccontactapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testassignment.NewsApp.model.AppDatabase

class MainViewModel : ViewModel() {

    var contactLiveData = MutableLiveData<ContactData>()

    fun getAllContact() {

    }

}