package example.kotlin.teng.githublist.ui.base

interface LoadMoreExecutor {
    fun setLoadMoreEnable(isEnable: Boolean)

    fun setLoadMoreListener(loadMoreListener:  LoadMoreListener)

    interface LoadMoreListener {
        fun onLoadMore()
    }
}

