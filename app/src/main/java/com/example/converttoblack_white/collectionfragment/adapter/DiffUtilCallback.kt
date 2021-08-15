package com.example.converttoblack_white.collectionfragment.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.converttoblack_white.photo.Photo

class DiffUtilCallback
    : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(
        oldItem: Photo,
        newItem: Photo
    ): Boolean {
        return oldItem.photoId == newItem.photoId
    }

    override fun areContentsTheSame(
        oldItem: Photo,
        newItem: Photo
    ): Boolean {
        return oldItem == newItem
    }
}