package com.example.budgettingtool

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json


// Setup datastore in a singleton or repository
val Context.dataStore by preferencesDataStore(name = "parent_item_prefs")

class ParentItemRepository(private val context: Context) {
    private val PARENT_ITEMS_KEY = stringPreferencesKey("parent_items")

    val parentItemsFlow: Flow<List<ParentItem>> = context.dataStore.data
        .map { preferences ->
            val json = preferences[PARENT_ITEMS_KEY] ?: "[]"
            Json.decodeFromString(json)
        }

    suspend fun saveParentItems(parentItems: List<ParentItem>) {
        context.dataStore.edit { preferences ->
            preferences[PARENT_ITEMS_KEY] = Json.encodeToString(parentItems)
        }
    }
}