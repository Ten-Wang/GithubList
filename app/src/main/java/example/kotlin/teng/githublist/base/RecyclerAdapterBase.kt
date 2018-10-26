package example.kotlin.teng.githublist.base

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import example.kotlin.teng.githublist.R

abstract class RecyclerAdapterBase<T> protected constructor(dataList: MutableList<T>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), LoadMoreExecutor, RecyclerAdapterHelper.CustomizeSizeDelegate {

    private val recyclerAdapterHelper: RecyclerAdapterHelper = RecyclerAdapterHelper(dataList, this)
    var headerView: View? = null
        set(headerView) {
            field = headerView
            recyclerAdapterHelper.setHasHeaderView(headerView != null)
        }
    private var footerView: View? = null
    protected var dataList: List<T> = dataList

    override val dataSize: Int
        get() = recyclerAdapterHelper.dataSize

    val isLoadMoreEnable: Boolean
        get() = recyclerAdapterHelper.isLoadMoreEnable

    override fun getItemViewType(adapterPosition: Int): Int {
        return if (recyclerAdapterHelper.isNormalItem(adapterPosition))
            getCustomItemViewType(recyclerAdapterHelper.calcDataItemPosition(adapterPosition))
        else
            recyclerAdapterHelper.getItemType(adapterPosition)
    }

    private fun getCustomItemViewType(position: Int): Int {
        return RecyclerAdapterHelper.VIEW_TYPE_NORMAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: ViewHolder
        if (recyclerAdapterHelper.isHeaderType(viewType)) {
            vh = onCreateHeaderViewHolder(LayoutInflater.from(parent.context), parent)
            vh.setIsRecyclable(false)
        } else if (recyclerAdapterHelper.isLoadMoreType(viewType)) {
            vh = onCreateLoadMoreViewHolder(LayoutInflater.from(parent.context), parent)
        } else if (recyclerAdapterHelper.isFooterType(viewType)) {
            vh = onCreateFooterViewHolder(LayoutInflater.from(parent.context), parent)
        } else {
            vh = onCreateItemViewHolder(LayoutInflater.from(parent.context), parent, viewType)
        }
        return vh
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (recyclerAdapterHelper.isNormalItem(position)) {
            onBindItemViewHolder(viewHolder, position - if (recyclerAdapterHelper.hasHeaderView()) 1 else 0)
        } else if (recyclerAdapterHelper.checkToBindLoadMore(position)) {
            onBindLoadMoreViewHolder(viewHolder, recyclerAdapterHelper.isLoadMoreFailed)
        } else if (recyclerAdapterHelper.isHeaderItem(position)) {
            onBindHeaderViewHolder(viewHolder)
        } else if (recyclerAdapterHelper.isFooterItem(position)) {
            onBindFooterViewHolder(viewHolder)
        }
    }

    override fun getItemCount(): Int {
        return recyclerAdapterHelper.adapterItemCount
    }

    override fun setLoadMoreEnable(isEnable: Boolean) {
        recyclerAdapterHelper.isLoadMoreEnable = isEnable
    }

    override fun setLoadMoreListener(loadMoreListener: LoadMoreExecutor.LoadMoreListener) {
        recyclerAdapterHelper.setLoadMoreListener(loadMoreListener)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        if (recyclerAdapterHelper.isLoadMoreItem(holder.adapterPosition)) {
            recyclerAdapterHelper.isLoadMoreFailed = false
        }
    }

    fun clearItemData() {
        recyclerAdapterHelper.clearData()
    }

    fun setFooterView(footerView: View?) {
        this.footerView = footerView
        recyclerAdapterHelper.setHasFooterView(footerView != null)
    }

    private fun onCreateHeaderViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder {
        return object : ViewHolder(this.headerView!!) {

        }
    }

    protected fun onCreateFooterViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder {
        return object : ViewHolder(footerView!!) {

        }
    }

    protected fun onBindLoadMoreViewHolder(viewHolder: RecyclerView.ViewHolder, isLoadMoreFailed: Boolean) {}

    protected fun onBindHeaderViewHolder(viewHolder: RecyclerView.ViewHolder) {
        if (this.headerView!!.parent != null) {
            (this.headerView!!.parent as ViewGroup).removeView(this.headerView)
        }
    }

    protected fun onBindFooterViewHolder(viewHolder: RecyclerView.ViewHolder) {
        if (footerView!!.parent != null) {
            (footerView!!.parent as ViewGroup).removeView(footerView)
        }
    }

    fun hasHeaderView(): Boolean {
        return recyclerAdapterHelper.hasHeaderView()
    }

    fun hasFooterView(): Boolean {
        return recyclerAdapterHelper.hasFooterView()
    }

    fun isHeaderViewHolder(vh: ViewHolder): Boolean {
        return recyclerAdapterHelper.isHeaderType(vh.itemViewType)
    }

    private fun onCreateLoadMoreViewHolder(from: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder {
        return object :
            ViewHolder(LayoutInflater.from(parent.context).inflate(DEFAULT_LOAD_MORE_LAYOUT, parent, false)) {

        }
    }

    protected abstract fun onCreateItemViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder

    protected abstract fun onBindItemViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int)

    protected fun getItemPosition(viewHolder: ViewHolder): Int {
        return viewHolder.layoutPosition - if (hasHeaderView()) 1 else 0
    }

    fun notifyDataChanged(dataPosition: Int) {
        notifyItemChanged(dataPosition + if (hasHeaderView()) 1 else 0)
    }

    fun notifyDataRemoved(dataPosition: Int) {
        notifyItemRemoved(dataPosition + if (hasHeaderView()) 1 else 0)
    }

    fun onLoadMoreFailed() {
        if (recyclerAdapterHelper.isLoadMoreEnable) {
            recyclerAdapterHelper.isLoadMoreFailed = true
            val itemCount = itemCount - if (recyclerAdapterHelper.hasHeaderView()) 1 else 0
            notifyItemChanged(if (itemCount > 0) itemCount - 1 else 0)
        }
    }

    fun notifyLoadMoreChanged(hasNextPage: Boolean, newItemSize: Int) {
        if (!hasNextPage && newItemSize == 0 && !hasFooterView()) {
            notifyItemRemoved(itemCount - 1)
        } else {
            notifyItemRangeChanged(itemCount - 1, if (hasNextPage) newItemSize else 1)
        }
    }


    companion object {

        private val DEFAULT_LOAD_MORE_LAYOUT = R.layout.item_loadmore_default
    }

}
