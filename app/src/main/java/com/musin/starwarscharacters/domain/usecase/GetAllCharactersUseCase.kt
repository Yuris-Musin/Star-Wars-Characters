package com.musin.starwarscharacters.domain.usecase

import com.musin.starwarscharacters.domain.repository.CharactersRepository
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {

    operator fun invoke() = charactersRepository.getAllCharacters()
}