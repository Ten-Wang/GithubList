package example.kotlin.teng.githublist.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import example.kotlin.teng.githublist.GithubListApplication
import example.kotlin.teng.githublist.R
import example.kotlin.teng.githublist.ui.detail.ActivityGithubUserDetail
import example.kotlin.teng.githublist.resource.network.GithubUserItem
import example.kotlin.teng.githublist.resource.network.api_interface.GithubAPI
import kotlinx.android.synthetic.main.activity_github_user_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ActivityGithubUserList : AppCompatActivity(),
    UsersListAdapter.UserListItemAdapterListener {

    private lateinit var mGithubService: GithubAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_user_list)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this, (recyclerView.layoutManager as LinearLayoutManager).orientation)
        )
        mGithubService = (applicationContext as GithubListApplication).mGithubService
        mGithubService.getPagerUsers(since, perPage).enqueue(object :
            Callback<List<GithubUserItem>> {
            override fun onResponse(
                call: Call<List<GithubUserItem>>,
                response: Response<List<GithubUserItem>>
            ) {
                Log.i(TAG, "onResponse")
                if (response.body() != null) {
                    since += perPage
                    userList = ArrayList(response.body()!!)
                    setUsersListRecyclerView(userList)
                } else {
                    onGitHubRejectRequest()
                }

            }

            override fun onFailure(
                call: Call<List<GithubUserItem>>,
                t: Throwable
            ) {
                Log.i(TAG, "onFailure")
                onGitHubRejectRequest()
            }
        })

    }

    private fun onGitHubRejectRequest() {
        Toast.makeText(this, "GitHub reject request!", Toast.LENGTH_SHORT).show()
    }


    override fun setUsersListRecyclerView(resource: ArrayList<GithubUserItem>) {
        Log.i(TAG, "setUsersListRecyclerView")

        val mUserItemAdapter = UsersListAdapter(resource, this)

        Toast.makeText(this, "Loading more...", Toast.LENGTH_SHORT).show()
        onLoadMore()
//        mUserItemAdapter.setLoadMoreListener({
//            Toast.makeText(this, "Loading more...", Toast.LENGTH_SHORT).show()
//            mGithubListPresenter.onLoadMore()
//        })
        recyclerView.recycledViewPool.clear()
        recyclerView.adapter = mUserItemAdapter
        mUserItemAdapter.notifyDataSetChanged()
    }

    fun addUsersListRecyclerView(items: ArrayList<GithubUserItem>) {
        val mUserItemAdapter = (recyclerView.adapter as UsersListAdapter)
        mUserItemAdapter.addUserItems(items)
    }

    fun onLoadMoreFailed() {
        val mUserItemAdapter = (recyclerView.adapter as UsersListAdapter)
        mUserItemAdapter.onLoadMoreFailed()
        onGitHubRejectRequest()
    }

    fun onListItemClick(githubUserItem: GithubUserItem) {
        Log.i(TAG, "onListItemClick")
        startActivity(
            Intent(this, ActivityGithubUserDetail::class.java)
                .putExtra("login", githubUserItem.login)
        )
    }

    override fun onUserItemClick(position: Int) {
        Log.i(TAG, "onUserItemClick")
        onListItemClick(userList[position])
    }

    companion object {
        private val TAG = "ActivityGithubUserList"
    }
    private var since = 0
    private val perPage = 20
    private lateinit var userList: ArrayList<GithubUserItem>

    fun onLoadMore() {
        mGithubService.getPagerUsers(since, perPage).enqueue(object :
            Callback<List<GithubUserItem>> {
            override fun onResponse(
                call: Call<List<GithubUserItem>>,
                response: Response<List<GithubUserItem>>
            ) {
                Log.i(TAG, "onResponse")
                if (response.body() != null) {
                    since += perPage
                    val items = ArrayList(response.body()!!)
                    userList.addAll(items)
                    addUsersListRecyclerView(items)
                } else {
                    onGitHubRejectRequest()
                }
            }

            override fun onFailure(
                call: Call<List<GithubUserItem>>,
                t: Throwable
            ) {
                Log.i(TAG, "onFailure")
                onLoadMoreFailed()
            }
        })
    }

    internal interface GLView {
        fun onGitHubRejectRequest()

        fun setUsersListRecyclerView(items: ArrayList<GithubUserItem>)

        fun addUsersListRecyclerView(items: ArrayList<GithubUserItem>)

        fun onLoadMoreFailed()

        fun onListItemClick(githubUserItem: GithubUserItem)

    }
}
