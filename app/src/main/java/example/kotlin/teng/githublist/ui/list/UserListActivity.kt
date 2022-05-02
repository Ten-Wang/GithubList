package example.kotlin.teng.githublist.ui.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import example.kotlin.teng.githublist.databinding.ActivityUserListBinding
import example.kotlin.teng.githublist.resource.network.response.UserItem
import example.kotlin.teng.githublist.resource.utils.Constants
import example.kotlin.teng.githublist.ui.detail.DetailFragment
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserListActivity : AppCompatActivity() {

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
            layoutManager = LinearLayoutManager(this@UserListActivity)
            this.addItemDecoration(
                DividerItemDecoration(
                    this@UserListActivity,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
            userItemPagingAdapter.userItemClickListener = object :
                UserItemPagingAdapter.UserItemClickListener {
                override fun onUserItemClicked(userItem: UserItem) {
                    DetailFragment.newInstance(userItem.login)
                        .show(supportFragmentManager, Constants.BUNDLE_STR)
                }

            }
            adapter = userItemPagingAdapter
        }

        MainScope().launch {
            listViewModel.userListPaging.collectLatest {
                userItemPagingAdapter.submitData(it)
            }
        }
    }
}
