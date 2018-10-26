package example.kotlin.teng.githublist.base

class RecyclerAdapterHelper(private val dataList: MutableList<*>, private val sizeDelegate: CustomizeSizeDelegate) {

    var isLoadMoreEnable = false
    var isLoadMoreFailed = false
    private var hasHeaderView = false
    private var hasFooterView = false
    private lateinit var loadMoreListener: LoadMoreExecutor.LoadMoreListener

    val adapterItemCount: Int
        get() {
            val dataSize = sizeDelegate.dataSize
            return if (dataSize == 0) if (hasHeaderView) 1 else 0 else dataSize + (if (isLoadMoreEnable || hasFooterView) 1 else 0) + if (hasHeaderView) 1 else 0
        }

    val dataSize: Int
        get() = dataList.size

    fun setLoadMoreListener(loadMoreListener: LoadMoreExecutor.LoadMoreListener) {
        this.setLoadMoreListener(loadMoreListener)
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
        val itemType: Int
        if (isHeaderItem(position)) {
            itemType = VIEW_TYPE_HEADER
        } else if (isLoadMoreItem(position)) {
            itemType = VIEW_TYPE_LOAD_MORE
        } else if (isFooterItem(position)) {
            itemType = VIEW_TYPE_FOOTER
        } else {
            itemType = VIEW_TYPE_NORMAL
        }
        return itemType
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

        val VIEW_TYPE_NORMAL = Integer.MAX_VALUE
        private val VIEW_TYPE_LOAD_MORE = Integer.MAX_VALUE - 10
        val VIEW_TYPE_HEADER = Integer.MAX_VALUE - 12
        private val VIEW_TYPE_FOOTER = Integer.MAX_VALUE - 14
    }
}
