package example.kotlin.teng.githublist

import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import example.kotlin.teng.githublist.base.RecyclerAdapterBase
import example.kotlin.teng.githublist.network.GithubUserItem

import java.util.ArrayList

class UsersListAdapter internal constructor(
    private val userList: ArrayList<GithubUserItem>,
    private val mListener: UserListItemAdapterListener
) : RecyclerAdapterBase<GithubUserItem>(userList) {

    internal var selectedPosition = -1

    init {
        isLoadMoreEnable
    }

    override fun onCreateItemViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return UserViewHolder(
            inflater.inflate(R.layout.item_user_list, parent, false))
    }

    override fun onBindItemViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val vh = viewHolder as UserViewHolder
        vh.bindData(userList, position)
    }

    fun resetUserItems(items: List<GithubUserItem>) {
        this.userList.clear()
        this.userList.addAll(items)
    }

    fun addUserItems(items: ArrayList<GithubUserItem>) {
        this.userList.addAll(items)
        notifyLoadMoreChanged(true, items.size)
    }

    private inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textViewLogin: TextView = itemView.findViewById(R.id.textView_login)
        private val progress: ProgressBar = itemView.findViewById(R.id.progressBar)
        private val relativeBadge: RelativeLayout = itemView.findViewById(R.id.relative_badge)
        private val imgAvatarUrl: ImageView = itemView.findViewById(R.id.img_avatar_url)
        private val contentLayout: View = itemView.findViewById(R.id.item_user_content)

        init {
            contentLayout.setOnClickListener { _ ->
                selectedPosition = getItemPosition(this@UserViewHolder)
                mListener.onUserItemClick(getItemPosition(this@UserViewHolder))
            }
        }

        internal fun bindData(list: List<GithubUserItem>, position: Int) {
            val item = list[position]
            textViewLogin.text = item.login
            relativeBadge.visibility =
                    when (item.siteAdmin){
                        true ->View.VISIBLE
                        false->View.GONE
                        null ->View.GONE
                    }

            Glide.with(itemView.context)
                .load(item.avatarUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress.visibility = View.GONE
                        return false
                    }
                })
                .into(imgAvatarUrl)
        }
    }

    internal interface UserListItemAdapterListener {
        fun onUserItemClick(position: Int)
        fun setUsersListRecyclerView(resource: ArrayList<GithubUserItem>)
    }
}
