package com.example.tesis1

import android.util.Log
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class AudioWebSocketListener(
    private val onMessageReceived: (String) -> Unit
) : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
        Log.d("AudioWebSocket", "WebSocket Opened")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.d("AudioWebSocket", "Receiving: $text")
        onMessageReceived(text)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        Log.d("AudioWebSocket", "Receiving bytes: " + bytes.hex())
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(1000, null)
        Log.d("AudioWebSocket", "Closing: $code / $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
        t.printStackTrace()
    }
}
