package com.globant.carlosmunoz.exoplayerbasic

import android.view.View
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private var isFullscreen = false
    private var showFullScreenButton = true

    fun setIsFullscreen(isFullscreen: Boolean) {
        this.isFullscreen = isFullscreen
        this.showFullScreenButton = !isFullscreen
    }

    fun getIsFullScreen() = isFullscreen

    fun getShowFullScreenButton(): Int {
        return if (showFullScreenButton) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    fun getShowMinimizeButton(): Int {
        return if (showFullScreenButton) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

}