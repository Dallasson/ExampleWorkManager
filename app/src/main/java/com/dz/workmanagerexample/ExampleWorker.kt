package com.dz.workmanagerexample

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters

class ExampleWorker (var context: Context,
   workerParameters: WorkerParameters): CoroutineWorker(context,workerParameters) {

    override suspend fun doWork(): Result {
         return Result.success()
    }

}