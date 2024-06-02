package com.amaurypm.workmanagerdm

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class MyWorker1(
    private val context: Context,
    private val workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val suma = suma(5.0, 3.0)
        return Result.success()

        /*return if(suma>=100000)
            Result.failure()
        else
            Result.retry()*/

    }

    private suspend fun suma(num1: Double, num2: Double): Double{
        delay(5000)
        return num1 + num2
    }

}