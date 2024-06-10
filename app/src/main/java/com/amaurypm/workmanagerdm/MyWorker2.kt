package com.amaurypm.workmanagerdm

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.delay
import kotlin.math.pow

class MyWorker2(
    private val context: Context,
    private val workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        //val cubo = cubo(4.0)

        val data = inputData

        val cubo = cubo(data.getDouble("num", 0.0))

        return Result.success(
            workDataOf(
                "cubo" to cubo
            )
        )
    }

    private suspend fun cubo(num: Double): Double{
        delay(3000)
        return num.pow(3.0)
    }

}