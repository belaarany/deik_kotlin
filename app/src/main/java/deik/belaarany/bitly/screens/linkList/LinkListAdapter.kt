package deik.belaarany.bitly.screens.linkList

import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import deik.belaarany.bitly.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class LinkListAdapter(private val mDataSet: ArrayList<String>) : RecyclerView.Adapter<LinkListAdapter.ViewHolder>() {
    private var size = 0

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val textView: TextView

        init {
            v.setOnClickListener { Log.d(TAG, "Element $adapterPosition clicked.") }
            textView = v.findViewById<View>(R.id.linkName) as TextView
        }
    }

    fun refreshData(add: String?) {
        if (!TextUtils.isEmpty(add)) {
            if (add != null) {
                mDataSet!!.add(add)
            }
            size = mDataSet.size

            //notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view.
        val v: View = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.fragment_links_item, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Log.d(TAG, "Element $position set.")
        viewHolder.textView.text = mDataSet!![position]
    }

    override fun getItemCount(): Int {
        return size
    }

    companion object {
        private const val TAG = "CustomAdapter"
    }

    init {
        if (mDataSet != null && !mDataSet.isEmpty()) {
            size = mDataSet.size
        }
    }
}