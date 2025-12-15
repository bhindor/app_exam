package com.example.diary.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.databinding.DiaryListBinding
import com.example.diary.model.Diary

class DiaryRecyclerAdapter(
    private val onItemClick: (Diary) -> Unit
) : RecyclerView.Adapter<DiaryRecyclerAdapter.DiaryViewHolder>() {

    private var diaryList: List<Diary> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DiaryViewHolder {
        val binding = DiaryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DiaryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiaryRecyclerAdapter.DiaryViewHolder, position: Int) {
        val diary = diaryList[position]
        holder.bind(diary)

        holder.itemView.setOnClickListener {
            onItemClick(diary)
        }
    }

    override fun getItemCount(): Int {
        return diaryList.size
    }

    fun setList(list: List<Diary>) {
        this.diaryList = list
        notifyDataSetChanged()
    }

    class DiaryViewHolder(val binding: DiaryListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(diary: Diary) {
            binding.tvTitle.text = diary.title
        }
    }



}