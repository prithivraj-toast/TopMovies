package com.zestworks.moviedetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zestworks.moviedetail.databinding.MovieDetailFragmentBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MovieDetailFragment : Fragment() {
    private lateinit var binding: MovieDetailFragmentBinding
    private lateinit var viewModel: MovieDetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MovieDetailFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            requireActivity(),
            MovieDetailViewModelFactory(requireContext())
        )[MovieDetailViewModel::class.java]

        //TODO : This link construction and inference can be done inside a class in a new "navigator" module that every feature module depends on.
        val movieID: String = arguments!!["movieID"].toString()

        viewModel.viewState(movieID.toInt()).observe(viewLifecycleOwner, Observer {
            Log.d("test", it.toString())
        })
    }
}