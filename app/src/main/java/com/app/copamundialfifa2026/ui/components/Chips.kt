package com.app.copamundialfifa2026.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.copamundialfifa2026.data.model.TicketCategory
import com.app.copamundialfifa2026.data.model.TicketPriority
import com.app.copamundialfifa2026.data.model.TicketStatus
import com.app.copamundialfifa2026.ui.theme.PriorityCritical
import com.app.copamundialfifa2026.ui.theme.PriorityHigh
import com.app.copamundialfifa2026.ui.theme.PriorityLow
import com.app.copamundialfifa2026.ui.theme.PriorityMedium
import com.app.copamundialfifa2026.ui.theme.StatusClosed
import com.app.copamundialfifa2026.ui.theme.StatusInProgress
import com.app.copamundialfifa2026.ui.theme.StatusOpen
import com.app.copamundialfifa2026.ui.theme.StatusResolved

/** Small colored pill used for priority/status/category labels. */
@Composable
private fun Pill(text: String, color: Color, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = Color.White,
        style = MaterialTheme.typography.labelMedium,
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(color)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    )
}

@Composable
fun PriorityChip(priority: TicketPriority, modifier: Modifier = Modifier) {
    val color = when (priority) {
        TicketPriority.CRITICAL -> PriorityCritical
        TicketPriority.HIGH -> PriorityHigh
        TicketPriority.MEDIUM -> PriorityMedium
        TicketPriority.LOW -> PriorityLow
    }
    Pill(text = priority.label, color = color, modifier = modifier)
}

@Composable
fun StatusChip(status: TicketStatus, modifier: Modifier = Modifier) {
    val color = when (status) {
        TicketStatus.OPEN -> StatusOpen
        TicketStatus.IN_PROGRESS -> StatusInProgress
        TicketStatus.RESOLVED -> StatusResolved
        TicketStatus.CLOSED -> StatusClosed
    }
    Pill(text = status.label, color = color, modifier = modifier)
}

@Composable
fun CategoryChip(category: TicketCategory, modifier: Modifier = Modifier) {
    Pill(text = category.label, color = StatusClosed, modifier = modifier)
}
