package com.praions.filmsfinder.adapters

import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.praions.filmsfinder.R
import com.praions.filmsfinder.ui.search.FilmCard
import com.praions.filmsfinder.ui.theme.FilmsFinderTheme

class FilmAdapter(
    private var onClick: (filmId: Long) -> Unit
) : ListAdapter<FilmCard, FilmAdapter.FilmListViewHolder>(FilmCardDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FilmListViewHolder(
        ComposeView(parent.context), onClick
    )


    override fun onBindViewHolder(holder: FilmListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FilmListViewHolder(
        private val composeView: ComposeView,
        private val onClick: (Long) -> Unit,
    ) : RecyclerView.ViewHolder(composeView) {

        fun bind(input: FilmCard) {
            composeView.setContent {
                Box(
                    modifier = Modifier
                        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                        .size(width = 160.dp, height = 270.dp)
                ) {
                    FilmsFinderTheme {
                        FilmCardItem(
                            film = input,
                            onClick = onClick,
                            modifier = Modifier.size(width = 160.dp, height = 270.dp)
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun FilmCardItem(
    film: FilmCard,
    onClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier
        .clip(MaterialTheme.shapes.small)
        .clickable { onClick(film.id) }
    ) {
        val imageModifier = Modifier
            .weight(1f)
            .clip(MaterialTheme.shapes.small)
        if (film.imageUrl != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(film.imageUrl)
                    .crossfade(true).build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = stringResource(R.string.movie_poster),
                contentScale = ContentScale.Crop,
                modifier = imageModifier
            )
        } else {
            Box(modifier = imageModifier.background(MaterialTheme.colorScheme.tertiary).fillMaxWidth()) {
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
        Spacer(modifier = Modifier.height(8.dp))
        val movieName = film.name ?: stringResource(R.string.unknown)
        val movieNameFormated = if (movieName.length > 20) movieName.take(20) + "..." else movieName
        Text(
            text = movieNameFormated,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            minLines = 2,
            maxLines = 2
        )
    }
}

@Preview
@Composable
fun FilmCardItemPreview() {
    FilmsFinderTheme {
        FilmCardItem(
            film = FilmCard(
                id = 1,
                imageUrl = "https://st.kp.yandex.net/images/film_iphone/iphone360_326.jpg",
                name = "Побег из Шоушенка"
            ),
            onClick = {},
            modifier = Modifier
                .padding(bottom = 16.dp)
                .size(width = 160.dp, height = 270.dp)
        )
    }
}


class FilmCardDiffCallback : DiffUtil.ItemCallback<FilmCard>() {
    override fun areItemsTheSame(
        oldItem: FilmCard, newItem: FilmCard
    ) = oldItem.id == newItem.id


    override fun areContentsTheSame(
        oldItem: FilmCard, newItem: FilmCard
    ) = oldItem == newItem

}

