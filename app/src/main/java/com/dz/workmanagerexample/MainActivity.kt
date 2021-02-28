package com.dz.workmanagerexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.work.*

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

                WorkManager.getInstance(this).enqueue(oneTimeWorkRequest.build())


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
                    .getWorkInfoByIdLiveData(oneTimeWorkRequest.build().id)
                    .observe(this, Observer {
                        Log.d("TAG","Code Status ${it.state}")
                    })

            }
    }
}