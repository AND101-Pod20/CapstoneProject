package com.example.capstoneproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import android.util.Log
import androidx.core.content.ContextCompat
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.android.material.textfield.TextInputEditText
import okhttp3.Headers


val apiKey = "qaR1WZE4YjHu7HZApkiqIvQBysmrhANCbVrxLtHf2nM"
val URL = "https://api.unsplash.com/photos/?page=1&per_page=50&client_id=qaR1WZE4YjHu7HZApkiqIvQBysmrhANCbVrxLtHf2nM"


class MainActivity : AppCompatActivity() {

    private lateinit var photos: ArrayList<PhotoData>
//    private lateinit var photos: ArrayList<UnsplashPhoto>
    private lateinit var photoRV: RecyclerView
    private lateinit var fetchButton: Button
    private lateinit var promptTextField: TextInputLayout
    private lateinit var adapter: PhotosAdapter


    var photoList = arrayListOf<PhotoData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setAppTheme();
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        photos = arrayListOf()

        setupLayout()
        getData()
        fetchButton = findViewById(R.id.button)
        fetchButton.setOnClickListener{
            getCustomData(promptTextField.editText?.text.toString())
        }


        val themeButton: Button = findViewById(R.id.button3)
        themeButton.setOnClickListener {
            toggleTheme()
        }

        setupRecyclerView()
    }
    companion object {
        var isDarkMode = false
    }

    private fun setAppTheme() {
        setTheme(if (isDarkMode) R.style.AppTheme_Dark else R.style.AppTheme_Light)
//        val textView: TextInputEditText = findViewById(R.id.input1)
//        textView.setTextColor(ContextCompat.getColor(this, if (isDarkMode) R.color.darkTextColor else R.color.lightTextColor))
    }
    private fun toggleTheme() {
        isDarkMode = !isDarkMode
        recreate() // Recreate the activity to apply the new theme
    }


    private fun setupLayout() {
        photoRV = findViewById(R.id.recyclerView)
        fetchButton = findViewById(R.id.button)
        promptTextField = findViewById(R.id.textInputLayout)

    }

    private fun setupRecyclerView() {
        this.adapter = PhotosAdapter(photos)
//        this.adapter = Pho
//        this.adapter = PhotosAdapter(photos)
        photoRV.adapter = this.adapter
        photoRV.layoutManager = LinearLayoutManager(this@MainActivity)

        // TODO - (opt.) Add divider between RecyclerView items
//        val dividerItemDecoration = DividerItemDecoration(rvTracks.context, LinearLayoutManager.VERTICAL)
//        rvTracks.addItemDecoration(dividerItemDecoration)
    }


    private fun getData(){
        val client = AsyncHttpClient()
        client["https://api.unsplash.com/photos/?page=1&per_page=30&client_id=${apiKey}", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                val jsonArray = json.jsonArray
                for(i in 0..<jsonArray.length()) {


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

    private fun getCustomData(text: String) {
        val client = AsyncHttpClient()
        client["https://api.unsplash.com/search/photos?page=1&query=${text}&client_id=${apiKey}", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                val customJson = json.jsonObject.getJSONArray("results")

                if (photos.isNotEmpty()) {
                    photos.clear()
                }


//                Log.d("DOG", "response$customJson")
                for(i in 0..<customJson.length()-1){
                    val jsonObj = customJson.getJSONObject(i)
                    val customPhoto = PhotoData(

                        jsonObj.getString("id"),
                        jsonObj.getJSONObject("urls").getString("regular"),
                        jsonObj.getString("description"),
                        jsonObj.getJSONObject("user").getString("name"),
                        jsonObj.getJSONObject("links").getString("html"),
                        jsonObj.getJSONObject("links").getString("download"),
                    )

                    photos.add(customPhoto)
                    this@MainActivity.adapter.notifyDataSetChanged()

//                    Log.d("DOG", "response successful$customPhoto")
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