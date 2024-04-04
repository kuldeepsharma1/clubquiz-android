package com.kuldeep.clubquiz.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.kuldeep.clubquiz.R
import com.kuldeep.clubquiz.databinding.RowQuizBinding
import com.kuldeep.clubquiz.dialogs.QuizOptionsDialog
import com.kuldeep.clubquiz.extensions.navigate
import com.kuldeep.clubquiz.fragments.OfflineQuizFragment
import com.kuldeep.clubquiz.obj.Quiz
import com.kuldeep.clubquiz.util.BundleArguments
import com.kuldeep.clubquiz.util.PreferenceHelper

class LibraryAdapter(
    private val quizzes: List<Quiz>,
    private val parentFragment: Fragment
) : RecyclerView.Adapter<LibraryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowQuizBinding.inflate(layoutInflater, parent, false)
        return LibraryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        val quiz = quizzes[position]
        holder.binding.apply {
            quizName.text = quiz.name
            quizSize.text = root.context.getString(
                R.string.questions,
                quiz.questions?.size.toString()
            )
            quizType.setImageResource(
                if (quiz.creator == true) {
                    R.drawable.ic_bookmark
                } else {
                    R.drawable.ic_public
                }
            )
            root.setOnClickListener {
                val quizFragment = OfflineQuizFragment().apply {
                    val quizIndex = PreferenceHelper.getQuizzes().indexOf(quiz)
                    arguments = bundleOf(BundleArguments.quizIndex to quizIndex)
                }
                parentFragment.parentFragmentManager.navigate(quizFragment)
            }
            root.setOnLongClickListener {
                val quizOptionsDialog = QuizOptionsDialog(position)
                quizOptionsDialog.show(parentFragment.childFragmentManager, null)
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return quizzes.size
    }
}

class LibraryViewHolder(
    val binding: RowQuizBinding
) : RecyclerView.ViewHolder(binding.root)
