package com.dz.workmanagerexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editText = findViewById<EditText>(R.id.editNumber)
        findViewById<Button>(R.id.fetchData)
            .setOnClickListener {
                val number  = Integer.parseInt(editText.text.toString())

                val dataInput = Data.Builder()
                    .putInt("number",number)
                    .build()

                val constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()

                val oneTimeWorkRequest = OneTimeWorkRequestBuilder<ExampleWorker>()
                    .setInputData(dataInput)
                    .addTag("network")
                    .setConstraints(constraints)
                    .build()

                WorkManager.getInstance(this).enqueue(oneTimeWorkRequest)


                // TODO : We can the status of our request by either ID or Tag
                WorkManager.getInstance(this)
                    .getWorkInfosByTagLiveData("network")
                    .observe(this, Observer {
                         for(workInfo in it){
                             Log.d("TAG","Code Status ${workInfo.state}")
                        }
                    })

                // TODO : With Id
                WorkManager.getInstance(this)
                    .getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
                    .observe(this, Observer {
                        Log.d("TAG","Code Status ${it.state}")
                    })



//                // TODO : Periodic Request
//
//                val inputData = Data.Builder()
//                    .putInt("number",number)
//                    .build()
//
//                val periodicRequestConstraints  = Constraints.Builder()
//                    .setRequiredNetworkType(NetworkType.CONNECTED)
//                    .build()
//
//                val periodicWorkRequest = PeriodicWorkRequestBuilder<ExampleWorker>(
//                    15,TimeUnit.MINUTES)
//                    .setInputData(inputData)
//                    .setConstraints(periodicRequestConstraints)
//                    .addTag("periodic")
//                    .build()
//
//
//                WorkManager.getInstance(this).enqueue(periodicWorkRequest)
//
//
//                // TODO : By Id
//                WorkManager.getInstance(this).getWorkInfoByIdLiveData(
//                    periodicWorkRequest.id).observe(this, Observer {
//                        Log.d("TAG","Periodic Status ${it.state}")
//                })
//
//                // TODO : By Tag
//                WorkManager.getInstance(this).getWorkInfosByTagLiveData("periodic")
//                    .observe(this, Observer {
//                        for(work in it){
//                            Log.d("TAG","Periodic Status ${work.state}")
//                        }
//                    })
//

                // TODO : Chaining Requests

                val oneTimeWorkRequest1 = OneTimeWorkRequestBuilder<ExampleWorker>()
                    .setInputData(dataInput)
                    .addTag("network")
                    .setConstraints(constraints)
                    .build()

                val oneTimeWorkRequest2 = OneTimeWorkRequestBuilder<ExampleWorker>()
                    .setInputData(dataInput)
                    .addTag("network")
                    .setConstraints(constraints)
                    .build()


                WorkManager.getInstance(this)
                    .beginWith(oneTimeWorkRequest)
                    .then(oneTimeWorkRequest1)
                    .then(oneTimeWorkRequest2)
                    .enqueue().state.observe(this, Observer {

                    })

            }
    }
}