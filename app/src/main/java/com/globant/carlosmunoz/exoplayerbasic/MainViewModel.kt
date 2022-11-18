package com.globant.carlosmunoz.exoplayerbasic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private var isFullscreen: MutableLiveData<Boolean> = MutableLiveData(false)
    val fullScreenStatus: Boolean
        get() = isFullscreen.value!!

    fun setIsFullscreen(isFullscreen: Boolean) {
        this.isFullscreen.postValue(isFullscreen)
    }

}