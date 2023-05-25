package br.com.github.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.github.R
import br.com.github.databinding.ItemUserBinding
import br.com.github.domain.model.user.BaseUser
import br.com.github.ui.common.extensions.getDimension
import br.com.github.ui.common.extensions.loadImage

class ListUserAdapter(private val onItemClick: (BaseUser) -> Unit) :
    PagingDataAdapter<BaseUser, ListUserAdapter.ListViewHolder>(DIFF_CALLBACK) {

    inner class ListViewHolder(private val userBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(userBinding.root) {
        val context = userBinding.root.context
        fun bind(user: BaseUser) {
            userBinding.apply {
                tvItemUsername.text = user.login
                ivItemAvatar.loadImage(
                    url = user.avatarUrl.orEmpty(),
                    height = context.getDimension(R.dimen.userAvatarDetailHeight),
                    width = context.getDimension(R.dimen.userAvatarDetailWidth),
                    isCircleCrop = true
                )
                root.setOnClickListener { onItemClick(user) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val userBinding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(userBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        getItem(position)?.let { user ->
            holder.bind(user)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BaseUser>() {
            override fun areItemsTheSame(oldItem: BaseUser, newItem: BaseUser): Boolean {
                return oldItem.login == newItem.login
            }

            override fun areContentsTheSame(oldItem: BaseUser, newItem: BaseUser): Boolean {
                return oldItem.toString() == newItem.toString()
            }
        }
    }
}
