package com.example.firstkmmapp

expect class RandomUUID(){
    val randomId : String
}

expect class Platform(){
    val getPlatform : String
}
