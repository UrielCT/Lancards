package com.example.idioms.domain.usecases.words

import com.example.idioms.domain.models.Word
import com.example.idioms.domain.repository.WordRepository
import javax.inject.Inject

class UpdateWordUseCase @Inject constructor(private val wordRepository: WordRepository) {
    suspend operator fun invoke(word: Word) = wordRepository.update(word)
}