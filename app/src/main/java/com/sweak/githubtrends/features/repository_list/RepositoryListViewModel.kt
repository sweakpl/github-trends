package com.sweak.githubtrends.features.repository_list

import androidx.lifecycle.ViewModel
import com.sweak.githubtrends.features.repository_list.model.RepositoryPreviewWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class RepositoryListViewModel : ViewModel() {
    val state = MutableStateFlow(RepositoryListScreenState())

    init {
        state.update { currentState ->
            currentState.copy(
                repositories = listOf(
                    RepositoryPreviewWrapper(
                        id = 0,
                        name = "qralarm-android",
                        username = "sweakpl",
                        description = "QRAlarm is an Android alarm clock application that lets the user turn off alarms by scanning the QR Code.",
                        totalStars = 177,
                        starsSince = 3
                    ),
                    RepositoryPreviewWrapper(
                        id = 0,
                        name = "qralarm-android",
                        username = "sweakpl",
                        description = "QRAlarm is an Android alarm clock application that lets the user turn off alarms by scanning the QR Code.",
                        totalStars = 177,
                        starsSince = 3
                    ),
                    RepositoryPreviewWrapper(
                        id = 0,
                        name = "qralarm-android",
                        username = "sweakpl",
                        description = "QRAlarm is an Android alarm clock application that lets the user turn off alarms by scanning the QR Code.",
                        totalStars = 177,
                        starsSince = 3
                    )
                )
            )
        }
    }
}