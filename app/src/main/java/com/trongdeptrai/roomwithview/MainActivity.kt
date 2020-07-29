package com.trongdeptrai.roomwithview

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.trongdeptrai.roomwithview.data.adapter.WordListAdapter
import com.trongdeptrai.roomwithview.data.viewmodel.WordViewModel
import com.trongdeptrai.roomwithview.modal.Word
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var wordViewModel: WordViewModel
    private lateinit var adapter: WordListAdapter
    private val newWordActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)
        wordViewModel.allWords.observe(this, Observer { words -> words?.let { adapter.setWords(it) }})
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let {
                val word = Word(it)
                wordViewModel.insert(word)
            }
        }else {
            Toast.makeText(this, R.string.empty_not_saved, Toast.LENGTH_LONG).show()
        }
    }

    fun initView() {
        adapter = WordListAdapter(this)
        recyclerView.adapter = adapter
        fab.setOnClickListener{
            val intent = Intent(this, NewWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
    }
}