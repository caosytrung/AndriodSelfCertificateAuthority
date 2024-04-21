package com.trungcs.androidnetwork

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.trungcs.androidnetwork.ui.theme.AndroidNetworkCertificateAuthorityTheme
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidNetworkCertificateAuthorityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Content()
                }
            }
        }
    }
}

@Composable
fun Content(
    viewModel: MainViewModel = viewModel(),
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier) {
        val uiState by viewModel.uiState.collectAsState()

        when (val state = uiState) {
            is UiState.Loading -> LoadingView()
            is UiState.Error -> ErrorView(viewModel)
            is UiState.Success -> HelloView(
                state.hello.hello,
                viewModel
            )
        }
    }
}

@Composable
fun LoadingView() {
    CenterColumn(modifier = Modifier.fillMaxSize()) {
        Text(text = stringResource(id = R.string.demo_loading))
        CircularProgressIndicator()
    }
}

@Composable
fun HelloView(hello: String, viewModel: MainViewModel) {
    CenterColumn(modifier = Modifier.fillMaxSize()) {
        Text(hello)
        ElevatedButton(onClick = {viewModel.getHelloFromLocalServer()}) {
            Text(text = stringResource(id = R.string.reload))
        }
    }
}


@Composable
fun ErrorView(viewModel: MainViewModel) {
    CenterColumn(modifier = Modifier.fillMaxSize()) {
        Text(text = stringResource(id = R.string.demo_error_fetching_product))
        ElevatedButton(onClick = {viewModel.getHelloFromLocalServer()}) {
            Text(text = stringResource(id = R.string.reload))
        }
    }
}

@Composable
fun CenterColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidNetworkCertificateAuthorityTheme {

    }
}