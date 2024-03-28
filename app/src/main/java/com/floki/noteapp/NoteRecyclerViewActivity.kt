package com.floki.noteapp

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.floki.noteapp.Adapter.NoteListAdapter
import com.floki.noteapp.Model.Note
import com.floki.noteapp.databinding.ActivityAllNoteBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.LazyThreadSafetyMode.NONE


class NoteRecyclerViewActivity : AppCompatActivity(), NoteListAdapter.NotesClickListener {
    private val binding by lazy(NONE) { ActivityAllNoteBinding.inflate(layoutInflater) }
    private val noteListAdapter by lazy(NONE) { NoteListAdapter(this) }
    // Database, view model, note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpRecyclerView()
        var nextId = 1
        val notes = List(10) {
            Note(
                id = nextId++,
                title = "$it title. ✍\uFE0F Youtube script ideas",
                note = "There are many apps in Android that can run or emulate other operating systems, via utilizing hardware support for platform... ",
                date = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(Date())
            )
        }
        noteListAdapter.submitList(notes) {
            println(">>> submitList Done")
        }

        binding.button2.setOnClickListener { view ->
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpRecyclerView() {
        binding.recyclerViewNote.run {
            setHasFixedSize(true) //optimization
            layoutManager = LinearLayoutManager(context)
            adapter = noteListAdapter
            val mDividerItemDecoration = DividerItemDecoration(
                context,
                (layoutManager as LinearLayoutManager).orientation
            )

            val dividerDrawable = GradientDrawable()
            dividerDrawable.setSize(10, 25) // Set the width of the divider

            mDividerItemDecoration.setDrawable(dividerDrawable)
            addItemDecoration(mDividerItemDecoration)
        }

    }

    override fun onItemClicked(note: Note) {
        TODO("Not yet implemented")
    }

    override fun onLongItemClicked(note: Note, noteLayout: ConstraintLayout) {
        TODO("Not yet implemented")
    }
}