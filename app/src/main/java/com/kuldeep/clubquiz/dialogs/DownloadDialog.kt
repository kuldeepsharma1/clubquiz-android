package com.kuldeep.clubquiz.dialogs

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.kuldeep.clubquiz.R
import com.kuldeep.clubquiz.databinding.DialogDownloadBinding
import com.kuldeep.clubquiz.obj.Quiz
import com.kuldeep.clubquiz.util.ApiInstance
import com.kuldeep.clubquiz.util.PreferenceHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DownloadDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogDownloadBinding.inflate(layoutInflater)
        binding.input.hint = getString(R.string.quiz_name)

        val categoryNames = mutableListOf(getString(R.string.none))
        val categoryQueries = mutableListOf("")

        lifecycleScope.launchWhenCreated {
            ApiInstance.apiHelper.getCategories().forEach {
                categoryNames += it.name!!
                categoryQueries += it.id!!
            }
            binding.spinner.adapter = ArrayAdapter(
                requireContext(),
                R.layout.spinner_item,
                categoryNames
            )
        }

        // build the dialog
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.download)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val name = binding.input.text.toString()
                if (name != "") {
                    val position = binding.spinner.selectedItemPosition
                    val category = if (position == 0) null else categoryQueries[position]
                    downloadQuestions(binding.input.text.toString(), category)
                } else {
                    Toast.makeText(context, R.string.name_empty, Toast.LENGTH_SHORT).show()
                }
            }
            .setView(binding.root)
        return dialog.create()
    }

    private fun downloadQuestions(name: String, category: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val questions = try {
                ApiInstance.apiHelper.getQuestions(category)
            } catch (e: Exception) {
                Log.e("download failed", e.toString())
                return@launch
            }
            val quiz = Quiz(
                name = name,
                creator = false,
                questions = questions
            )
            Log.e("quiz", quiz.toString())
            PreferenceHelper.saveQuiz(quiz)
        }
    }
}
