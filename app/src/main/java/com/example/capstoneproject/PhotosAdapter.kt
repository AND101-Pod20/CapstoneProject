package com.example.capstoneproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PhotosAdapter(private val mPhotos: List<UnsplashPhoto>): RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // TODO - Create references to widgets in a RecyclerView Item

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rvItemId: Int? = null
        val context = parent.context
        val inflater = LayoutInflater.from(context)
//        val photoView = inflater.inflate(R.layout.item_track, parent, false)
        var photoView = rvItemId?.let { inflater.inflate(it, parent, false) };
        return photoView?.let { ViewHolder(it) }!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // TODO - Create references from the ViewHolder widget references to the cell layout
        val photo: UnsplashPhoto = mPhotos[position]

        // TODO - Bind the widget's data to the Photo data

        // TODO - Bind the ImageView with the UnsplashPhoto image url

    }

    override fun getItemCount(): Int {
        return mPhotos.size
    }
}