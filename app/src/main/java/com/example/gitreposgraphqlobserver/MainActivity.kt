package com.example.gitreposgraphqlobserver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import com.example.gitreposgraphqlobserver.presenter.screen.RepositoriesScreen
import com.example.gitreposgraphqlobserver.ui.theme.GitReposGraphQLObserverTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitReposGraphQLObserverTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text("Repositories") })
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    RepositoriesScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}