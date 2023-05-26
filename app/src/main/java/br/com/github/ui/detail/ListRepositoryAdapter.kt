package br.com.github.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.github.R
import br.com.github.databinding.ItemRepositoryBinding
import br.com.github.domain.model.repos.UserRepositoryModel

class ListRepositoryAdapter() :
    ListAdapter<UserRepositoryModel, ListRepositoryAdapter.ListViewHolder>(DIFF_CALLBACK) {

    inner class ListViewHolder(private val repositoryBinding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(repositoryBinding.root) {
        fun bind(model: UserRepositoryModel) = with(repositoryBinding) {
            val context = root.context
            tvRepoName.text = model.name
            tvDescription.text = model.description
            tvForkCount.text =
                String.format(context.getString(R.string.value_with_forks), model.forksCount)
            tvStarCount.text =
                String.format(context.getString(R.string.value_with_starts), model.stargazersCount)
            tvWatchCount.text =
                String.format(context.getString(R.string.value_with_following), model.watchersCount)
            tvLicense.text = model.license?.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val repositoryBinding =
            ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(repositoryBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        getItem(position)?.let { repo ->
            holder.bind(repo)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserRepositoryModel>() {
            override fun areItemsTheSame(
                oldItem: UserRepositoryModel,
                newItem: UserRepositoryModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UserRepositoryModel,
                newItem: UserRepositoryModel
            ): Boolean {
                return oldItem.toString() == newItem.toString()
            }
        }
    }
}
