package com.praions.filmsfinder.adapters

import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.praions.filmsfinder.ui.search.Title
import com.praions.filmsfinder.ui.theme.FilmsFinderTheme

class TitleAdapter() : ListAdapter<Title, TitleAdapter.TitleViewHolder>(TitleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TitleViewHolder(
        ComposeView(parent.context)
    )


    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TitleViewHolder(
        private val composeView: ComposeView,
    ) : RecyclerView.ViewHolder(composeView) {

        fun bind(input: Title) {
            composeView.setContent {
                FilmsFinderTheme {
                    TitleItem(
                        title = input, modifier = Modifier
                    )
                }
            }
        }
    }

}

@Composable
fun TitleItem(
    title: Title,
    modifier: Modifier = Modifier,
) {
    val padding =
        if (title.id == 0) PaddingValues(top = 8.dp) else PaddingValues(top = 16.dp, bottom = 8.dp)
    Box(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(padding)
            .fillMaxWidth()
    ) {
        Text(
            text = title.title,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 9.dp, bottom = 9.dp, start = 16.dp)
        )
    }
}

@Preview
@Composable
fun TitleItemPreview() {
    FilmsFinderTheme {
        TitleItem(
            title = Title(
                id = 1,
                title = "Фильмы"
            ),
            modifier = Modifier
        )
    }
}


class TitleDiffCallback : DiffUtil.ItemCallback<Title>() {
    override fun areItemsTheSame(
        oldItem: Title, newItem: Title
    ) = oldItem.id == newItem.id


    override fun areContentsTheSame(
        oldItem: Title, newItem: Title
    ) = oldItem == newItem

}