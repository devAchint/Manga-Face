package com.achint.mangaface.ui.screens.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achint.mangaface.domain.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(private val usersRepository: UsersRepository) :
    ViewModel() {

    private val _isUserSignedIn = MutableStateFlow<Boolean?>(null)
    val isUserSignedIn = _isUserSignedIn.asStateFlow()

    init {
        viewModelScope.launch {
            val isSignedIn = usersRepository.isUserSignedIn()
            _isUserSignedIn.update {
                isSignedIn
            }
        }
    }


}