package com.example.converttoblack_white.convertedimagefragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.converttoblack_white.lifecycle.SingleLiveEvent

class ConvertedImageViewModel(application: Application) : AndroidViewModel(application) {
    private val _convertEvent = SingleLiveEvent<Boolean>()
    val convertEvent: LiveData<Boolean> = _convertEvent

    private val _navigateBack = SingleLiveEvent<Boolean>()
    val navigateBack: LiveData<Boolean> = _navigateBack


    fun onBackClick(){
        _navigateBack.value = true
    }

    fun onConvertClick() {
        _convertEvent.value = true
    }
}