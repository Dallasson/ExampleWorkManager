package com.dz.workmanagerexample

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.dz.workmanagerexample.models.JsonHolderModel
import com.dz.workmanagerexample.webauth.RetrofitSingleton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExampleWorker (var context: Context,
   workerParameters: WorkerParameters): CoroutineWorker(context,workerParameters) {

    @SuppressLint("RestrictedApi")
    override suspend fun doWork(): Result {
        return try {
            val data = inputData
            val number = data.getInt("number",1)

            RetrofitSingleton.getRetrofitInstance()
                    .getPost(number)
                    .enqueue(object : Callback<JsonHolderModel>{
                        override fun onResponse(call: Call<JsonHolderModel>, response: Response<JsonHolderModel>) {
                            if(response.isSuccessful){
                                Toast.makeText(context,"Item Value is ${response.body()}",
                                        Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<JsonHolderModel>, t: Throwable) {
                            //something is wrong
                        }

                    })

            return Result.success()
        }catch (ex : Exception){
            return Result.Failure()
        }
    }

}