package com.musin.starwarscharacters.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musin.starwarscharacters.domain.repository.CharactersRepository
import com.musin.starwarscharacters.domain.entity.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: CharactersRepository
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
                // First, check if we have cached data
                val cachedCharacters = repository.getAllCharacters().first()
                if (cachedCharacters.isNotEmpty()) {
                    _state.value = CharactersState.Success(cachedCharacters)
                } else {
                    // No cache, fetch from network
                    repository.fetchFromNetwork()
                    val updatedCharacters = repository.getAllCharacters().first()
                    if (updatedCharacters.isEmpty()) {
                        _state.value = CharactersState.Empty
                    } else {
                        _state.value = CharactersState.Success(updatedCharacters)
                    }
                }
            } catch (e: Exception) {
                _state.value = CharactersState.Error(e.message ?: "Unknown error")
            }
        }
    }
    
    fun retry() {
        loadCharacters()
    }
}

sealed class CharactersState {
    object Loading : CharactersState()
    object Empty : CharactersState()
    data class Success(val characters: List<Character>) : CharactersState()
    data class Error(val message: String) : CharactersState()
}