package com.dev_young.note.presentation.notesList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dev_young.note.R
import com.dev_young.note.domain.model.Note

@Composable
fun NoteCard(
    note: Note,
    modifier: Modifier,
    onDeleteClick: () -> Unit
) {
    val textColor = if (note.color == Color(0xFF1B1B1A).toArgb()) Color.White else Color.Black
    val iconTint = if (note.color == Color(0xFF1B1B1A).toArgb()) Color.White else Color.Black
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(15.dp),
        shape = RectangleShape
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(note.color))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            ) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = textColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = note.content,
                    maxLines = 5,
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    color = textColor
                )
            }
            IconButton(
                onClick = { onDeleteClick() },
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "DeleteIcon",
                    tint = iconTint
                )
            }
        }
    }
}