package com.achint.mangaface.ui.screens.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achint.mangaface.domain.repository.UsersRepository
import com.achint.mangaface.utils.isValidEmail
import com.achint.mangaface.utils.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SignInStates {
    InvalidCredentials, Success, Fail
}

data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val isValidEmail: Boolean? = null,
    val isValidPassword: Boolean? = null,
    val isLoading: Boolean = false,
    val signInState: SignInStates? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(private val usersRepository: UsersRepository) :
    ViewModel() {

    private val _authUiState = MutableStateFlow(AuthUiState())
    val authUiState = _authUiState.asStateFlow()

    init {
        viewModelScope.launch {
            val isSignedIn = usersRepository.isUserSignedIn()
            _authUiState.update {
                it.copy(
                    signInState = if (isSignedIn) SignInStates.Success else null
                )
            }
        }
    }

    fun setEmail(email: String) {
        _authUiState.update {
            it.copy(
                email = email
            )
        }
    }

    fun setPassword(password: String) {
        _authUiState.update {
            it.copy(
                password = password
            )
        }
    }


    fun authenticate() {
        viewModelScope.launch {
            _authUiState.update {
                it.copy(
                    isValidEmail = authUiState.value.email.isValidEmail(),
                    isValidPassword = authUiState.value.password.isValidPassword()
                )
            }
            if (authUiState.value.isValidEmail == true && authUiState.value.isValidPassword == true) {
                _authUiState.update { it.copy(isLoading = true) }
                delay(5000)
                val isUserAlreadyExists =
                    usersRepository.isUserAlreadyExists(authUiState.value.email)
                if (isUserAlreadyExists) {
                    signIn()
                } else {
                    signUp()
                }
            }
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            usersRepository.signUp(
                email = authUiState.value.email,
                password = authUiState.value.password
            )
            _authUiState.value = _authUiState.value.copy(
                isLoading = false,
                signInState = SignInStates.Success
            )
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            val isSignIn = usersRepository.signIn(
                email = authUiState.value.email,
                password = authUiState.value.password
            )
            if (isSignIn) {
                _authUiState.update {
                    it.copy(
                        isLoading = false,
                        signInState = SignInStates.Success
                    )
                }
            } else {
                _authUiState.update {
                    it.copy(
                        isLoading = false,
                        signInState = SignInStates.InvalidCredentials
                    )
                }
            }
        }
    }


}