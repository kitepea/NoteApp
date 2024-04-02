package com.floki.noteapp.Adapter

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
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

class NoteListAdapter(private val listener: NotesClickListener) :
    ListAdapter<Note, NoteListAdapter.NoteViewHolder>(NoteDiffUtilItemCallback) {
    private var notes: MutableList<Note> = mutableListOf()

    class NoteViewHolder(
        private val binding: NoteItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note, listener: NotesClickListener) {
            binding.run {
                tvTitle.text = note.title
                tvTitle.isSelected = true

                tvContent.text = note.note

                tvDate.text = note.date
                tvDate.isSelected = true

                val drawable = ResourcesCompat.getDrawable(
                    itemView.resources,
                    R.drawable.curve,
                    null
                ) as GradientDrawable
                drawable.setColor(itemView.resources.getColor(note.color, null))
                noteLayout.background = drawable

                noteLayout.setOnClickListener {
                    listener.onItemClicked(note)
                }

                noteLayout.setOnLongClickListener {
                    listener.onLongItemClicked(note, noteLayout)
                    true
                }
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
        holder.bind(getItem(position), listener)
    }

    interface NotesClickListener {
        fun onItemClicked(note: Note)
        fun onLongItemClicked(note: Note, noteLayout: ConstraintLayout)
    }
}