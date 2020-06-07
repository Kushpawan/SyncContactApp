package com.kushpawan.synccontactapp

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "contact_data")
@Parcelize
data class ContactData(
    var id: String? = null,
    var name: String? = null,
    var mobileNumber: String? = null,
    var email: String? = null
) : Parcelable