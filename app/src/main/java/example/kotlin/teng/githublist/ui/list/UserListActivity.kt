package example.kotlin.teng.githublist.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import example.kotlin.teng.githublist.databinding.UserListActivityBinding
import example.kotlin.teng.githublist.resource.network.UserItem
import example.kotlin.teng.githublist.ui.detail.DetailActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserListActivity : AppCompatActivity(), UserItemPagingAdapter.UserItemClickListener {

    private val viewModel: UserListViewModel by viewModel()
    private lateinit var binding: UserListActivityBinding

    private val userItemPagingAdapter: UserItemPagingAdapter by lazy {
        UserItemPagingAdapter()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UserListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.userListPaging.collectLatest {
                userItemPagingAdapter.submitData(it)
            }
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            this.addItemDecoration( DividerItemDecoration(
                applicationContext,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation))
            adapter = userItemPagingAdapter
            userItemPagingAdapter.userItemClickListener = this@UserListActivity
        }
    }

    override fun onUserItemClicked(userItem: UserItem) {
        startActivity(
            Intent(this, DetailActivity::class.java)
                .putExtra("login", userItem.login)
        )
    }
}
