package com.zestworks.moviedetail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.zestworks.common.LCE.Content
import com.zestworks.common.LCE.Error
import com.zestworks.common.LCE.Loading
import com.zestworks.moviedetail.databinding.MovieDetailFragmentBinding
import com.zestworks.moviedetail.viewmodel.MovieDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class MovieDetailFragment : Fragment() {
    private lateinit var binding: MovieDetailFragmentBinding
    private val viewModel: MovieDetailViewModel by viewModel()

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

        //TODO : This link construction and inference can be done inside a class in a new "navigator" module that every feature module depends on.
        val movieID: String = arguments!!["movieID"].toString()

        viewModel.viewState(movieID.toInt()).observe(viewLifecycleOwner, Observer {
            when (it) {
                Loading -> {
                    binding.apply {
                        progressBar.visibility = VISIBLE
                        content.visibility = GONE
                    }
                }
                is Content -> {
                    binding.apply {
                        progressBar.visibility = GONE
                        content.visibility = VISIBLE
                        textMovieTitle.text = it.data.title
                        textMovieOverview.text = it.data.overview
                        //TODO : UiModel
                        Glide.with(requireContext())
                            .load("https://image.tmdb.org/t/p/w780${it.data.backdropPath}")
                            .into(imageMovieBackdrop)
                    }
                }
                is Error -> {
                    binding.apply {
                        progressBar.visibility = GONE
                        content.visibility = GONE
                        Snackbar.make(root, it.errorMessage, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}