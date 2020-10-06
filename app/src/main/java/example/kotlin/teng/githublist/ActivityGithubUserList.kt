package example.kotlin.teng.githublist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import example.kotlin.teng.githublist.detail.ActivityGithubUserDetail
import example.kotlin.teng.githublist.network.GithubUserItem
import kotlinx.android.synthetic.main.activity_github_user_list.*
import java.util.*

class ActivityGithubUserList : AppCompatActivity(), GithubListPresenter.GLView,
    UsersListAdapter.UserListItemAdapterListener {

    private lateinit var mGithubListPresenter: GithubListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_user_list)

        initViews()

        mGithubListPresenter = GithubListPresenter(this, (applicationContext as BaseApplication).mGithubService)
        mGithubListPresenter.onCreate()
    }

    private fun initViews() {
        Log.i(TAG, "initViews")

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
            this, (recyclerView.layoutManager as LinearLayoutManager).orientation)
        )
    }

    override fun onGitHubRejectRequest() {
        Toast.makeText(this, "GitHub reject request!", Toast.LENGTH_SHORT).show()
    }


    override fun setUsersListRecyclerView(resource: ArrayList<GithubUserItem>) {
        Log.i(TAG, "setUsersListRecyclerView")

        val mUserItemAdapter = UsersListAdapter(resource, this)

        Toast.makeText(this, "Loading more...", Toast.LENGTH_SHORT).show()
        mGithubListPresenter.onLoadMore()
//        mUserItemAdapter.setLoadMoreListener({
//            Toast.makeText(this, "Loading more...", Toast.LENGTH_SHORT).show()
//            mGithubListPresenter.onLoadMore()
//        })
        recyclerView.recycledViewPool.clear()
        recyclerView.adapter = mUserItemAdapter
        mUserItemAdapter.notifyDataSetChanged()
    }

    override fun addUsersListRecyclerView(items: ArrayList<GithubUserItem>) {
        val mUserItemAdapter = (recyclerView.adapter as UsersListAdapter)
        mUserItemAdapter.addUserItems(items)
    }

    override fun onLoadMoreFailed() {
        val mUserItemAdapter = (recyclerView.adapter as UsersListAdapter)
        mUserItemAdapter.onLoadMoreFailed()
        onGitHubRejectRequest()
    }

    override fun onListItemClick(githubUserItem: GithubUserItem) {
        Log.i(TAG, "onListItemClick")
        startActivity(
            Intent(this, ActivityGithubUserDetail::class.java)
                .putExtra("login", githubUserItem.login)
        )
    }

    override fun onUserItemClick(position: Int) {
        Log.i(TAG, "onUserItemClick")
        mGithubListPresenter.onUserItemClick(position)
    }

    companion object {
        private val TAG = "ActivityGithubUserList"
    }

}
