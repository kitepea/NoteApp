package com.floki.noteapp

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.floki.noteapp.Adapter.NoteListAdapter
import com.floki.noteapp.Database.NoteDatabase
import com.floki.noteapp.Model.Note
import com.floki.noteapp.Model.NoteViewModel
import com.floki.noteapp.databinding.ActivityAllNoteBinding
import kotlin.LazyThreadSafetyMode.NONE


class NoteRecyclerViewActivity : AppCompatActivity(), NoteListAdapter.NotesClickListener,
    PopupMenu.OnMenuItemClickListener {
    private val binding by lazy(NONE) { ActivityAllNoteBinding.inflate(layoutInflater) }
    private val noteListAdapter by lazy(NONE) { NoteListAdapter(this) }
    private val database: NoteDatabase by lazy { NoteDatabase.getDatabase(this) }

    private val noteViewModel: NoteViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }
    private lateinit var selectedNote: Note

    private val updateNote =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val note = result.data?.getParcelableExtra("note") as? Note
                if (note != null) {
                    noteViewModel.updateNote(note)
                }
            }
        }
    // Database, view model, note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpRecyclerView()

        /*
        var nextId = 1
        val notes = List(10) {
            Note(
                id = nextId++,
                title = "$it title. ✍\uFE0F Youtube script ideas",
                note = "There are many apps in Android that can run or emulate other operating systems, via utilizing hardware support for platform... ",
                date = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(Date())
            )
        }
        */

        noteViewModel.allNotes.observe(this) { list ->
            list?.let {
                noteListAdapter.submitList(list) {
                    Log.d("Submit", "submitList Done")
                }
                if (list.isEmpty()) {
                    binding.noNoteView.visibility = View.VISIBLE
                } else {
                    binding.noNoteView.visibility = View.INVISIBLE
                }
            }
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
        val getContent =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val note = result.data?.getParcelableExtra("note") as? Note
                    if (note != null) {
                        noteViewModel.insertNote(note)
                    }
                }
            }

        binding.buttonAddNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            getContent.launch(intent)
        }

    }

    override fun onItemClicked(note: Note) {
        val intent = Intent(this, AddNoteActivity::class.java)
        intent.putExtra("current_note", note)
        updateNote.launch(intent)
    }

    override fun onLongItemClicked(note: Note, noteLayout: ConstraintLayout) {
        selectedNote = note
        popUpDisplay(noteLayout)
    }

    private fun popUpDisplay(noteLayout: ConstraintLayout) {
        val popUp = PopupMenu(this, noteLayout)
        popUp.setOnMenuItemClickListener(this@NoteRecyclerViewActivity)
        popUp.inflate(R.menu.pop_up_menu)
        popUp.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete_note) {
            noteViewModel.deleteNode(selectedNote)
            return true
        }
        return false
    }
}