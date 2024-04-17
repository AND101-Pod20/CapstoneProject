package com.example.capstoneproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

//class PhotosAdapter(private val mPhotos: List<UnsplashPhoto>): RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {
class PhotosAdapter(private val mPhotos: List<PhotoData>): RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        val authorTextView = itemView.findViewById<TextView>(R.id.textView)
        var fetchButton = itemView.findViewById<Button>(R.id.button2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rvItemId: Int? = null
        val context = parent.context
        val inflater = LayoutInflater.from(context)
//        val photoView = inflater.inflate(R.layout.item_track, parent, false)
//        var photoView = rvItemId?.let { inflater.inflate(it, parent, false) };
//        return photoView?.let { ViewHolder(it) }!!

        val photoView = inflater.inflate(R.layout.item_photo, parent, false)
        return ViewHolder(photoView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // TODO - Create references from the ViewHolder widget references to the cell layout
//        val photo: UnsplashPhoto = mPhotos[position]
        val photo: PhotoData = mPhotos[position]
        val imageView = holder.imageView
        val authorView = holder.authorTextView
        val fetchButton = holder.fetchButton

        // TODO - Bind the widget's data to the Photo data
        authorView.text = photo.photographer

        // TODO - Bind the ImageView with the UnsplashPhoto image url
        Glide.with(holder.itemView)
            .load(photo.URL)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return mPhotos.size
    }
}