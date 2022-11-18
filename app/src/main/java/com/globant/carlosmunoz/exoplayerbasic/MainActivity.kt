package com.globant.carlosmunoz.exoplayerbasic

import android.content.pm.ActivityInfo
import android.opengl.Visibility
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.globant.carlosmunoz.exoplayerbasic.databinding.ActivityMainBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

/**
 * If you want to add more functionality to your player, extend to Player.Listener interface
 */
class MainActivity : AppCompatActivity() {


    /**
     *A companion object, but is not necessary, you could retrieve a list from somewhere and directly add
     * to exoPlayer Media List
     * */
    companion object {
        const val VIDEO_URL =
            "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
    }

    //TODO: Fix on landscape changed video keep playing

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    /**
     * I declared this globally just for achieve more scope in the class
     */
    private lateinit var exoPlayer: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setupUI()
        setContentView(binding.root)

    }


    override fun onPostResume() {
        super.onPostResume()
        fullScreenConfig()
    }

    private fun setupUI() {
        //This is declared on the MainActivity XML, is a very simple XML tag, go check it.
        val pvPlayer = binding.pvMedia
        setupExoPlayer()
        pvPlayer.apply {
            player = exoPlayer
        }

        binding.imbShowFullscreen.visibility = viewModel.getShowFullScreenButton()
        binding.imbExitFullscreen.visibility = viewModel.getShowMinimizeButton()
        binding.imbShowFullscreen.setOnClickListener {
            fullScreen()
        }

        binding.imbExitFullscreen.setOnClickListener {
            showSystemUI()
        }
    }

    /**
     * Is mandatory to build a exoPlayer instance, and pass all this configuration to watch a video
     * a MediaItem with their respective URL with a video on it
     */
    private fun setupExoPlayer() {
        val mediaItem = MediaItem.fromUri(VIDEO_URL)
        exoPlayer = ExoPlayer.Builder(this)
            .build()
        exoPlayer.apply {
            setMediaItem(mediaItem)
            prepare()
        }
    }

    private fun fullScreen() {
        viewModel.setIsFullscreen(true)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    private fun showSystemUI() {
        viewModel.setIsFullscreen(false)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    private fun fullScreenConfig() {
        if (viewModel.getIsFullScreen()) {
            WindowInsetsControllerCompat(window, binding.activityMain).let { controller ->
                controller.hide(WindowInsetsCompat.Type.systemBars())
                controller.systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            WindowInsetsControllerCompat(
                window,
                binding.activityMain
            ).show(WindowInsetsCompat.Type.systemBars())
        }
    }
}
