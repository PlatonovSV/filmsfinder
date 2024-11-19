package com.praions.filmsfinder.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.praions.filmsfinder.R

@Composable
fun FilmsFinderAsyncImage(
    imageUrl: String?,
    contentScale: ContentScale = ContentScale.Crop,
    modifier: Modifier = Modifier
) {
    val modifier = modifier
    if (imageUrl != null && imageUrl.isNotEmpty()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(imageUrl)
                .crossfade(true).build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.movie_poster),
            contentScale = contentScale,
            modifier = modifier
                .clip(MaterialTheme.shapes.small)
        )
    } else {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.tertiary, MaterialTheme.shapes.small)
        ) {
            Icon(
                painter = painterResource(R.drawable.media),
                contentDescription = stringResource(R.string.movie_poster),
                modifier = Modifier
                    .size(height = 51.8.dp, width = 57.44.dp)
                    .padding(top = 5.18.dp)
                    .align(Alignment.Center),
                tint = MaterialTheme.colorScheme.onTertiary
            )
        }
    }
}