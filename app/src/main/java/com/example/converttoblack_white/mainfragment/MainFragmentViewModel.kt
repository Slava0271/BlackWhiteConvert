package com.example.converttoblack_white.mainfragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.navigation.NavDirections
import com.example.converttoblack_white.lifecycle.SingleLiveEvent

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val _onClickEvent = SingleLiveEvent<Boolean>()
    val onClickEvent: LiveData<Boolean> = _onClickEvent

    private val _navigate = SingleLiveEvent<NavDirections>()
    val navigate: LiveData<NavDirections> = _navigate

    fun onButtonClick() {
        _onClickEvent.value = true
    }

    fun navigateToCollection(){
        _navigate.postValue(MainFragmentDirections.actionMainFragmentToCollectionFragment())
    }
}