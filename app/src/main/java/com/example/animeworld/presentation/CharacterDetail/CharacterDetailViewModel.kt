package com.example.animeworld.presentation.CharacterDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeworld.domain.useCase.GetCharacterDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(private val getCharacterDetailUseCase: GetCharacterDetailUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow(CharacterDetailState())
    val state: StateFlow<CharacterDetailState> = _state


    fun loadCharacter(charcterId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            getCharacterDetailUseCase(charcterId)
                .catch { e ->
                    _state.value =
                        _state.value.copy(isLoading = false, error = e.message ?: "Unknown Error")
                }
                .collectLatest { character ->
                    _state.value = _state.value.copy(character = character, isLoading = false)
                }

        }
    }

}