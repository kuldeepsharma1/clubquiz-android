package com.kuldeep.clubquiz.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.kuldeep.clubquiz.R
import com.kuldeep.clubquiz.databinding.FragmentCategoriesBinding
import com.kuldeep.clubquiz.extensions.navigate
import com.kuldeep.clubquiz.extensions.showStyledSnackBar
import com.kuldeep.clubquiz.util.ApiInstance
import com.kuldeep.clubquiz.util.BundleArguments

class CategoriesFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchCategories()
    }

    private fun fetchCategories() {
        lifecycleScope.launchWhenCreated {
            val categories = try {
                ApiInstance.apiHelper.getCategories()
            } catch (e: Exception) {
                binding.root.showStyledSnackBar(R.string.network_error)
                return@launchWhenCreated
            }

            val sortedCategories = categories.sortedBy { it.name }

            val categoryNames = mutableListOf<String>()
            val categoryQueries = mutableListOf<String>()
            sortedCategories.forEach {
                categoryNames += it.name!!
                categoryQueries += it.id!!
            }

            val categoriesAdapter =
                ArrayAdapter(requireContext(), R.layout.list_item, categoryNames)
            binding.categoriesLV.adapter = categoriesAdapter

            binding.progress.visibility = View.GONE
            binding.categoriesLV.visibility = View.VISIBLE

            binding.categoriesLV.onItemClickListener = OnItemClickListener { _, _, index, _ ->
                val quizFragment = OnlineQuizFragment().apply {
                    arguments = bundleOf(BundleArguments.category to categoryQueries[index])
                }

                parentFragmentManager.navigate(quizFragment)
            }
        }
    }
}
