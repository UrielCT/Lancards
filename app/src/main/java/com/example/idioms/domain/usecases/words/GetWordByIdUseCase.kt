package com.example.idioms.domain.usecases.words

import com.example.idioms.domain.models.Word
import com.example.idioms.domain.repository.WordRepository
import javax.inject.Inject

class GetWordByIdUseCase @Inject constructor(private val wordRepository: WordRepository){
    operator fun invoke(id: Int): Word? {
        return wordRepository.getWordById(id)
    }
}