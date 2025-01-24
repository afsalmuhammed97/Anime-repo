package com.dev.jikanapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dev.jikanapp.databinding.FragmentDetailBinding
import com.dev.jikanapp.network.Resource
import com.dev.jikanapp.ui.detailPreview.DetailViewModel
import com.dev.jikanapp.ui.homelisting.HomeViewModel
import com.dev.jikanapp.uttil.handleApiError
import com.dev.jikanapp.uttil.loadImage
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Tracks
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class DetailFragment : Fragment() {
    private  var exoPlayer: ExoPlayer?=null
    //   private var _binding: FragmentListingBinding?=null
    private var _binding:FragmentDetailBinding?=null
    val binding get() = _binding!!
    private var animeId: Int? = null
    private var videoUrl: String? = null

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            animeId = it.getInt("anime_id")

            Log.d("TTY","argument $animeId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding=FragmentDetailBinding.inflate(inflater,container,false)
      //  initPlayer()

        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      detailViewModel=  ViewModelProvider(requireActivity())[DetailViewModel::class.java]

        if (animeId!=null){
            detailViewModel.getAnimDetails(animeId!!)
        }

        detailViewModel. animeData.observe(viewLifecycleOwner) {

            when (it) {

                is Resource.Success -> {
                    binding.progressBar2.visibility = View.GONE
                    val data =it.value.body()!!

                binding.thumbImage.loadImage(data.data.images.jpg.large_image_url)
                    binding.videoView.visibility=View.GONE
                    videoUrl=data.data.trailer?.embed_url
                    Log.d("URL12","url $videoUrl")
                    Log.d("TTT", "data success ${it.value.body()}")

                    binding.tittle.text=data.data.title
                    binding.Genere.text=data.data.genres.toString()
                    binding.mainCast.text=data.data.themes.toString()
                    binding.noEpisode.text="${data.data.episodes}"
                    binding.rating.text=data.data.rating
                    binding.plot.text=data.data.synopsis

                }

                is Resource.Failure -> {

                    binding.progressBar2.visibility = View.VISIBLE
                    handleApiError(it)
                }

            }
        }

        binding.playButton.setOnClickListener{

            if (videoUrl!=null){
                initPlayer(videoUrl!!)
              binding.playButton.visibility=View.GONE
            }



        }
    }

    private fun initPlayer(url:String) {
//        trackSelector = DefaultTrackSelector(this)
//        trackSelector.setParameters(
//            trackSelector.parameters.buildUpon().setPreferredAudioLanguage("eng")
//        )
        val url="https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        val mediaItem = MediaItem.fromUri(url)

        binding.videoView.visibility=View.VISIBLE
        exoPlayer = ExoPlayer.Builder(requireContext())
           // .setTrackSelector(trackSelector)
            .setSeekBackIncrementMs(5000)
            .setSeekForwardIncrementMs(5000)
            .build()

        binding.videoView.apply {
            player = exoPlayer
            keepScreenOn = true
        }
        exoPlayer!!.setMediaItem(mediaItem)
        exoPlayer!!.playWhenReady = true    // playWhenReady
       // exoPlayer.seekTo(currentItem, playBackPosition)
        exoPlayer!!.prepare()
        exoPlayer!!.play()


        exoPlayer!!.addListener(object : Player.Listener {
            override fun onTracksChanged(tracks: Tracks) {
                // Update UI using current tracks.
            }
        })




        //tittle_text.text = currentVideo.tittle


        exoPlayer?.addListener(object : Player.Listener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                super.onPlayerStateChanged(playWhenReady, playbackState)

                if (playbackState == Player.STATE_BUFFERING) {
                    //binding.progressBar.visibility= View.VISIBLE

                } else if (playbackState == Player.STATE_READY) {
                    //  binding.progressBar.visibility= View.GONE

                }

//                if (!exoPlayer.playWhenReady) {
//                    handler.removeCallbacks(updateProgressAction)
//                } else {
//                    onProgress()
//                }
            }

            override fun onTracksChanged(tracks: Tracks) {

                super.onTracksChanged(tracks)

            }


        })


    }

    override fun onPause() {
        super.onPause()

        exoPlayer?.pause()

    }

    override fun onStop() {
        super.onStop()
        exoPlayer?.pause()
        exoPlayer?.stop()
        exoPlayer?.release()
        exoPlayer?.clearMediaItems()

    }



}