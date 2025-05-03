package com.achint.mangaface.ui.screens.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achint.mangaface.data.local.UserEntity
import com.achint.mangaface.domain.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val msg: String? = null,
    val isValidEmail: Boolean = false,
    val isValidPassword: Boolean = false,
    val isLoading: Boolean = false,
    val signInSuccess: Boolean = false
)

@HiltViewModel
class AuthViewModel @Inject constructor(private val usersRepository: UsersRepository) :
    ViewModel() {

    private val _authUiState = MutableStateFlow(AuthUiState())
    val authUiState = _authUiState.asStateFlow()

    init {
        authUiState.distinctUntilChangedBy { it.email }.map {
            it.email.isNotBlank() && it.email.contains("@") && it.email.contains(".")
        }.onEach { isValidEmail ->
            _authUiState.update { it.copy(isValidEmail = isValidEmail) }
        }.launchIn(viewModelScope)

        authUiState.distinctUntilChangedBy { it.password }.map {
            it.password.isNotBlank() && it.password.length >= 6
        }.onEach { isValidPassword ->
            _authUiState.update { it.copy(isValidPassword = isValidPassword) }
        }.launchIn(viewModelScope)
    }

    fun setEmail(email: String) {
        _authUiState.update { it.copy(email = email) }
    }

    fun setPassword(password: String) {
        _authUiState.update { it.copy(password = password) }
    }

    fun resetMsg(){
        _authUiState.update { it.copy(msg = null) }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authUiState.update { it.copy(isLoading = true) }
            val isUserAlreadySignedIn = usersRepository.isUserAlreadyExists(email)
            if (isUserAlreadySignedIn) {
                val isSignIn = usersRepository.signIn(email, password)
                if (!isSignIn) {
                    _authUiState.update {
                        it.copy(
                            isLoading = false,
                            signInSuccess = false,
                            msg = "Invalid credentials"
                        )
                    }
                    return@launch
                }
            } else {
                usersRepository.signUp(UserEntity(email = email, password = password))
            }
            _authUiState.update { it.copy(isLoading = false, signInSuccess = true, msg = "Sign In Success") }
        }
    }


}