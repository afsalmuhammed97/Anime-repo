package com.dev.jikanapp.ui.homelisting

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.jikanapp.databinding.ListingItemBinding
import com.dev.jikanapp.model.Data
import com.dev.jikanapp.uttil.loadImage

class ListingAdapter(private val onItemClicked: (albumId: Int) -> Unit) :
    RecyclerView.Adapter<ListingAdapter.ItemHolder>() {


    private val diffCallBack = object : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.mal_id == newItem.mal_id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            ListingItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        //  holder.binding.txetTittle.text=list[position]

        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size


    inner class ItemHolder(val binding: ListingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.outerLay.setOnClickListener {
                val id = differ.currentList[adapterPosition].mal_id
                onItemClicked(id)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(anime: Data) {

            binding.apply {
                this.txetTittle.text = anime.title
                this.textNumberOfEpisodes.text = "${anime.episodes.toString()} Episodes"
                this.textRating.text = "Rating : ${anime.rating}"
                this.posterImg.loadImage(anime.images.jpg.large_image_url)
            }
        }
    }
}