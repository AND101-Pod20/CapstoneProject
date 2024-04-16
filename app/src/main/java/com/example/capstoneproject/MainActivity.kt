package com.example.capstoneproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var photos: ArrayList<UnsplashPhoto>
    private lateinit var photoRV: RecyclerView
    private lateinit var fetchButton: Button
    private lateinit var promptTextField: TextInputLayout
    private lateinit var adapter: PhotosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupLayout()
        setupRecyclerView()
    }

    private fun setupLayout() {
        photoRV = findViewById(R.id.recyclerView)
        fetchButton = findViewById(R.id.textInputLayout)
        promptTextField = findViewById(R.id.button)
    }

    private fun setupRecyclerView() {
        this.adapter = PhotosAdapter(photos)
        photoRV.adapter = this.adapter
        photoRV.layoutManager = LinearLayoutManager(this@MainActivity)

        // TODO - (opt.) Add divider between RecyclerView items
//        val dividerItemDecoration = DividerItemDecoration(rvTracks.context, LinearLayoutManager.VERTICAL)
//        rvTracks.addItemDecoration(dividerItemDecoration)
    }
}