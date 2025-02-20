package com.sweak.githubtrends.features.repository_details

import androidx.lifecycle.ViewModel
import com.sweak.githubtrends.features.repository_details.model.RepositoryDetailsWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class RepositoryDetailsViewModel : ViewModel() {
    val state = MutableStateFlow(RepositoryDetailsScreenState())

    init {
        state.update { currentState ->
            currentState.copy(
                repositoryDetailsWrapper =
                RepositoryDetailsWrapper(
                    name = "qralarm-android",
                    username = "sweakpl",
                    description = "QRAlarm is an Android alarm clock application that lets the user turn off alarms by scanning the QR Code.",
                    totalStars = 177,
                    starsSince = 3
                )
            )
        }
    }
}