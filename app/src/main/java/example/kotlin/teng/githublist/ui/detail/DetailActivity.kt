package example.kotlin.teng.githublist.ui.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.util.Linkify
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import example.kotlin.teng.githublist.databinding.UsersDetailActivityBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModel()
    private lateinit var binding: UsersDetailActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = UsersDetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        detailViewModel.userDetail.observe(this) {
            if (it != null) {
                binding.tvName.text = it.name
                if (it.bio != null) {
                    binding.textViewBio.text = it.bio.toString()
                }
                binding.tvLogin.text = it.login
                binding.tvLocation.text = it.location
                binding.tvBlog.text = it.blog
                binding.viewBadge.visibility =
                    if (it.siteAdmin != null) View.VISIBLE else View.GONE

                Glide.with(applicationContext)
                    .load(it.avatarUrl)
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

        binding.tvBlog.autoLinkMask = Linkify.ALL

        val login = intent.getStringExtra("login")
        if (login != null) {
            detailViewModel.getUserDetail(login)
        }

        binding.btnClose.setOnClickListener { finish() }
    }
}
