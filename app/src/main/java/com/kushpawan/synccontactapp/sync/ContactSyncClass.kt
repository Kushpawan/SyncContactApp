package com.kushpawan.synccontactapp.sync

import android.content.Context
import android.provider.ContactsContract
import android.text.TextUtils
import com.example.testassignment.NewsApp.model.AppDatabase
import com.kushpawan.synccontactapp.database.ContactData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


open class ContactSyncClass {
    private lateinit var db: AppDatabase

    fun getContacts(ctx: Context) {
        db = AppDatabase(ctx)

        try {
            val list: MutableList<ContactData> = ArrayList()
            val contentResolver = ctx.contentResolver
            val cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null
            )

            if (cursor!!.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                    // get the user's phone number
                    var phone: String? = null
                    if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        val phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            arrayOf(id),
                            null
                        )
                        if (phoneCursor != null && phoneCursor.moveToFirst()) {
                            phone =
                                phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            phoneCursor.close()
                        }
                    }

                    // get the user's email address
                    var email: String? = null
                    val emailCursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                        arrayOf(id),
                        null
                    )
                    if (emailCursor != null && emailCursor.moveToFirst()) {
                        email =
                            emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))
                        emailCursor.close()
                    }

                    // if the user user has phone then add it to contacts
                    if (!TextUtils.isEmpty(phone) && isValidNumber(phone)) {
                        val info = ContactData()
                        info.id = id
                        info.name = name
                        email?.let {
                            info.email = it
                        }
                        info.mobileNumber = phone
                        list.add(info)
                    }
                }
                cursor.close()
            }
            saveList(list)
        } catch (ex: Exception) {

        }
    }

    //check for valid mobile number
    private fun isValidNumber(phoneNumber: String?): Boolean {
        return if (phoneNumber.toString().length <= 10) {
            false
        } else {
            android.util.Patterns.PHONE.matcher(phoneNumber.toString()).matches()
        }
    }

    // save synced list to local db using Room & Coroutine Scope
    private fun saveList(list: MutableList<ContactData>) {
        GlobalScope.launch {
            db.todoDao().clearAndCacheContact(list)
        }
    }
}