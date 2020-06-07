package com.kushpawan.synccontactapp

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "contact_data")
@Parcelize
data class ContactData(
    @PrimaryKey(autoGenerate = true)
    var idp: Int? = null,
    var id: String? = null,
    var name: String? = null,
    var mobileNumber: String? = null,
    var email: String? = null
) : Parcelable