package com.example.intelligentcity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intelligentcity.adapters.NoteAdapter
import com.example.intelligentcity.entities.Note
import com.example.intelligentcity.viewModel.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SecondActivity : AppCompatActivity() {

    private lateinit var noteViewModel: NoteViewModel
    private val newWordActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // recycler view
        val recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view)
        val adapter = NoteAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // view model
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel.allNotes.observe(this, Observer { notes ->
            // Update the cached copy of the words in the adapter.
            notes?.let { adapter.setNotes(it) }
        })

        //Fab
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@SecondActivity, AddNote::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val ptitle = data?.getStringExtra(AddNote.EXTRA_REPLY_TITLE)
            val ptext = data?.getStringExtra(AddNote.EXTRA_REPLY_TEXT)

            if (ptitle!= null && ptext != null) {
                val note = Note(title = ptitle, text = ptext)
                noteViewModel.insert(note)
            }

        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_cont, menu)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {

        return when (item!!.itemId) {
            R.id.edit -> {


                Toast.makeText(this@SecondActivity, " Atualizado com sucesso! ", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.remove -> {
                //apagar da bd
                noteViewModel.deleteById(id = item.itemId)

                //refresh da lista
                Toast.makeText(this@SecondActivity, " Removido com sucesso! ", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}

