package example.kotlin.teng.githublist

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import example.kotlin.teng.githublist.detail.ActivityGithubUserDetail
import example.kotlin.teng.githublist.network.GithubService
import example.kotlin.teng.githublist.network.GithubUserItem

import java.util.ArrayList

class ActivityGithubUserList : AppCompatActivity(), GithubListPresenter.GLView,
    UsersListAdapter.UserListItemAdapterListener {

    private lateinit var mGithubListPresenter: GithubListPresenter
    private lateinit var mGithubService: GithubService
    private lateinit var userRecycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_user_list)

        initViews()
        mGithubService = (application as BaseApplication).githubService

        mGithubListPresenter = GithubListPresenter(this, mGithubService)
        mGithubListPresenter.onCreate()
    }

    private fun initViews() {
        Log.i(TAG, "initViews")
        userRecycler = findViewById(R.id.recyclerView)
        val linearLayoutManager = LinearLayoutManager(this)
        userRecycler.layoutManager = linearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(
            userRecycler.context,
            linearLayoutManager.orientation
        )
        userRecycler.addItemDecoration(dividerItemDecoration)
    }

    override fun onGitHubRejectRequest() {
        Toast.makeText(this, "GitHub reject request!", Toast.LENGTH_SHORT).show()
    }

    override fun setUsersListRecyclerView(resource: ArrayList<GithubUserItem>) {
        Log.i(TAG, "setUsersListRecyclerView")

        val mUserItemAdapter = UsersListAdapter(resource, this)
//        mUserItemAdapter.setLoadMoreListener {
//            Toast.makeText(this, "Loading more...", Toast.LENGTH_SHORT).show()
//            mGithubListPresenter.onLoadMore()
//        }
        userRecycler.recycledViewPool.clear()
        userRecycler.adapter = mUserItemAdapter
        mUserItemAdapter.notifyDataSetChanged()
    }

    override fun addUsersListRecyclerView(items: ArrayList<GithubUserItem>) {
        val mUserItemAdapter = (userRecycler.adapter as UsersListAdapter)
        mUserItemAdapter.addUserItems(items)
    }

    override fun onLoadMoreFailed() {
        val mUserItemAdapter = (userRecycler.adapter as UsersListAdapter)
        mUserItemAdapter.onLoadMoreFailed()
        onGitHubRejectRequest()
    }

    override fun onListItemClick(userItem: GithubUserItem) {
        Log.i(TAG, "onListItemClick")
        startActivity(
            Intent(this, ActivityGithubUserDetail::class.java)
                .putExtra("login", userItem.login)
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
