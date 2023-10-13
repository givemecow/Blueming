package com.example.myapplicationfin

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationfin.databinding.ItemQnaBinding


class MyQnaViewHolder(val binding: ItemQnaBinding) : RecyclerView.ViewHolder(binding.root)

class MyQnaAdapter(val context: Context, val itemList: MutableList<ItemQnaModel>): RecyclerView.Adapter<MyQnaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyQnaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyQnaViewHolder(ItemQnaBinding.inflate(layoutInflater))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MyQnaViewHolder, position: Int) {
        val data = itemList.get(position)

        holder.binding.run {
            itemEmailView.text=data.email
            itemDateView.text=data.date
            itemContentView.text=data.content
        }

        //스토리지 이미지 다운로드........................
        var imageRef = MyApplication.storage.reference.child("images/{${data.qid}}.jpg")
        imageRef.downloadUrl.addOnCompleteListener{task ->
            if(task.isSuccessful){
                // 다운로드 이미지를 ImageView에 보여줌
                // 스토리지에서 이미지를 가져오는 과정
                GlideApp.with(context)
                    .load(task.result)
                    .into(holder.binding.itemImageView)
            }
        }

    }
}
