package com.kuldeep.clubquiz.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.kuldeep.clubquiz.R
import com.kuldeep.clubquiz.databinding.FragmentStatsBinding
import com.kuldeep.clubquiz.util.StatsHelper

class UserStatsFragment : Fragment() {
    private lateinit var binding: FragmentStatsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val stats = StatsHelper.getStats(requireContext())
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, stats)
        binding.apiStats.adapter = adapter

        binding.progress.visibility = View.GONE
        binding.apiStats.visibility = View.VISIBLE
    }
}
