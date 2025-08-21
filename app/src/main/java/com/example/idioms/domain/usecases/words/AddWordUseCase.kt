package com.example.idioms.domain.usecases.words

import com.example.idioms.data.repository.WordRepository
import com.example.idioms.ui.models.Word
import javax.inject.Inject

class AddWordUseCase @Inject constructor(private val wordRepository: WordRepository) {
    suspend operator fun invoke(word: Word){
        wordRepository.add(word)
    }
}