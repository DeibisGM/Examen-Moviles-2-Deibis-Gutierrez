package com.app.copamundialfifa2026.ui.screens.ticketdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.copamundialfifa2026.core.FeatureFlags
import com.app.copamundialfifa2026.core.UserMessages
import com.app.copamundialfifa2026.data.AppContainer
import com.app.copamundialfifa2026.data.model.TicketPriority
import com.app.copamundialfifa2026.data.model.TicketStatus
import com.app.copamundialfifa2026.ui.components.AppScaffold
import com.app.copamundialfifa2026.ui.components.CategoryChip
import com.app.copamundialfifa2026.ui.components.LabeledDropdown
import com.app.copamundialfifa2026.ui.components.PriorityChip
import com.app.copamundialfifa2026.ui.components.StatusChip

@Composable
fun TicketDetailScreen(
    ticketId: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TicketDetailViewModel = viewModel(
        factory = TicketDetailViewModel.Factory(ticketId, AppContainer.ticketRepository)
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.message, uiState.errorMessage) {
        val text = uiState.message ?: uiState.errorMessage
        if (text != null) {
            snackbarHostState.showSnackbar(text)
            viewModel.consumeMessage()
        }
    }

    AppScaffold(
        title = UserMessages.TicketDetail.TITLE,
        modifier = modifier,
        showBack = true,
        onBackClick = onBack,
        snackbarHostState = snackbarHostState
    ) { padding ->
        val ticket = uiState.ticket
        if (ticket == null) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text(UserMessages.TicketDetail.NOT_FOUND)
            }
            return@AppScaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(ticket.title, style = MaterialTheme.typography.headlineSmall)

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PriorityChip(priority = ticket.priority)
                StatusChip(status = ticket.status)
            }

            LabeledRow(UserMessages.TicketDetail.PROVIDER, ticket.provider)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = UserMessages.TicketDetail.CATEGORY,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                CategoryChip(category = ticket.category)
            }
            LabeledRow(UserMessages.TicketDetail.CREATED, ticket.createdAt)

            HorizontalDivider()

            Text(
                text = UserMessages.TicketDetail.DESCRIPTION,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(ticket.description, style = MaterialTheme.typography.bodyLarge)

            HorizontalDivider()

            // --- Status update (required by the MVP) ---
            Text(
                text = UserMessages.TicketDetail.STATUS_SECTION,
                style = MaterialTheme.typography.titleSmall
            )
            LabeledDropdown(
                label = UserMessages.TicketDetail.STATUS_SECTION,
                options = TicketStatus.entries,
                selected = ticket.status,
                optionLabel = { it.label },
                onSelected = { viewModel.updateStatus(it) }
            )

            // --- Priority update (gated by a feature flag) ---
            if (FeatureFlags.PRIORITY_UPDATE_ENABLED) {
                Text(
                    text = UserMessages.TicketDetail.PRIORITY_SECTION,
                    style = MaterialTheme.typography.titleSmall
                )
                LabeledDropdown(
                    label = UserMessages.TicketDetail.PRIORITY_SECTION,
                    options = TicketPriority.entries,
                    selected = ticket.priority,
                    optionLabel = { it.label },
                    onSelected = { viewModel.updatePriority(it) }
                )
            }
        }
    }
}

@Composable
private fun LabeledRow(label: String, value: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}
