package com.dz.workmanagerexample

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.dz.workmanagerexample.models.JsonHolderModel
import com.dz.workmanagerexample.models.JsonHolderModelItem
import com.dz.workmanagerexample.webauth.RetrofitSingleton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExampleWorker (var context: Context,
   workerParameters: WorkerParameters): CoroutineWorker(context,workerParameters) {

    private lateinit var outPut : Data
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

                                val response = response.body()
                                val array  : Array<String> = arrayOf(response?.toTypedArray().toString())

                                outPut = Data.Builder()
                                    .putStringArray("value", array)
                                    .build()
                            }
                        }

                        override fun onFailure(call: Call<JsonHolderModel>, t: Throwable) {
                            //something is wrong
                        }

                    })

             Result.success(outPut)
        }catch (ex : Exception){
             Result.Failure()
        }
    }

}