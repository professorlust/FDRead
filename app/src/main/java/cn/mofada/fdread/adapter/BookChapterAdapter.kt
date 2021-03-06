package cn.mofada.fdread.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.mofada.fdread.R
import cn.mofada.fdread.bean.Chapter
import org.litepal.crud.DataSupport

/**
 * Created by fada on 2017/6/11.
 * 小说章节适配器
 */
class BookChapterAdapter(var data: List<Chapter>) : RecyclerView.Adapter<BookChapterAdapter.ViewHolder>() {
    var mContext: Context? = null
    var listener: OnItemClickListener? = null
    var isOrder: Boolean = true

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        if (mContext == null) {
            mContext = parent?.context
        }
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.item_list_chapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val chapter: Chapter = data[position]
        holder?.title?.text = chapter.title
        val chapters: List<Chapter> = DataSupport.where("chapterId = '${chapter.chapterId}'").find(Chapter::class.java)
        if (chapters.isNotEmpty()) holder?.done?.visibility = View.VISIBLE
        if (holder != null) {
            setItemEvents(holder)
        }
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var title: TextView? = itemView?.findViewById(R.id.item_list_title)
        var done: ImageView? = itemView?.findViewById(R.id.item_list_done)
    }

    fun listener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun refresh(data: List<Chapter>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun reversed() {
        isOrder = !isOrder
        data = data.reversed()
        notifyDataSetChanged()
    }

    fun setItemEvents(holder: ViewHolder) {
        if (listener != null) {
            holder.itemView.setOnClickListener {
                val layoutPosition = holder.layoutPosition
                listener?.onItemClick(holder.itemView, layoutPosition)
            }
        }
    }
}