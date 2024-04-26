package com.floki.noteapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.floki.noteapp.Model.Note
import com.floki.noteapp.databinding.ActivityAddNoteBinding
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var firebaseAuth: FirebaseAuth


    // For note updating
    private lateinit var note: Note
    private lateinit var oldNote: Note

    // Track if note is updated or new note is created
    private var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        try {
            oldNote = (intent.getParcelableExtra("current_note") as? Note)!!
            binding.eTTitle.setText(oldNote.title)
            binding.eTNote.setText(oldNote.note)
            isUpdate = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.saveButton.setOnClickListener {
            val title = "✍\uFE0F " + binding.eTTitle.text.toString()
            val content = binding.eTNote.text.toString()

            if (title.isNotEmpty() || content.isNotEmpty()) {
                val formatter = SimpleDateFormat("EEE, MMMM dd yyyy HH:mm:ss")

                val user = firebaseAuth.currentUser
                val userId = user?.uid

                note = if (isUpdate) {
                    // Read
                    Note(oldNote.id, title, userId, content, "(Edited) ${formatter.format(Date())}")
                } else {
                    Note(null, title, userId, content, formatter.format(Date()))
                }

                val intent = Intent()
                intent.putExtra("note", note)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(
                    this@AddNoteActivity,
                    "Please enter title and note",
                    Toast.LENGTH_SHORT
                ).show()
                // Read
                return@setOnClickListener
            }
        }
    }
}