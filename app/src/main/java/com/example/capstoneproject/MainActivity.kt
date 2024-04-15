package com.example.capstoneproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Contacts.Photo
import android.util.Log
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers


val apiKey = "qaR1WZE4YjHu7HZApkiqIvQBysmrhANCbVrxLtHf2nM"
val URL = "https://api.unsplash.com/photos/?page=1&per_page=50&client_id=qaR1WZE4YjHu7HZApkiqIvQBysmrhANCbVrxLtHf2nM"


class MainActivity : AppCompatActivity() {

    var photoList = arrayListOf<PhotoData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getData()
    }


        private fun getData(){
            val client = AsyncHttpClient()
            client["https://api.unsplash.com/photos/?page=1&per_page=30&client_id=${apiKey}", object : JsonHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                    val jsonArray = json.jsonArray
                    for(i in 0..jsonArray.length()-1){


                        val jsonObj = jsonArray.getJSONObject(i)
                        val photo = PhotoData(

                            jsonObj.getString("id"),
                            jsonObj.getJSONObject("urls").getString("regular"),
                            jsonObj.getString("description"),
                            jsonObj.getJSONObject("user").getString("name"),
                            jsonObj.getJSONObject("links").getString("html"),
                            jsonObj.getJSONObject("links").getString("download"),
                        )
                        Log.d("Photos", "response successful$photo")


//                        photoList.add(photo)


                    }


                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    errorResponse: String,
                    throwable: Throwable?
                ) {
                    Log.d("Photos Error", errorResponse)
                }
            }]


        }


}