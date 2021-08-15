package com.example.converttoblack_white.collectionfragment.adapter

import android.graphics.BitmapFactory
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.converttoblack_white.R
import com.example.converttoblack_white.databinding.ItemPhotoBinding
import com.example.converttoblack_white.photo.Photo
import java.io.File

class PhotoViewHolder(private val binding: ItemPhotoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Photo, listener: ((Photo) -> Unit)?) {
        binding.photo = item
        binding.userContainer.setOnClickListener { listener?.invoke(item) }
        Log.d("showUrl", item.photoUri)
        setPic(binding.imageView,item.photoUri)
    }

    private fun setPic(imageView:AppCompatImageView,filePath: String) {

        // Get the dimensions of the View
        val targetW: Int = 100
        val targetH: Int = 100



        val bmOptions = BitmapFactory.Options().apply {
            // Get the dimensions of the bitmap
            inJustDecodeBounds = true

            BitmapFactory.decodeFile(filePath)

            val photoW: Int = outWidth
            val photoH: Int = outHeight



            // Determine how much to scale down the image
            val scaleFactor: Int = Math.max(1, Math.min(photoW / targetW, photoH / targetH))

            // Decode the image file into a Bitmap sized to fill the View
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
            inPurgeable = true
        }
        BitmapFactory.decodeFile(filePath, bmOptions)?.also { bitmap ->
            imageView.setImageBitmap(bitmap)
        }
    }

}