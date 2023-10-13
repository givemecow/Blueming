package com.example.myapplicationfin

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationfin.databinding.ItemRecyclerviewBinding

class MyViewHolder(val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root)

class MyAdapter(val datas: MutableList<String>?): RecyclerView.Adapter<MyViewHolder>(){
    private var textColor: String = "#000000"
    lateinit var context: Context

    override fun getItemCount(): Int{
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder{
        context = parent.context
        val binding = ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
//            = MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val binding=(holder as MyViewHolder).binding
        val data = datas?.get(position)

        binding.apply {
            itemData.text = data
            itemData.setTextColor(Color.parseColor(textColor))
        }
//        var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
//        val txColor:String? = sharedPreferences.getString("tx_color", "")
//        binding.itemData.setTextColor(Color.parseColor(textColor))
        //binding.itemData.setTextColor(Color.parseColor(txColor))  // 주석 처리 후 실행
    }

    fun setTextColor(color: String) {
        textColor = color
        notifyDataSetChanged()
    }
}
