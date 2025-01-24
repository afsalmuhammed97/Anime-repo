package com.dev.jikanapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.jikanapp.databinding.FragmentListingBinding
import com.dev.jikanapp.network.Resource
import com.dev.jikanapp.ui.homelisting.HomeViewModel
import com.dev.jikanapp.ui.homelisting.ListingAdapter
import com.dev.jikanapp.uttil.handleApiError


class ListingFragment : Fragment() {

    private var _binding: FragmentListingBinding?=null
    private val binding get() = _binding!!
    private lateinit var listAdapter: ListingAdapter
    private lateinit var homeViewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding=FragmentListingBinding.inflate(inflater,container,false)
        homeViewModel =
            ViewModelProvider(requireActivity())[HomeViewModel::class.java]


         listAdapter= ListingAdapter(){
             Log.d("YY","click $it")
             val bundle = Bundle().apply {
                 putInt("anime_id", it)
             }
             findNavController().navigate(R.id.action_listingFragment_to_detailFragment,bundle)
         }
        binding.mainRv.apply {
            this.adapter=listAdapter
            this.layoutManager= LinearLayoutManager(context)
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel. animeList.observe(viewLifecycleOwner) {

            when (it) {

                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE

                    val data = it.value.body()!!
                      listAdapter.differ.submitList(data.data)
                    Log.d("TTT", "data ${it.value.body()!!.data.get(0)}")
                }

                is Resource.Failure -> {

                    binding.progressBar.visibility = View.VISIBLE
                    handleApiError(it)
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}