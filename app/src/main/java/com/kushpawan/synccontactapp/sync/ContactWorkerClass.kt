package com.kushpawan.synccontactapp.sync

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class ContactWorkerClass(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        // perform long running operation
        ContactSyncClass().getContacts(applicationContext)

        return Result.success()
    }

}