package com.sanjay.helyxonandroidassessment.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.sanjay.helyxonandroidassessment.Models.AllProductsList
import com.sanjay.helyxonandroidassessment.R
import com.squareup.picasso.Picasso

class AllProductsAdapter(var itemList: ArrayList<AllProductsList>):RecyclerView.Adapter<AllProductsAdapter.Viewholder>() {

    private var mOnItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(itemLists: AllProductsList, position: Int)
    }
    fun setOnClickListeners(onItemClick: OnItemClickListener) {
        mOnItemClickListener = onItemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.all_products_list_adapter, parent, false)
        return Viewholder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = itemList[position]
        Picasso.get().load(item.image).into(holder.productImgIV)
        holder.productNameTV.text = item.title
        holder.productPriceTV.text = "₹ ${item.price}"
        holder.productCL.setOnClickListener {
            mOnItemClickListener?.onItemClick(item,position)
        }
    }

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val productImgIV = itemView.findViewById<ImageView>(R.id.productImgIV)
        val productNameTV = itemView.findViewById<TextView>(R.id.productNameTV)
        val productPriceTV = itemView.findViewById<TextView>(R.id.productPriceTV)
        val productCL = itemView.findViewById<ConstraintLayout>(R.id.productCL)

    }

}