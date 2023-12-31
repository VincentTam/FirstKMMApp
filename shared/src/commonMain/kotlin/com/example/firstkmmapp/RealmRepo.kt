package com.example.firstkmmapp

import CommonFlow
import asCommonFlow
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.internal.platform.runBlocking
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.AppConfiguration
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import kotlinx.coroutines.flow.map

class RealmRepo {
    lateinit var realm: Realm

    private val appServiceInstance by lazy {
        val configuration =
            AppConfiguration.Builder("application-0-uqewy").log(LogLevel.ALL).build()
        App.create(configuration)
    }

    init {
        setupRealmSync()
    }

    private fun setupRealmSync() {
        val user = runBlocking { appServiceInstance.login(Credentials.anonymous()) }
        val config = SyncConfiguration
            .Builder(user, setOf(QueryInfo::class))
            .initialSubscriptions { realm ->
                // only can write data, which cover in initialSubscriptions
                add(
                    query = realm.query<QueryInfo>(),
                    name = "subscription name",
                    updateExisting = true
                )
            }
            .build()
        realm = Realm.open(config)
    }

    suspend fun saveInfo(query: String) {
        val info = QueryInfo().apply {
            _id = RandomUUID().randomId
            queries = query
        }
        realm.write {
            copyToRealm(info)
        }
    }

    suspend fun getAllData(): CommonFlow<List<String>> {
        if (!this::realm.isInitialized) {
            setupRealmSync()
        }
        return realm.query<QueryInfo>().asFlow().map {
            it.list.map { it.queries }
        }.asCommonFlow()
    }
}
