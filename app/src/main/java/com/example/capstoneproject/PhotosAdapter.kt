package com.example.capstoneproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PhotosAdapter(private val mPhotos: List<PhotoData>): RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val authorTextView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val photoView = inflater.inflate(R.layout.item_photo, parent, false)
        return ViewHolder(photoView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = mPhotos[position]

        // Load low-quality image first
        Glide.with(holder.itemView.context)
            .load(photo.lowQualityURL)
            .into(holder.imageView)

        // Load high-quality image in the background
        Glide.with(holder.itemView.context)
            .load(photo.highQualityURL)
            .into(holder.imageView)

        holder.authorTextView.text = photo.photographer
    }

    override fun getItemCount(): Int {
        return mPhotos.size
    }
}