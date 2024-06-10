package com.amaurypm.workmanagerdm

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.amaurypm.workmanagerdm.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val decimalFormat = DecimalFormat("###,###,###.00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.tvWork1Status.movementMethod = ScrollingMovementMethod()
        binding.tvWork2Status.movementMethod = ScrollingMovementMethod()

        val data = workDataOf(
            "num1" to 25.3,
            "num2" to 32.4
        )

        val workRequest1 = OneTimeWorkRequestBuilder<MyWorker1>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(
                        //NetworkType.METERED //La red del operador móvil
                        //NetworkType.UNMETERED //Red WiFi
                        NetworkType.CONNECTED
                    )
                    .setRequiresBatteryNotLow(
                        true
                    )
                    .build()
            )
            .setInputData(data)
            .build()



        val workManager = WorkManager.getInstance(applicationContext)

        binding.ivStartWork.setOnClickListener {
            workManager.beginUniqueWork(
                "mywork1",
                ExistingWorkPolicy.KEEP,
                workRequest1
            )
                //.then(workRequest2)
                .enqueue()

            /*workManager.beginUniqueWork(
                "mywork2",
                ExistingWorkPolicy.KEEP,
                workRequest2
            )
                .enqueue()*/
        }

        workManager.getWorkInfoByIdLiveData(workRequest1.id)
            .observe(this){ work1Info ->

                when(work1Info.state){
                    WorkInfo.State.SUCCEEDED -> {
                        Log.d("MyWorker1", "Trabajo completado con éxito")
                    }
                    WorkInfo.State.FAILED -> {
                        Log.d("MyWorker1", "Trabajo fallido")
                    }
                    WorkInfo.State.ENQUEUED -> {
                        Log.d("MyWorker1", "Trabajo encolado")
                    }
                    //Otras opciones
                    else -> {

                    }
                }

                if(work1Info!=null){
                    val workStatus = work1Info.state.name
                    binding.tvWork1Status.append("$workStatus\n")
                    if(work1Info.state.isFinished){
                        //binding.tvWork1Status.append("\n")
                        var result1 = work1Info.outputData.getDouble("suma", 0.0)
                        binding.tvWork1Status.append("Suma = \$${decimalFormat.format(result1)}\n\n")

                        val workRequest2 = OneTimeWorkRequestBuilder<MyWorker2>()
                            .setConstraints(
                                Constraints.Builder()
                                    .setRequiredNetworkType(
                                        NetworkType.CONNECTED
                                    )
                                    .setRequiresBatteryNotLow(
                                        true
                                    )
                                    .build()
                            )
                            .setInputData(
                                workDataOf(
                                    "num" to result1
                                )
                            )
                            .build()

                        //Mandamos ejecutar el workrequest2 con los datos del workrequest1
                        workManager.beginUniqueWork(
                            "mywork2",
                            ExistingWorkPolicy.KEEP,
                            workRequest2
                        )
                            .enqueue()

                        workManager.getWorkInfoByIdLiveData(workRequest2.id)
                            .observe(this){ work2Info ->
                                if(work2Info!=null){
                                    val workStatus = work2Info.state.name
                                    binding.tvWork2Status.append("$workStatus\n")
                                    if(work2Info.state.isFinished){
                                        //binding.tvWork2Status.append("\n")
                                        val result2 = work2Info.outputData.getDouble("cubo", 0.0)
                                        binding.tvWork2Status.append("Cubo = \$${decimalFormat.format(result2)}\n\n")
                                    }
                                }
                            }

                    }
                }
            }



    }
}