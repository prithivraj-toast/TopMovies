package com.zestworks.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.zestworks.common.LCE.Content
import com.zestworks.common.LCE.Error
import com.zestworks.common.LCE.Loading
import com.zestworks.movielist.databinding.MovieListFragmentBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MovieListFragment : Fragment() {

    private lateinit var viewModel: MovieListViewModel
    private lateinit var binding: MovieListFragmentBinding

    private val movieClickedAction = { id: Int ->
        findNavController().navigate(
            resources.getString(
                R.string.url_movie_detail_formatter,
                id.toString()
            ).toUri()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieListFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            requireActivity(),
            MovieListViewModelFactory(requireContext())
        )[MovieListViewModel::class.java]

        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            when (it) {
                Loading -> {
                    binding.apply {
                        progressBarMovieList.visibility = View.VISIBLE
                        listMovieList.visibility = View.GONE
                    }
                }
                is Content -> {
                    binding.apply {
                        progressBarMovieList.visibility = View.GONE
                        listMovieList.visibility = View.VISIBLE
                        if (listMovieList.adapter == null) {
                            listMovieList.adapter = MovieListAdapter(it.data, movieClickedAction)
                            listMovieList.layoutManager = LinearLayoutManager(context)
                        } else {
                            (listMovieList.adapter as MovieListAdapter).setData(it.data)
                            listMovieList.adapter!!.notifyDataSetChanged()
                        }
                    }
                }
                is Error -> {
                    binding.apply {
                        progressBarMovieList.visibility = View.GONE
                        listMovieList.visibility = View.VISIBLE
                        Snackbar.make(root, it.errorMessage, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
