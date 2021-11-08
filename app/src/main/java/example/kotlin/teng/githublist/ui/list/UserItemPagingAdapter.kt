package example.kotlin.teng.githublist.ui.list

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import example.kotlin.teng.githublist.databinding.ItemUserListBinding
import example.kotlin.teng.githublist.resource.network.response.UserItem

class UserItemPagingAdapter : PagingDataAdapter<UserItem, UserItemPagingAdapter.UserViewHolder>(
    UserComparator
) {
    var userItemClickListener: UserItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemPagingAdapter.UserViewHolder {
        return UserViewHolder(
            ItemUserListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class UserViewHolder(private val binding: ItemUserListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                userItemClickListener?.onUserItemClicked(
                    getItem(absoluteAdapterPosition) as UserItem
                )
            }
        }

        fun bind(item: UserItem) = with(binding) {
            tvLogin.text = item.login
            icBadge.visibility =
                when (item.siteAdmin) {
                    true -> View.VISIBLE
                    false -> View.GONE
                    null -> View.GONE
                }

            Glide.with(root.context)
                .load(item.avatarUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                })
                .into(imgAvatarUrl)
        }
    }

    override fun onBindViewHolder(vh: UserItemPagingAdapter.UserViewHolder, position: Int) {
        getItem(position)?.let { vh.bind(it) }
    }

    companion object {
        private val UserComparator = object : DiffUtil.ItemCallback<UserItem>() {
            override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
                return (oldItem.id == newItem.id)
            }

            override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean =
                oldItem == newItem
        }
    }

    interface UserItemClickListener {
        fun onUserItemClicked(userItem: UserItem)
    }

}