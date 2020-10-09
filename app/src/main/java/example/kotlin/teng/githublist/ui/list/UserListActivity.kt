package example.kotlin.teng.githublist.ui.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import example.kotlin.teng.githublist.ThisApplication
import example.kotlin.teng.githublist.R
import example.kotlin.teng.githublist.ui.detail.DetailActivity
import example.kotlin.teng.githublist.resource.network.UserItem
import example.kotlin.teng.githublist.resource.network.api_interface.GithubAPI
import kotlinx.android.synthetic.main.user_list_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class UserListActivity : AppCompatActivity(),
    UserListAdapter.UserListItemAdapterListener {

    private lateinit var mGithubService: GithubAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_list_activity)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this, (recyclerView.layoutManager as LinearLayoutManager).orientation)
        )
        mGithubService = (applicationContext as ThisApplication).mGithubService
        mGithubService.getPagerUsers(since, perPage).enqueue(object :
            Callback<List<UserItem>> {
            override fun onResponse(
                    call: Call<List<UserItem>>,
                    response: Response<List<UserItem>>
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
                    call: Call<List<UserItem>>,
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


    override fun setUsersListRecyclerView(resource: ArrayList<UserItem>) {
        Log.i(TAG, "setUsersListRecyclerView")

        val mUserItemAdapter = UserListAdapter(resource, this)

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

    fun addUsersListRecyclerView(items: ArrayList<UserItem>) {
        val mUserItemAdapter = (recyclerView.adapter as UserListAdapter)
        mUserItemAdapter.addUserItems(items)
    }

    fun onLoadMoreFailed() {
        val mUserItemAdapter = (recyclerView.adapter as UserListAdapter)
        mUserItemAdapter.onLoadMoreFailed()
        onGitHubRejectRequest()
    }

    fun onListItemClick(userItem: UserItem) {
        Log.i(TAG, "onListItemClick")
        startActivity(
            Intent(this, DetailActivity::class.java)
                .putExtra("login", userItem.login)
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
    private lateinit var userList: ArrayList<UserItem>

    fun onLoadMore() {
        mGithubService.getPagerUsers(since, perPage).enqueue(object :
            Callback<List<UserItem>> {
            override fun onResponse(
                    call: Call<List<UserItem>>,
                    response: Response<List<UserItem>>
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
                    call: Call<List<UserItem>>,
                    t: Throwable
            ) {
                Log.i(TAG, "onFailure")
                onLoadMoreFailed()
            }
        })
    }

    internal interface GLView {
        fun onGitHubRejectRequest()

        fun setUsersListRecyclerView(items: ArrayList<UserItem>)

        fun addUsersListRecyclerView(items: ArrayList<UserItem>)

        fun onLoadMoreFailed()

        fun onListItemClick(userItem: UserItem)

    }
}
