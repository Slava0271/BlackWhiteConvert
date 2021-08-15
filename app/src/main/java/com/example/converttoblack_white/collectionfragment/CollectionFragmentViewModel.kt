package com.example.converttoblack_white.collectionfragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.converttoblack_white.FilePath
import com.example.converttoblack_white.lifecycle.SingleLiveEvent
import com.example.converttoblack_white.photo.IdCounter
import com.example.converttoblack_white.photo.Photo
import java.io.File

class CollectionFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val _users = MutableLiveData<List<Photo>>()
    val users: LiveData<List<Photo>> = _users

    private val _navigateBack = SingleLiveEvent<Boolean>()
    val navigateBack: LiveData<Boolean> = _navigateBack

    private val _addClickEvent = SingleLiveEvent<Boolean>()
    val addClickEvent: LiveData<Boolean> = _addClickEvent

    val userList = mutableListOf<Photo>()

    init {
        showFolderContent()
        _users.postValue(userList)
    }

    fun onBackClick() {
        _navigateBack.value = true
    }

    fun onAddClick(){
        _addClickEvent.value = true
    }

    private fun showFolderContent() {
        val directory: File = File(FilePath.getRepositoryPath())
        val files = directory.listFiles()

        for (i in files.indices) {
            Log.d("fileSize","${files[i].length()}")
            if(files[i].length()>0)
            userList.add(createPhoto(files[i].absolutePath))
        }

        Log.d("showFiles", "${files.map { it.name }}")
    }

    private fun createPhoto(url: String): Photo {
        val photoId = IdCounter.getPhotoId()
        IdCounter.incrementPhotoId()

        return Photo(photoId, url)
    }
}