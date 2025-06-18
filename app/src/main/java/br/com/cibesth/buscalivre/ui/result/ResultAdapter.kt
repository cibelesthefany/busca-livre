package br.com.cibesth.buscalivre.ui.result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.cibesth.buscalivre.R
import br.com.cibesth.buscalivre.data.model.Item
import br.com.cibesth.buscalivre.utils.fixPrice
import br.com.cibesth.buscalivre.utils.toBrazilianCurrency
import com.bumptech.glide.Glide

class ResultsAdapter(
private val onItemClick: (Item) -> Unit
) : RecyclerView.Adapter<ResultsAdapter.ViewHolder>() {

    private val items = mutableListOf<Item>()

    fun submitList(newItems: List<Item>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvPrice = itemView.findViewById<TextView>(R.id.tvPrice)
        private val ivImage = itemView.findViewById<ImageView>(R.id.ivImage)

        fun bind(item: Item) {
            tvTitle.text = item.title
            tvPrice.text = item.price.fixPrice().toBrazilianCurrency()


            val imageUrl = item.pictures?.firstOrNull()?.secure_url
                ?: item.pictures?.firstOrNull()?.url
                ?: item.thumbnail


            Glide.with(itemView.context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(ivImage)

            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
