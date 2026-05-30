package com.app.copamundialfifa2026.ui.screens.createticket

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.copamundialfifa2026.core.UserMessages
import com.app.copamundialfifa2026.data.AppContainer
import com.app.copamundialfifa2026.data.model.TicketCategory
import com.app.copamundialfifa2026.data.model.TicketPriority
import com.app.copamundialfifa2026.ui.components.AppButton
import com.app.copamundialfifa2026.ui.components.AppScaffold
import com.app.copamundialfifa2026.ui.components.AppTextField
import com.app.copamundialfifa2026.ui.components.LabeledDropdown

@Composable
fun CreateTicketScreen(
    onBack: () -> Unit,
    onCreated: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreateTicketViewModel = viewModel(
        factory = CreateTicketViewModel.Factory(AppContainer.ticketRepository)
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // When the repository confirms creation, return to the list — which has already updated itself.
    LaunchedEffect(uiState.created) {
        if (uiState.created) onCreated()
    }

    AppScaffold(
        title = UserMessages.CreateTicket.TITLE,
        modifier = modifier,
        showBack = true,
        onBackClick = onBack
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            AppTextField(
                value = uiState.title,
                onValueChange = viewModel::onTitleChange,
                label = UserMessages.CreateTicket.TITLE_LABEL
            )
            AppTextField(
                value = uiState.description,
                onValueChange = viewModel::onDescriptionChange,
                label = UserMessages.CreateTicket.DESCRIPTION_LABEL,
                singleLine = false,
                minLines = 3
            )
            AppTextField(
                value = uiState.provider,
                onValueChange = viewModel::onProviderChange,
                label = UserMessages.CreateTicket.PROVIDER_LABEL
            )
            LabeledDropdown(
                label = UserMessages.CreateTicket.CATEGORY_LABEL,
                options = TicketCategory.entries,
                selected = uiState.category,
                optionLabel = { it.label },
                onSelected = viewModel::onCategoryChange
            )
            LabeledDropdown(
                label = UserMessages.CreateTicket.PRIORITY_LABEL,
                options = TicketPriority.entries,
                selected = uiState.priority,
                optionLabel = { it.label },
                onSelected = viewModel::onPriorityChange
            )

            if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            AppButton(
                text = if (uiState.isSubmitting) UserMessages.CreateTicket.SUBMITTING
                else UserMessages.CreateTicket.SUBMIT,
                onClick = viewModel::submit,
                loading = uiState.isSubmitting
            )
        }
    }
}
