package com.example.idioms.domain.usecases.words

import com.example.idioms.data.repository.WordRepository
import com.example.idioms.ui.models.Word
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWordsUseCase @Inject constructor(private val wordRepository: WordRepository){
    operator fun invoke(): Flow<List<Word>> {
        return wordRepository.words
    }
}