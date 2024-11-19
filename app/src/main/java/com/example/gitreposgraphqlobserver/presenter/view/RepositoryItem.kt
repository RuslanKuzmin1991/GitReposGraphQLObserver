package com.example.gitreposgraphqlobserver.presenter.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.gitreposgraphqlobserver.data.entity.Repository

@Composable
fun RepositoryItem(
    repository: Repository
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = repository.avatarUrl,
                contentDescription = "Owner Avatar",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = repository.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = repository.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "⭐ ${repository.stargazerCount}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    repository.languageColor?.let {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    Text(
                        text = repository.language, style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Button(
                onClick = {
                    try {
                        openInBrowser(context, repository.url)
                    } catch (error: Exception) {
                        //TODO: Temp. Needs to be improved
                        Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                    }
                }, modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Open")
            }
        }
    }
}

fun openInBrowser(context: Context, url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    } catch (error: Exception) {
        throw error
    }
}

@Preview(showBackground = false)
@Composable
fun RepositoryItemPreview() {
    val repo = Repository(
        "Swift repo",
        "some description",
        0,
        "null",
        "swift",
        null,
        "some data",
        "Johh Doe",
        "url",

        )
    RepositoryItem(repo)
}