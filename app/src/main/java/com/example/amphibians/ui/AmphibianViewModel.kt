package com.example.amphibians

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibians.data.AmphibianRepository
import com.example.amphibians.model.Amphibian
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface AppUiState {
    data class Success(val amphibianList: List<Amphibian>) : AppUiState
    object Error: AppUiState
    object Loading: AppUiState
}

class AmphibianViewModel(private val amphibianRepository: AmphibianRepository) : ViewModel() {
    // The mutable state that stores the status of the most recent request
    var appUiState: AppUiState by mutableStateOf(AppUiState.Loading)
        private set

    // Immediately call API to display data
    init { getAmphibianInfo() }

    // Get amphibian info from the API retrofit service and update the list
    fun getAmphibianInfo() {
        viewModelScope.launch {
            appUiState = AppUiState.Loading
            appUiState = try {
                AppUiState.Success(amphibianRepository.getAmphibianInfo())
            } catch (e: IOException) {
                AppUiState.Error
            } catch (e: HttpException) {
                AppUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AmphibiansApplication)
                val amphibianRepository = application.container.amphibianRepository
                AmphibianViewModel(amphibianRepository = amphibianRepository)
            }
        }
    }
}
