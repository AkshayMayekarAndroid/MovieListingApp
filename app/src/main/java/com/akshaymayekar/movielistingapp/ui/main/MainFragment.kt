package com.akshaymayekar.movielistingapp.ui.main

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.akshaymayekar.movielistingapp.R
import com.akshaymayekar.movielistingapp.ViewModel.MainViewModel
import com.akshaymayekar.movielistingapp.databinding.FragmentMainBinding
import com.akshaymayekar.movielistingapp.domain.model.Movie
import com.akshaymayekar.movielistingapp.ui.adapter.ListingAdapter
import com.akshaymayekar.movielistingapp.ui.adapter.PaginationListener
import com.akshaymayekar.movielistingapp.ui.adapter.PaginationListener.Companion.PAGE_START
import com.akshaymayekar.movielistingapp.util.Response
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: FragmentMainBinding? = null
    lateinit var manager: GridLayoutManager

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel


    lateinit var adpater : ListingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        observedata()

        viewModel.getMovieListing(1)

    }

    private fun setPaginationlistemer() {

    }

    private fun observedata() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.movieState.collect { response ->

                when (response) {
                    is Response.Failure -> {
                        hideLoadingDialog()
                        Toast.makeText(
                            requireContext(),
                            "Something went Wrong...",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is Response.Loading -> Unit
                    is Response.Success -> setDataToUI(response.data)
                }

            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        Toast.makeText(requireContext(), "Orientation Changed", Toast.LENGTH_SHORT).show()

        manager.spanCount = resources.getInteger(R.integer.grid_column_count)
        // manager.spanCount = (if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) 7 else 3)
        super.onConfigurationChanged(newConfig)
    }

    private fun setDataToUI(data: Movie?) {

        _binding?.let { it ->
            if(viewModel.list.isEmpty()) {
                manager = GridLayoutManager(requireContext(), 3)
                it.rvMovies.layoutManager = manager
                 data?.let { it1 ->
                     _binding?.toolbar?.title = data.page.title;
                    for(item in data.page.contentItems.content) {
                        viewModel._list.add(item)
                    }
                     adpater = ListingAdapter(viewModel.list)
                }
                it.rvMovies.adapter = adpater
            }else{
                data?.let {
                    for(item in data.page.contentItems.content) {
                        viewModel._list.add(item)
                    }
                    adpater.notifyDataSetChanged()
                }
            }
        }
        _binding?.rvMovies?.addOnScrollListener(object : PaginationListener(manager) {
            override fun loadMoreItems() {
                Toast.makeText(requireContext(), "Load more", Toast.LENGTH_SHORT).show()

                viewModel.isLoadingg = true;
                viewModel.currentPage++;
                viewModel.getMovieListing(viewModel.currentPage)
            }

            override val isLastPage: Boolean
                get() = viewModel.isLastPagee
            override val isLoading: Boolean
                get() = viewModel.isLoadingg

        })
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun Fragment.hideKeyboard() {
        _binding?.let { activity?.hideKeyboard(requireView()) }
    }

    private fun showLoadingDialog() {
        _binding?.let {
            it.progressCircular.visibility = View.VISIBLE
        }
    }

    private fun hideLoadingDialog() {
        _binding?.let {
            it.progressCircular.visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
       // return inflater.inflate(R.layout.fragment_main, container, false)
    }

}