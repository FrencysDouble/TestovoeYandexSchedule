package com.example.testovoeyandexschedule.main_ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter



@Composable
fun CalendarDialog(
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDate) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Выберите дату") },
        text = {
            CustomCalendar(
                onDateSelected = onDateSelected
            )
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Отмена")
            }
        }
    )
}

@Composable
fun CustomCalendar(
    onDateSelected: (LocalDate) -> Unit
) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }

    Column(modifier = Modifier.padding(16.dp)) {
        CalendarHeader(
            currentMonth = currentMonth,
            onPreviousMonth = { currentMonth = currentMonth.minusMonths(1) },
            onNextMonth = { currentMonth = currentMonth.plusMonths(1) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CalendarWeekDays()

        Spacer(modifier = Modifier.height(8.dp))

        CalendarDays(
            currentMonth = currentMonth,
            selectedDate = selectedDate,
            onDateSelected = {
                selectedDate = it
                onDateSelected(it)
            }
        )
    }
}

@Composable
fun CalendarHeader(
    currentMonth: YearMonth,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousMonth) {
            Text("<", fontSize = MaterialTheme.typography.bodyLarge.fontSize)
        }

        Text(
            text = currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        IconButton(onClick = onNextMonth) {
            Text(">", fontSize = MaterialTheme.typography.bodyLarge.fontSize)
        }
    }
}

@Composable
fun CalendarWeekDays() {
    Row(modifier = Modifier.fillMaxWidth()) {
        val daysOfWeek = listOf("пн", "вт", "ср", "чт", "пт", "сб", "вс")
        daysOfWeek.forEach { day ->
            Text(
                text = day,
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CalendarDays(
    currentMonth: YearMonth,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val firstDayOfMonth = currentMonth.atDay(1)
    val lastDayOfMonth = currentMonth.atEndOfMonth()
    val daysInMonth = lastDayOfMonth.dayOfMonth

    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
    val totalDays = firstDayOfWeek + daysInMonth

    Column {
        for (week in 0 until totalDays / 7 + 1) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (dayOffset in 0..6) {
                    val dayOfMonth = week * 7 + dayOffset - firstDayOfWeek + 1
                    if (dayOfMonth in 1..daysInMonth) {
                        val date = currentMonth.atDay(dayOfMonth)
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .clickable { onDateSelected(date) }
                                .background(
                                    if (date == selectedDate) Color.Gray else Color.Transparent
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = dayOfMonth.toString())
                        }
                    } else {
                        Box(modifier = Modifier.weight(1f)) {}
                    }
                }
            }
        }
    }
}