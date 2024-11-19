package com.praions.filmsfinder.ui.film

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.praions.filmsfinder.FilmsFinderToolbar
import com.praions.filmsfinder.R
import com.praions.filmsfinder.common.FilmsFinderAsyncImage

@Composable
fun FilmScreen(
    onBackClick: () -> Unit,
    canNavigateBack: Boolean,
    uiState: FilmUiState, modifier: Modifier
) {
    val screenName = when {
        uiState.name.isNotEmpty() -> uiState.name
        uiState.localizedName.isNotEmpty() -> uiState.localizedName
        else -> stringResource(R.string.name_unknown)
    }
    Scaffold(topBar = {
        FilmsFinderToolbar(
            title = screenName,
            onBackClick = onBackClick,
            canNavigateBack = canNavigateBack,
            modifier = Modifier
        )
    }) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            FilmsFinderAsyncImage(
                imageUrl = uiState.imageUrl,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(201.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(24.dp))
            val name = when {
                uiState.localizedName.isNotEmpty() -> uiState.localizedName
                uiState.name.isNotEmpty() -> uiState.name
                else -> stringResource(R.string.name_unknown)
            }
            Text(
                text = name,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            val genres = if (uiState.genres.isEmpty()) {
                stringResource(R.string.genres_unknown)
            } else {
                uiState.genres.joinToString(separator = ", ")
            }

            val year = if (uiState.year.isBlank()) {
                stringResource(R.string.year_unknown)
            } else {
                stringResource(
                    R.string.year,
                    uiState.year
                )
            }

            Text(
                text = "$genres, $year",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onTertiary
            )
            Spacer(modifier = Modifier.height(8.dp))
            val rating = if (uiState.rating.isBlank()) {
                stringResource(R.string.rating_unknown)
            } else {
                uiState.rating.take(3)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = rating,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.move_finder),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            val description = when {
                uiState.description.isNotEmpty() -> uiState.description
                else -> stringResource(R.string.description_unknown)
            }
            Text(
                text = description, style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

}