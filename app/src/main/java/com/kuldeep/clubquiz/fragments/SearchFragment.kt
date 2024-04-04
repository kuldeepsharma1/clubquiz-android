package com.kuldeep.clubquiz.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuldeep.clubquiz.adapters.LibraryAdapter
import com.kuldeep.clubquiz.databinding.FragmentSearchBinding
import com.kuldeep.clubquiz.models.SearchViewModel
import com.kuldeep.clubquiz.util.BundleArguments
import com.kuldeep.clubquiz.util.PreferenceHelper

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var query: String
    private val viewModel: SearchViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        query = arguments?.getString(BundleArguments.query)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.searchResults.layoutManager = LinearLayoutManager(requireContext())

        viewModel.searchQuery.observe(viewLifecycleOwner) {
            loadSuggestions(it)
        }

        loadSuggestions(query)
    }

    private fun loadSuggestions(query: String) {
        val quizzes = PreferenceHelper.getQuizzes()
        val results = quizzes.filter {
            // contained in the name
            it.name
                ?.lowercase()!!
                .contains(query.lowercase()) ||
                // contained in one question
                it.questions!!
                    .filter {
                        it.question!!
                            .lowercase()
                            .contains(query.lowercase())
                    }
                    .isNotEmpty()
        }

        val adapter = LibraryAdapter(results, this)
        binding.searchResults.adapter = adapter
    }
}
