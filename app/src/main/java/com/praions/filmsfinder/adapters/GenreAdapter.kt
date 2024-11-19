package com.praions.filmsfinder.adapters

import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.praions.filmsfinder.model.Genre
import com.praions.filmsfinder.ui.theme.FilmsFinderTheme

class GenreAdapter(
    private var onClick: (genreId: Int) -> Unit
) : ListAdapter<Genre, GenreAdapter.GenreViewHolder>(GenreDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GenreViewHolder(
        ComposeView(parent.context), onClick
    )


    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class GenreViewHolder(
        private val composeView: ComposeView,
        private val onClick: (Int) -> Unit,
    ) : RecyclerView.ViewHolder(composeView) {

        fun bind(input: Genre) {
            composeView.setContent {
                FilmsFinderTheme {
                    GenreItem(
                        genre = input, onClick = onClick, modifier = Modifier
                    )
                }
            }
        }
    }

}

@Composable
fun GenreItem(
    genre: Genre,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor =
        if (genre.isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.background
    Box(modifier = modifier
        .fillMaxWidth()
        .clickable { onClick(genre.id) }
        .background(color = backgroundColor)) {
        Text(
            text = genre.name.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 16.dp)
        )
    }
}

@Preview
@Composable
fun GenreItemPreview() {
    FilmsFinderTheme {
        GenreItem(
            genre = Genre(
                id = 1,
                name = "драма",
                isSelected = true
            ), onClick = {},
            modifier = Modifier
        )
    }
}


class GenreDiffCallback : DiffUtil.ItemCallback<Genre>() {
    override fun areItemsTheSame(
        oldItem: Genre, newItem: Genre
    ) = oldItem.id == newItem.id


    override fun areContentsTheSame(
        oldItem: Genre, newItem: Genre
    ) = oldItem == newItem

}