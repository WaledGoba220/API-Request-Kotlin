package com.example.myapitest

import android.app.DownloadManager.Request
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import com.example.myapitest.databinding.ActivityMainBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL

class MainActivity : AppCompatActivity() {
    val client = OkHttpClient()
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFetch.setOnClickListener{
            //makeRequest()
            makeRequestUsingOkHTTP()
        }
    }


    private  fun makeRequestUsingOkHTTP(){
        val request = okhttp3.Request.Builder().url("https://v2.jokeapi.dev/joke/Any").build()
        client.newCall(request).enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "${e.message}: ")
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread{
                    binding.textResult.text = response.body?.string()
                }

            }

        })
    }



    private fun makeRequest(){
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val url = URL("https://v2.jokeapi.dev/joke/Any")
        val connection = url.openConnection()
        val inputStream = connection.getInputStream()
        val inputStreamReader = InputStreamReader(inputStream)
        val result = inputStreamReader.readText()
        binding.textResult.text = result
    }


    companion object{
        private const val TAG = "MAIN_ACTIVITY"
    }


}