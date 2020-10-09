package example.kotlin.teng.githublist.ui.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import example.kotlin.teng.githublist.R
import example.kotlin.teng.githublist.custom.livedata.LiveDataContent
import example.kotlin.teng.githublist.custom.livedata.LiveDataObserver
import example.kotlin.teng.githublist.ui.detail.DetailActivity
import example.kotlin.teng.githublist.resource.network.UserItem
import example.kotlin.teng.githublist.ui.base.BaseActivity
import kotlinx.android.synthetic.main.user_list_activity.*
import kotlin.collections.ArrayList

class UserListActivity : BaseActivity(),
    UserListAdapter.UserListItemAdapterListener {

    private lateinit var _viewModel: UserListViewModel

    private var since = 0
    private val perPage = 20
    private lateinit var userList: ArrayList<UserItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_list_activity)

        _viewModel = ViewModelProvider(this).get(UserListViewModel::class.java)
        _viewModel.userList.observe(this, _userList)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this, (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )

        _viewModel.getUserList(since, perPage)
    }

    private val _userList =
        object : LiveDataObserver<LiveDataContent<ArrayList<UserItem>>>(lifecycleEvent) {
            override fun onContentChanged(liveDataContent: LiveDataContent<ArrayList<UserItem>>) {
                if (liveDataContent.content != null) {
                    val list = liveDataContent.content
                    if (list != null) {
                        since += perPage
                        userList = list
                        setUsersListRecyclerView(list)
                    } else {
                        onGitHubRejectRequest()
                    }
                } else {
                    onGitHubRejectRequest()
                }
            }
        }


    private fun onGitHubRejectRequest() {
        Toast.makeText(this, "GitHub reject request!", Toast.LENGTH_SHORT).show()
    }


    override fun setUsersListRecyclerView(resource: ArrayList<UserItem>) {
        Log.i(TAG, "setUsersListRecyclerView")

        val mUserItemAdapter = UserListAdapter(resource, this)

        Toast.makeText(this, "Loading more...", Toast.LENGTH_SHORT).show()
//        onLoadMore()
        recyclerView.recycledViewPool.clear()
        recyclerView.adapter = mUserItemAdapter
        mUserItemAdapter.notifyDataSetChanged()
    }

    override fun onUserItemClick(position: Int) {
        Log.i(TAG, "onUserItemClick")
        onListItemClick(userList[position])
    }

    private fun onListItemClick(userItem: UserItem) {
        Log.i(TAG, "onListItemClick")
        startActivity(
            Intent(this, DetailActivity::class.java)
                .putExtra("login", userItem.login)
        )
    }

    companion object {
        private const val TAG = "UserListActivity"
    }

    private fun onLoadMore() {
        _viewModel.getUserList(since,perPage)
//        mGithubService.getPagerUsers(since, perPage).enqueue(object :
//            Callback<List<UserItem>> {
//            override fun onResponse(
//                call: Call<List<UserItem>>,
//                response: Response<List<UserItem>>
//            ) {
//                Log.i(TAG, "onResponse")
//                if (response.body() != null) {
//                    since += perPage
//                    val items = ArrayList(response.body()!!)
//                    userList.addAll(items)
//                    addUsersListRecyclerView(items)
//                } else {
//                    onGitHubRejectRequest()
//                }
//            }
//
//            override fun onFailure(
//                call: Call<List<UserItem>>,
//                t: Throwable
//            ) {
//                Log.i(TAG, "onFailure")
//                onLoadMoreFailed()
//            }
//        })
    }

}
