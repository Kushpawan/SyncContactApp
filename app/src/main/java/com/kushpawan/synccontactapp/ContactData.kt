package com.kushpawan.synccontactapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ContactData(
    var name: String? = null,
    var number: String? = null,
    var email: String? = null
) : Parcelable