package com.example.capstoneproject

class UnsplashPhoto(val name: String) {

//    lateinit var mediumImageUrl: String? = null // Medium-quality image
//    lateinit var highImageUrl: String? = null
    lateinit var authorName: String

    companion object {
        private var lastPhotoId = 0
        fun createPhotoList(numPhotos: Int): ArrayList<UnsplashPhoto> {
            val photos = ArrayList<UnsplashPhoto>()
            for (i in 1..numPhotos) {
                photos.add(UnsplashPhoto("Photo " + ++lastPhotoId))
            }
            return photos
        }
    }
}