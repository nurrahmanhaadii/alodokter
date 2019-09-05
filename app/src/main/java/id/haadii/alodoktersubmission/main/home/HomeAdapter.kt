package id.haadii.alodoktersubmission.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.haadii.alodoktersubmission.R
import id.haadii.alodoktersubmission.data.Items
import kotlinx.android.synthetic.main.item_home.view.*

class HomeAdapter( private val items: ArrayList<Items>, private val listener: (Items) -> Unit) : RecyclerView.Adapter<HomeAdapter.HomeHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
        return HomeHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        holder.bindItem(items[position], listener)
    }

    inner class HomeHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ivImage = view.iv_home

        fun bindItem(item: Items, listener: (Items) -> Unit) {

            Glide.with(itemView.context)
                .load(item.uri)
                .into(ivImage)

            itemView.setOnClickListener {
                listener(item)
            }
        }
    }
}