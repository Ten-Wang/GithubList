package example.kotlin.teng.githublist.ui.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import example.kotlin.teng.githublist.databinding.ActivityUserListBinding
import example.kotlin.teng.githublist.resource.network.UserItem
import example.kotlin.teng.githublist.ui.detail.DetailFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserListActivity : AppCompatActivity(), UserItemPagingAdapter.UserItemClickListener {

    private lateinit var binding: ActivityUserListBinding
    private val listViewModel: UserListViewModel by viewModel()
    private val userItemPagingAdapter: UserItemPagingAdapter by lazy {
        UserItemPagingAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            this.addItemDecoration( DividerItemDecoration(
                applicationContext,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation))
            adapter = userItemPagingAdapter
            userItemPagingAdapter.userItemClickListener = this@UserListActivity
        }

        lifecycleScope.launch {
            listViewModel.userListPaging.collectLatest {
                userItemPagingAdapter.submitData(it)
            }
        }
    }

    override fun onUserItemClicked(userItem: UserItem) {
        DetailFragment.newInstance(userItem.login)
            .show(supportFragmentManager, "login")
//        startActivity(
//            Intent(this, DetailActivity::class.java)
//                .putExtra("login", userItem.login)
//        )
    }
}
