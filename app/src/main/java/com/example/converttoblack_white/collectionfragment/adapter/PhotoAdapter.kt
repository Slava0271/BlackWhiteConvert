package com.example.converttoblack_white.collectionfragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.converttoblack_white.databinding.ItemPhotoBinding
import com.example.converttoblack_white.photo.Photo

class PhotoAdapter(private val listener: ((Photo) -> Unit)? = null) :
    ListAdapter<Photo, PhotoViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding =
            ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

}