package com.kushpawan.synccontactapp

import android.content.Context
import android.provider.ContactsContract
import com.example.testassignment.NewsApp.model.AppDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

open class ContactClass {
    private lateinit var db: AppDatabase

    fun getContacts(ctx: Context) {
        db = AppDatabase(ctx)

        val list: MutableList<ContactData> = ArrayList()
        val contentResolver = ctx.contentResolver
        val cursor =
            contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)

        if (cursor!!.count > 0) {
            while (cursor.moveToNext()) {
                val id =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    val cursorInfo = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        null
                    )
                    while (cursorInfo!!.moveToNext()) {
                        val info = ContactData()
                        info.id = id
                        info.name =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                        info.mobileNumber =
                            cursorInfo.getString(cursorInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        list.add(info)
                    }
                    cursorInfo.close()
                }
            }
            cursor.close()
        }
        saveList(list)
    }

    private fun saveList(list: MutableList<ContactData>) {
        GlobalScope.launch {
            db.todoDao().insertContacts(list)
        }
    }
}