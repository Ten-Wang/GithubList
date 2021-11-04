package example.kotlin.teng.githublist.ui.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.util.Linkify
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import example.kotlin.teng.githublist.custom.livedata.LiveDataContent
import example.kotlin.teng.githublist.custom.livedata.LiveDataObserver
import example.kotlin.teng.githublist.databinding.UsersDetailActivityBinding
import example.kotlin.teng.githublist.resource.network.UserDetailItem
import example.kotlin.teng.githublist.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : BaseActivity() {

    private val viewModel: DetailViewModel by viewModel()
    private lateinit var binding: UsersDetailActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = UsersDetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.userDetail.observe(this, userDetailObserver)

        binding.tvBlog.autoLinkMask = Linkify.ALL

        val login = intent.getStringExtra("login")
        if (login != null) {
            viewModel.getUserDetail(login)
        }

        binding.btnClose.setOnClickListener { finish() }
    }

    private val userDetailObserver =
        object : LiveDataObserver<LiveDataContent<UserDetailItem>>(lifecycleEvent) {
            override fun onContentChanged(liveDataContent: LiveDataContent<UserDetailItem>) {
                if (liveDataContent.content != null) {
                    val userDetailItem = liveDataContent.content
                    if (userDetailItem != null) {
                        binding.tvName.text = userDetailItem.name
                        if (userDetailItem.bio != null) {
                            binding.textViewBio.text = userDetailItem.bio.toString()
                        }
                        binding.tvLogin.text = userDetailItem.login
                        binding.tvLocation.text = userDetailItem.location
                        binding.tvBlog.text = userDetailItem.blog
                        binding.viewBadge.visibility =
                            if (userDetailItem.siteAdmin != null) View.VISIBLE else View.GONE

                        Glide.with(applicationContext)
                            .load(userDetailItem.avatarUrl)
                            .listener(object : RequestListener<Drawable> {
                                override fun onLoadFailed(
                                    e: GlideException?, model: Any,
                                    target: Target<Drawable>, isFirstResource: Boolean
                                ): Boolean {
                                    binding.progressBar.visibility = View.GONE
                                    return false
                                }

                                override fun onResourceReady(
                                    resource: Drawable,
                                    model: Any,
                                    target: Target<Drawable>,
                                    dataSource: DataSource,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    binding.progressBar.visibility = View.GONE
                                    return false
                                }
                            })
                            .into(binding.imgAvatarUrl)
                    }
                }
            }
        }
}
