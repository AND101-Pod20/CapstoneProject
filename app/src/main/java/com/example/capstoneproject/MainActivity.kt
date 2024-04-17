package com.example.capstoneproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import android.provider.ContactsContract.Contacts.Photo
import android.util.Log
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
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
    private var loading = true
    private var currentPage = 1
    private val visibleThreshold = 5

    var photoList = arrayListOf<PhotoData>()
    companion object {
        private const val BASE_URL = "https://api.unsplash.com/photos/"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        photos = arrayListOf()
        setupLayout()
        setupRecyclerView()
        getData()
    }

    private fun setupLayout() {
        photoRV = findViewById(R.id.recyclerView)
        fetchButton = findViewById(R.id.button)
        promptTextField = findViewById(R.id.textInputLayout)
        fetchButton.setOnClickListener{
            photos.clear()
            getCustomData(promptTextField.editText?.text.toString())
        }
    }

    private fun setupRecyclerView() {
        adapter = PhotosAdapter(photos)
        photoRV.adapter = adapter
        photoRV.layoutManager = LinearLayoutManager(this)

        photoRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                val lastVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    currentPage++
                    getData()
                    loading = true
                }
            }
        })
    }


    private fun getData(){
        val client = AsyncHttpClient()
        val url = "$BASE_URL?page=$currentPage&per_page=30&client_id=$apiKey"
        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                val jsonArray = json.jsonArray
                for (i in 0 until jsonArray.length()) {
                    val jsonObj = jsonArray.getJSONObject(i)
                    val photo = PhotoData(
                        jsonObj.getString("id"),
                        jsonObj.getJSONObject("urls").getString("regular"),
                        jsonObj.optString("description") ?: "No Description",
                        jsonObj.getJSONObject("user").getString("name"),
                        jsonObj.getJSONObject("links").getString("html"),
                        jsonObj.getJSONObject("links").getString("download")
                    )
                    photos.add(photo)
                }
                adapter.notifyItemRangeInserted(adapter.itemCount, photos.size - 1)
                loading = false
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.e("Photos Error", errorResponse)
                loading = false
            }
        })
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