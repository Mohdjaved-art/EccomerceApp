package com.stdev.shopit.presentation.adapter

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stdev.shopit.data.model.Product
import com.stdev.shopit.data.model.Products
import com.stdev.shopit.databinding.ProductRvItemBinding
import com.stdev.shopit.databinding.SingleItemBinding


class BestProductsAdapter : RecyclerView.Adapter<BestProductsAdapter.BestProductsViewHolder>() {

    inner class BestProductsViewHolder(private val binding: ProductRvItemBinding) : // design item part
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Products) {
            binding.apply {
               // val priceAfterOffer = product.offerPercentage.getProductPrice(product.price)


                tvNewPrice.text = "$ ${String.format("%.2f", )}"


                tvPrice.paintFlags = tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG


             //   if (product.offerPercentage == null)


                    tvNewPrice.visibility = View.INVISIBLE

                Glide.with(itemView).load(product.image[0]).into(imgProduct)


                tvPrice.text = "$ ${product.price}"


                tvName.text = product.description
            }

        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Products>() {
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem == newItem
        }

       
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestProductsViewHolder {
        return BestProductsViewHolder(
            ProductRvItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: BestProductsViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)

        holder.itemView.setOnClickListener {
            onClick?.invoke(product)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onClick: ((Product) -> Unit)? = null

}

private fun Any?.invoke(product: Products?) {

}
