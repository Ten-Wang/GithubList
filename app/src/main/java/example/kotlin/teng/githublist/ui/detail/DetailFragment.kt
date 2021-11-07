package example.kotlin.teng.githublist.ui.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import example.kotlin.teng.githublist.R
import example.kotlin.teng.githublist.databinding.ActivityUsersDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BottomSheetDialogFragment() {

    private val detailViewModel: DetailViewModel by viewModel()
    private lateinit var binding: ActivityUsersDetailBinding

    companion object {
        fun newInstance(login: String): DetailFragment {
            val args = Bundle()
            args.putString("login", login)
            val fragment = DetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityUsersDetailBinding.inflate(layoutInflater)
        detailViewModel.userDetail.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    tvName.text = it.name
                    if (it.bio != null) {
                        tvBio.text = it.bio.toString()
                    }
                    tvLogin.text = it.login
                    tvLocation.text = it.location
                    tvBlog.text = it.blog
                    icBadge.visibility =
                        if (it.siteAdmin != null) View.VISIBLE else View.GONE

                    Glide.with(root.context)
                        .load(it.avatarUrl)
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
                        .into(imgAvatarUrl)
                }
            }
        }

        binding.apply {
            tvBlog.autoLinkMask = Linkify.ALL
            btnClose.setOnClickListener { closeFragment() }
        }

        requireArguments().getString("login")?.apply {
            detailViewModel.getUserDetail(this)
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setStyle(STYLE_NORMAL, R.style.DetailBottomSheetStyle)
    }

    private fun closeFragment() {
        val manager = parentFragmentManager
        val trans = manager.beginTransaction()
        trans.remove(this)
        trans.commit()
        manager.popBackStack()
    }

}