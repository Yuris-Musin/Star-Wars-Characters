package com.musin.starwarscharacters.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musin.starwarscharacters.domain.usecase.GetAllCharactersUseCase
import com.musin.starwarscharacters.domain.entity.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CharactersState>(CharactersState.Loading)
    val state: StateFlow<CharactersState> = _state.asStateFlow()

    init {
        loadCharacters()
    }

    private fun loadCharacters() {
        viewModelScope.launch {
            _state.value = CharactersState.Loading
            try {
                getAllCharactersUseCase()
                    .collect { characters ->
                        _state.value = CharactersState.Success(characters)
                    }
            } catch (e: Exception) {
                _state.value = CharactersState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class CharactersState {
    object Loading : CharactersState()
    data class Success(val characters: List<Character>) : CharactersState()
    data class Error(val message: String) : CharactersState()
}