package com.example.myapplicationfin

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplicationfin.databinding.ItemHomeBinding

class MyHomeViewHolder(val binding: ItemHomeBinding): RecyclerView.ViewHolder(binding.root)

class MyHomeAdapter(val context: Context, val datas: MutableList<ItemHomeModel>?): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun getItemCount(): Int{
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = MyHomeViewHolder(ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding=(holder as MyHomeViewHolder).binding

        //add......................................
        val model = datas!![position]
        binding.itemName.text = model.INST_NM
        binding.hosptlDiv.text = model.HOSPTL_DIV_NM
        binding.itemRoadnmAdd.text = model.REFINE_ROADNM_ADDR

    }
}