package com.trongdeptrai.roomwithview.data

import androidx.lifecycle.LiveData
import com.trongdeptrai.roomwithview.modal.Word

class WordRepository(private val wordDao: WordDAO) {
    val allWords: LiveData<List<Word>> = wordDao.getAlphabetizedWords()

    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}