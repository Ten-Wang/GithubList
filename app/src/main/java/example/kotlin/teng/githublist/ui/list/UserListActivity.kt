package example.kotlin.teng.githublist.ui.list

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import example.kotlin.teng.githublist.databinding.UserListActivityBinding
import example.kotlin.teng.githublist.resource.network.UserItem
import example.kotlin.teng.githublist.ui.detail.DetailActivity
import example.kotlin.teng.githublist.ui.recycler.LoadMoreExecutor
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserListActivity : AppCompatActivity(),
    UserListAdapter.UserListItemAdapterListener {

    private val viewModel: UserListViewModel by viewModel()
    private lateinit var binding: UserListActivityBinding

    private var since = 0
    private val perPage = 20
    private var userList = arrayListOf<UserItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UserListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.userList.observe(this) {
            val list = it
            if (list != null) {
                userList.addAll(list)
                since = list[list.size - 1].id
                setUsersListRecyclerView(userList)
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this, (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )

        viewModel.getUserList(since, perPage)
    }

    override fun setUsersListRecyclerView(resource: ArrayList<UserItem>) {
        val mUserItemAdapter = UserListAdapter(resource, this)
        mUserItemAdapter.setLoadMoreEnable(true)
        mUserItemAdapter.setLoadMoreListener(object : LoadMoreExecutor.LoadMoreListener {
            override fun onLoadMore() {
                viewModel.getUserList(since, perPage)
            }
        })
        Toast.makeText(this, "Loading more...", Toast.LENGTH_SHORT).show()

        binding.recyclerView.recycledViewPool.clear()
        binding.recyclerView.adapter = mUserItemAdapter
    }

    override fun onUserItemClick(position: Int) {
        startActivity(
            Intent(this, DetailActivity::class.java)
                .putExtra("login", userList[position].login)
        )
    }
}
