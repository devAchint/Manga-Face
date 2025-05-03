package com.achint.mangaface.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achint.mangaface.data.local.UserEntity
import com.achint.mangaface.domain.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val usersRepository: UsersRepository) :
    ViewModel() {

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            val isUserAlreadySignedIn = usersRepository.isUserAlreadyExists(email)
            if (isUserAlreadySignedIn) {
                usersRepository.signIn(email, password)
            } else {
                usersRepository.signUp(UserEntity(email = email, password = password))
            }
        }
    }

}