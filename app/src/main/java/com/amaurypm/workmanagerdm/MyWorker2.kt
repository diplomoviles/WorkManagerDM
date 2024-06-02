package com.amaurypm.workmanagerdm

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay
import kotlin.math.pow

class MyWorker2(
    private val context: Context,
    private val workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val cubo = cubo(4.0)
        return Result.success()
    }

    private suspend fun cubo(num: Double): Double{
        delay(3000)
        return num.pow(3.0)
    }

}