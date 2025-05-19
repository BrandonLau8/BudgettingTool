package com.example.budgettingtool

import androidx.compose.runtime.mutableStateListOf
import kotlinx.serialization.Serializable

@Serializable
data class SubItem(
    var name:String,
    var cost:Double
)

@Serializable
data class ParentItem(
    var name:String,
    val subItems: List<SubItem> = emptyList()
) {
    val totalCost:Double get() = subItems.orEmpty().sumOf{it.cost}
}