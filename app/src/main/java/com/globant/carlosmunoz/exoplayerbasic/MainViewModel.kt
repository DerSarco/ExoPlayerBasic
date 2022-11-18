package com.globant.carlosmunoz.exoplayerbasic

import android.view.View
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private var isFullscreen = false
    private var showFullScreenButton = true
    private var currentTime: Long = 0
    private var currentWindow: Int = 0
    private var isPlaying = false

    fun setIsFullscreen(isFullscreen: Boolean) {
        this.isFullscreen = isFullscreen
        this.showFullScreenButton = !isFullscreen
    }

    fun setCurrentTimeLine(currentTime: Long, currentWindowIndex: Int) {
        this.currentTime = currentTime
        this.currentWindow = currentWindowIndex
    }

    fun getCurrentTime() = this.currentTime
    fun getCurrentWindow() = this.currentWindow
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

    fun getIsPlaying() = this.isPlaying

    fun setIsPlaying(playing: Boolean) {
        this.isPlaying = playing
    }
}