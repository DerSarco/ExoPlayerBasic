package com.globant.carlosmunoz.exoplayerbasic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.globant.carlosmunoz.exoplayerbasic.databinding.ActivityMainBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector

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

    /**
     * I declared this globally just for achieve more scoope in the class
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
        exoPlayer.play()
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
    override fun onIsPlayingChanged(isPlaying: Boolean) {
        when (isPlaying) {
            true -> Toast.makeText(this, "Playing!", Toast.LENGTH_SHORT).show()
            false -> Toast.makeText(this, "Not Playing", Toast.LENGTH_SHORT).show()
        }
    }
}
