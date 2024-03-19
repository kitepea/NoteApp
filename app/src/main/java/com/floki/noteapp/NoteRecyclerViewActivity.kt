package com.floki.noteapp

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.floki.noteapp.databinding.ActivityAllNoteBinding
import com.google.android.material.snackbar.Snackbar
import java.util.UUID
import kotlin.LazyThreadSafetyMode.NONE


class NoteRecyclerViewActivity : AppCompatActivity() {
    private val binding by lazy(NONE) { ActivityAllNoteBinding.inflate(layoutInflater) }
    private val noteListAdapter by lazy(NONE) { NoteListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpRecyclerView()
        val notes = List(10) {
            Note(
                id = UUID.randomUUID().toString(),
                title = "$it title. ✍\uFE0F Youtube script ideas",
                content = "There are many apps in Android that can run or emulate other operating systems, via utilizing hardware support for platform... "
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
}