package com.praions.filmsfinder.adapters

import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.praions.filmsfinder.R
import com.praions.filmsfinder.common.FilmsFinderAsyncImage
import com.praions.filmsfinder.model.FilmCard
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
        FilmsFinderAsyncImage(
            imageUrl = film.imageUrl,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        val movieName = film.name ?: stringResource(R.string.unknown)
        Text(
            text = movieName,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            minLines = 2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
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

