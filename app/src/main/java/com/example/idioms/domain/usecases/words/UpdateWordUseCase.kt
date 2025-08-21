package com.example.idioms.domain.usecases.words

import com.example.idioms.data.repository.WordRepository
import com.example.idioms.ui.models.Word
import javax.inject.Inject

class UpdateWordUseCase @Inject constructor(private val wordRepository: WordRepository) {
    suspend operator fun invoke(word: Word){
        wordRepository.update(word)
    }
}