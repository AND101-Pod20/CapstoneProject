package com.example.capstoneproject

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.Executors

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

        var mImage: Bitmap?
        val downloadUrl = photo.downloadLink
        val myExecutor = Executors.newSingleThreadExecutor()
        val myHandler = Handler(Looper.getMainLooper())

        fetchButton.setOnClickListener {
            myExecutor.execute {
                mImage = mLoad(downloadUrl)
                myHandler.post {
                    imageView.setImageBitmap(mImage)
                    if(mImage!=null){
                        mSaveMediaToStorage(mImage)
                    }
                }
            }
        }
    }

    private fun mLoad(string: String): Bitmap? {
        val url: URL = mStringToURL(string)!!
        val connection: HttpURLConnection?
        try {
            connection = url.openConnection() as HttpURLConnection
            connection.connect()
            val inputStream: InputStream = connection.inputStream
            val bufferedInputStream = BufferedInputStream(inputStream)
            return BitmapFactory.decodeStream(bufferedInputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this@PhotosAdapter, "Error", Toast.LENGTH_LONG).show()
        }
        return null
    }

    // Function to convert string to URL
    private fun mStringToURL(string: String): URL? {
        try {
            return URL(string)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return null
    }

    // Function to save image on the device.
    // Refer: https://www.geeksforgeeks.org/circular-crop-an-image-and-save-it-to-the-file-in-android/
    private fun mSaveMediaToStorage(bitmap: Bitmap?) {
        val filename = "${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }
        fos?.use {
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(this , "Saved to Gallery" , Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return mPhotos.size
    }
}