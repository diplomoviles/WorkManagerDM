package com.amaurypm.workmanagerdm

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.delay

class MyWorker1(
    private val context: Context,
    private val workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {

        //val suma = suma(5.0, 3.0)

        val data = inputData

        val suma = suma(
            data.getDouble("num1", 0.0),
            data.getDouble("num2", 0.0)
        )

        return Result.success(
            workDataOf(
                "suma" to suma
            )
        )

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