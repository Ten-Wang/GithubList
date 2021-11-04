package example.kotlin.teng.githublist.ui.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import example.kotlin.teng.githublist.custom.livedata.LiveDataContent
import example.kotlin.teng.githublist.custom.livedata.LiveDataObserver
import example.kotlin.teng.githublist.databinding.UserListActivityBinding
import example.kotlin.teng.githublist.resource.network.UserItem
import example.kotlin.teng.githublist.ui.base.BaseActivity
import example.kotlin.teng.githublist.ui.detail.DetailActivity
import example.kotlin.teng.githublist.ui.recycler.LoadMoreExecutor
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserListActivity : BaseActivity(),
    UserListAdapter.UserListItemAdapterListener {

    private val _viewModel: UserListViewModel by viewModel()
    private lateinit var binding: UserListActivityBinding

    private var since = 0
    private val perPage = 20
    private lateinit var userList: ArrayList<UserItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UserListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _viewModel.userList.observe(this, _userList)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this, (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
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
                        userList = list
                        since = list[list.size - 1].id ?: since + 20
                        setUsersListRecyclerView(list)
                    }
                }
            }
        }


    override fun setUsersListRecyclerView(resource: ArrayList<UserItem>) {
        Log.i(TAG, "setUsersListRecyclerView")

        val mUserItemAdapter = UserListAdapter(resource, this)
        mUserItemAdapter.setLoadMoreEnable(true)
        mUserItemAdapter.setLoadMoreListener(object : LoadMoreExecutor.LoadMoreListener {
            override fun onLoadMore() {
                _viewModel.getUserList(since, perPage)
            }
        })
        Toast.makeText(this, "Loading more...", Toast.LENGTH_SHORT).show()

        binding.recyclerView.recycledViewPool.clear()
        binding.recyclerView.adapter = mUserItemAdapter
    }

    override fun onUserItemClick(position: Int) {
        Log.i(TAG, "onUserItemClick")
        startActivity(
                Intent(this, DetailActivity::class.java)
                        .putExtra("login", userList[position].login)
        )
    }

    companion object {
        private const val TAG = "UserListActivity"
    }

}
