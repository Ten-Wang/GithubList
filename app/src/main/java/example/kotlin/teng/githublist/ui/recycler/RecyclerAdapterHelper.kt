package example.kotlin.teng.githublist.ui.recycler

class RecyclerAdapterHelper(
    private val dataList: MutableList<*>,
    private val sizeDelegate: CustomizeSizeDelegate
) {

    var isLoadMoreEnable: Boolean = false
    var isLoadMoreFailed: Boolean = false
    private var hasHeaderView: Boolean = false
    private var hasFooterView: Boolean = false
    private lateinit var loadMoreListener: LoadMoreExecutor.LoadMoreListener

    val adapterItemCount: Int
        get() {
            val dataSize = sizeDelegate.dataSize
            return if (dataSize == 0) if (hasHeaderView) 1 else 0 else dataSize + (if (isLoadMoreEnable || hasFooterView) 1 else 0) + if (hasHeaderView) 1 else 0
        }

    val dataSize: Int
        get() = dataList.size

    fun setLoadMoreListener(loadMoreListener: LoadMoreExecutor.LoadMoreListener) {
        this.loadMoreListener = loadMoreListener
    }

    fun setHasHeaderView(hasHeaderView: Boolean) {
        this.hasHeaderView = hasHeaderView
    }

    fun setHasFooterView(hasFooterView: Boolean) {
        this.hasFooterView = hasFooterView
    }

    fun calcDataItemPosition(adapterPosition: Int): Int {
        return adapterPosition - if (hasHeaderView) 1 else 0
    }

    fun getItemType(position: Int): Int {
        return when {
            isHeaderItem(position) -> VIEW_TYPE_HEADER
            isLoadMoreItem(position) -> VIEW_TYPE_LOAD_MORE
            isFooterItem(position) -> VIEW_TYPE_FOOTER
            else -> VIEW_TYPE_NORMAL
        }
    }

    fun checkToBindLoadMore(position: Int): Boolean {
        if (isLoadMoreItem(position)) {
            if (!isLoadMoreFailed) {
                loadMoreListener.onLoadMore()
            }
            return true
        }
        return false
    }

    fun hasHeaderView(): Boolean {
        return hasHeaderView
    }

    fun hasFooterView(): Boolean {
        return hasFooterView
    }

    fun isNormalItem(position: Int): Boolean {
        return !isHeaderItem(position) && !isFooterItem(position) && !isLoadMoreItem(position)
    }

    fun isHeaderItem(position: Int): Boolean {
        return hasHeaderView && position == 0
    }

    fun isFooterItem(position: Int): Boolean {
        return hasFooterView && position == adapterItemCount - 1
    }

    fun isLoadMoreItem(position: Int): Boolean {
        return isLoadMoreEnable && position == adapterItemCount - 1
    }

    fun isLoadMoreType(viewType: Int): Boolean {
        return viewType == VIEW_TYPE_LOAD_MORE
    }

    fun isHeaderType(viewType: Int): Boolean {
        return viewType == VIEW_TYPE_HEADER
    }

    fun isFooterType(viewType: Int): Boolean {
        return viewType == VIEW_TYPE_FOOTER
    }

    fun clearData() {
        dataList.clear()
    }

    interface CustomizeSizeDelegate {
        val dataSize: Int
    }

    companion object {
        const val VIEW_TYPE_NORMAL = Integer.MAX_VALUE
        private const val VIEW_TYPE_LOAD_MORE = Integer.MAX_VALUE - 10
        const val VIEW_TYPE_HEADER = Integer.MAX_VALUE - 12
        private const val VIEW_TYPE_FOOTER = Integer.MAX_VALUE - 14
    }
}
