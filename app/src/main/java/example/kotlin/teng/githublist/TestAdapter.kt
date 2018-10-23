package example.kotlin.teng.githublist

import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import example.kotlin.teng.githublist.network.GithubUserItem

class TestAdapter internal constructor(
    private val githubUserListItem: List<GithubUserItem>?) :
    RecyclerView.Adapter<TestAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title)
        var year: TextView = view.findViewById(R.id.year)
        var genre: TextView = view.findViewById(R.id.genre)
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.test_list_item, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(@NonNull holder: MyViewHolder, position: Int) {
        val item = githubUserListItem!![position]
        holder.title.text = item.login
    }

    override fun getItemCount(): Int {
        return githubUserListItem!!.size
    }
}

