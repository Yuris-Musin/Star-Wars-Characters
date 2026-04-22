package com.musin.starwarscharacters.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musin.starwarscharacters.domain.repository.CharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val repository: CharactersRepository
) : ViewModel() {

    private val _state = MutableStateFlow<CharacterDetailState>(CharacterDetailState.Loading)
    val state: StateFlow<CharacterDetailState> = _state.asStateFlow()

    fun loadCharacter(characterId: Int) {
        viewModelScope.launch {
            _state.value = CharacterDetailState.Loading
            try {
                val character = repository.getCharacterById(characterId)
                _state.value = CharacterDetailState.Success(character)
            } catch (e: Exception) {
                _state.value = CharacterDetailState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
