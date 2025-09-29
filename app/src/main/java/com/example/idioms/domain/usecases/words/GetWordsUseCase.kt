package com.example.idioms.domain.usecases.words

import com.example.idioms.domain.models.Word
import com.example.idioms.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWordsUseCase @Inject constructor(private val wordRepository: WordRepository){
    operator fun invoke(): Flow<List<Word>> = wordRepository.getWords()
}