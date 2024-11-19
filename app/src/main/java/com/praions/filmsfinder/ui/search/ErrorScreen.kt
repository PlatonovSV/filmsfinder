package com.praions.filmsfinder.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.InternalComposeApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.praions.filmsfinder.R
import com.praions.filmsfinder.network.ErrorType
import com.praions.filmsfinder.ui.theme.FilmsFinderTheme

@OptIn(InternalComposeApi::class)
@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    errorType: ErrorType?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        val stringRes =
            if (errorType == ErrorType.SERVER) R.string.server_error else R.string.no_internet
        StaticSnackbar(
            retryAction,
            message = stringResource(stringRes),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun StaticSnackbar(
    retryAction: () -> Unit,
    message: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(56.dp)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(2.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            message,
            color = MaterialTheme.colorScheme.background,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(start = 16.dp)
        )
        TextButton(onClick = retryAction) {
            Text(
                text = stringResource(R.string.retry).uppercase(),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    }
}

@Preview
@Composable
fun ErrorScreenPreview() {
    FilmsFinderTheme {
        ErrorScreen(
            errorType = ErrorType.NO_INTERNET,
            retryAction = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}