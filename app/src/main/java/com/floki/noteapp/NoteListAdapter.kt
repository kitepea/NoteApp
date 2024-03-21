package com.floki.noteapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.floki.noteapp.Model.Note
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

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}