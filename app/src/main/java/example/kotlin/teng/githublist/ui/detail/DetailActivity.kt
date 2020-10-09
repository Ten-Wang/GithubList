package example.kotlin.teng.githublist.ui.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.util.Linkify
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import example.kotlin.teng.githublist.R
import example.kotlin.teng.githublist.custom.livedata.LiveDataContent
import example.kotlin.teng.githublist.custom.livedata.LiveDataObserver
import example.kotlin.teng.githublist.resource.network.UserDetailItem
import example.kotlin.teng.githublist.ui.base.BaseActivity
import kotlinx.android.synthetic.main.users_detail_activity.*

class DetailActivity : BaseActivity() {

    private lateinit var _viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.users_detail_activity)

        _viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        _viewModel.userDetail.observe(this, _userDetail)

        tv_blog.autoLinkMask = Linkify.ALL

        val login = intent.getStringExtra("login")
        if (login != null) {
            _viewModel.getUserDetail(login)
        }

        btn_close.setOnClickListener { finish() }
    }

    private val _userDetail =
        object : LiveDataObserver<LiveDataContent<UserDetailItem>>(lifecycleEvent) {
            override fun onContentChanged(liveDataContent: LiveDataContent<UserDetailItem>) {
                if (liveDataContent.content != null) {
                    val userDetailItem = liveDataContent.content
                    if (userDetailItem != null) {
                        tv_name.text = userDetailItem.name
                        if (userDetailItem.bio != null) {
                            textView_Bio.text = userDetailItem.bio.toString()
                        }
                        tv_login.text = userDetailItem.login
                        tv_location.text = userDetailItem.location
                        tv_blog.text = userDetailItem.blog
                        view_badge.visibility =
                            if (userDetailItem.siteAdmin != null) View.VISIBLE else View.GONE

                        Glide.with(applicationContext)
                            .load(userDetailItem.avatarUrl)
                            .listener(object : RequestListener<Drawable> {
                                override fun onLoadFailed(
                                    e: GlideException?, model: Any,
                                    target: Target<Drawable>, isFirstResource: Boolean
                                ): Boolean {
                                    progressBar.visibility = View.GONE
                                    return false
                                }

                                override fun onResourceReady(
                                    resource: Drawable,
                                    model: Any,
                                    target: Target<Drawable>,
                                    dataSource: DataSource,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    progressBar.visibility = View.GONE
                                    return false
                                }
                            })
                            .into(img_avatar_url)
                    }
                }
            }
        }

    companion object {
        private const val TAG = "UserDetailActivity"
    }
}
