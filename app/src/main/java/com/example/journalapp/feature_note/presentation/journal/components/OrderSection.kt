package com.example.journalapp.feature_note.presentation.journal.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.journalapp.feature_note.domain.util.NoteOrder
import com.example.journalapp.feature_note.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            DefaultRadioButton(
                text = "Title",
                isClicked = noteOrder is NoteOrder.Title,
                onClick = {
                    onOrderChange(NoteOrder.Title(noteOrder.orderType))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                isClicked = noteOrder is NoteOrder.Date,
                onClick = {
                    onOrderChange(NoteOrder.Date(noteOrder.orderType))
                }
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Row(
            Modifier.fillMaxWidth()
        ){
            DefaultRadioButton(
                text = "Ascending",
                isClicked = noteOrder.orderType is OrderType.Ascending,
                onClick = {
                    onOrderChange(noteOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                isClicked = noteOrder.orderType is OrderType.Descending,
                onClick = {
                    onOrderChange(noteOrder.copy(OrderType.Descending))
                }
            )
        }
    }

}