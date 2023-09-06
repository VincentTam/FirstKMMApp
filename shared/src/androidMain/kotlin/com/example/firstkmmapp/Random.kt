package com.example.firstkmmapp

import java.util.UUID

actual class RandomUUID {
    actual val randomId: String
        get() = UUID.randomUUID().toString()
}
