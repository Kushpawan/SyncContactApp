package com.kushpawan.synccontactapp.database

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.kushpawan.synccontactapp.ContactClass

class ContactWorkerClass(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        // perform long running operation
        ContactClass().getContacts(applicationContext)

        return Result.success()
    }

}