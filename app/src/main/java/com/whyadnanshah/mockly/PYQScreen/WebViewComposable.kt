package com.whyadnanshah.mockly.PYQScreen

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewComposable(
    url: String,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val webView = remember { mutableStateOf<WebView?>(null) }
    var canGoBack by remember { mutableStateOf(false) }

    // Memory management
    DisposableEffect(Unit) {
        onDispose {
            webView.value?.apply {
                stopLoading()
                destroy()
            }
        }
    }

    // Enhanced back handling
    BackHandler(enabled = true) {
        if (canGoBack) {
            webView.value?.goBack()
        } else {
            onBackPressed()
        }
    }

    Column(modifier = modifier) {
        // WebView Implementation is HEREEEEEE
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )

                    webViewClient = object : WebViewClient() {
                        override fun onReceivedError(
                            view: WebView,
                            request: WebResourceRequest?,
                            error: WebResourceError?
                        ) {
                            loadUrl("about:blank")
                        }

                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            canGoBack = view?.canGoBack() ?: false
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            canGoBack = view?.canGoBack() ?: false
                        }
                    }

                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        setSupportMultipleWindows(true)
                    }

                    webView.value = this
                }
            },
            update = { view ->
                if (view.url != url && url.isNotEmpty()) {
                    view.loadUrl(url)
                }
            },
            modifier = Modifier.weight(1f)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(
                onClick = {
                    if (canGoBack) webView.value?.goBack()
                    else onBackPressed()
                },
                enabled = true
            ) {
                Icon(
                    if (canGoBack) Icons.Default.ArrowBack else Icons.Default.Close,
                    contentDescription = if (canGoBack) "Back" else "Close"
                )
            }
            Text(
                text = if (canGoBack) "Back" else "Close"
            )
        }
    }
}