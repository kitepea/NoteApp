package com.floki.noteapp.Adapter

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.floki.noteapp.Model.Note
import com.floki.noteapp.R
import com.floki.noteapp.databinding.NoteItemLayoutBinding

object NoteDiffUtilItemCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        // compare by id
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        // compare by content -> ==
        return oldItem == newItem
    }
}

class NoteListAdapter() :
    ListAdapter<Note, NoteListAdapter.NoteViewHolder>(NoteDiffUtilItemCallback) {
    private var notes: List<Note> = emptyList()

    class NoteViewHolder(
        private val binding: NoteItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.run {
                tvTitle.text = note.title
                tvContent.text = note.note
                tvDate.text = note.date
                val drawable = ResourcesCompat.getDrawable(
                    itemView.resources,
                    R.drawable.curve,
                    null
                ) as GradientDrawable
                drawable.setColor(itemView.resources.getColor(note.color, null))
                noteLayout.background = drawable
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NoteViewHolder(binding)
    }


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}