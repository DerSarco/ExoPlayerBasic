package com.globant.carlosmunoz.exoplayerbasic

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.globant.carlosmunoz.exoplayerbasic.databinding.ActivityMainBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

/**
 * If you want to add more functionality to your player, extend to Player.Listener interface
 */
class MainActivity : AppCompatActivity(), Player.Listener {


    /**
     *A companion object, but is not necessary, you could retrieve a list from somewhere and directly add
     * to exoPlayer Media List
     * */
    companion object {
        const val VIDEO_URL =
            "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
    }

    private lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel()

    /**
     * I declared this globally just for achieve more scope in the class
     */
    private lateinit var exoPlayer: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setupUI()
        setContentView(binding.root)
    }

    private fun setupUI() {
        //This is declared on the MainActivity XML, is a very simple XML tag, go check it.
        val pvPlayer = binding.pvMedia
        setupExoPlayer()
        pvPlayer.apply {
            player = exoPlayer
        }
        //TODO: Fix behaviour of fullscreen status
        binding.imbFullscreen.setOnClickListener {
            when (viewModel.fullScreenStatus) {
                true -> showSystemUI()
                false -> fullScreen()
            }
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
            addListener(this@MainActivity)
            prepare()
        }
    }

    /**
     * This is not mandatory, i just checking how the listener works.
     */


    private fun fullScreen() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        WindowInsetsControllerCompat(window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        viewModel.setIsFullscreen(true)

    }

    private fun showSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        WindowInsetsControllerCompat(
            window,
            binding.root
        ).show(WindowInsetsCompat.Type.systemBars())
        viewModel.setIsFullscreen(false)
    }
}
