package com.example.budgettingtool

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ItemListViewModel @Inject constructor(
    private val repository: ParentItemRepository): ViewModel() {


    private val _parentItems = MutableStateFlow<List<ParentItem>>(emptyList())
    val parentItems: StateFlow<List<ParentItem>> = _parentItems

    init {
        viewModelScope.launch {
            repository.parentItemsFlow.collect { items ->
                _parentItems.value = items
            }
        }
    }


    fun updateSubItem(parentIndex: Int, subItemIndex: Int, name: String, cost: Double) {
        // 1. Get a mutable list of parent items
        val updatedParentItems = _parentItems.value.toMutableList()

        // 2. Get the parent item to update
        val parentItem = updatedParentItems[parentIndex]

        // 3. Create a mutable copy of subItems and update the specific one
        val updatedSubItems = parentItem.subItems.toMutableList()
        updatedSubItems[subItemIndex] = updatedSubItems[subItemIndex].copy(name = name, cost = cost)

        // 4. Replace the parent item with a new instance containing the updated subItems
        updatedParentItems[parentIndex] = parentItem.copy(subItems = updatedSubItems)

        // 5. Update the state
        _parentItems.value = updatedParentItems

        // 6. Persist changes to DataStore
        saveItems(updatedParentItems)
    }

    fun updateParentItem(index: Int, newName: String) {
        val updatedList = _parentItems.value.toMutableList()
        val item = updatedList[index]
        updatedList[index] = item.copy(name = newName)
        _parentItems.value = updatedList
        saveItems(updatedList)
    }

    private fun saveItems(items: List<ParentItem>) {
        viewModelScope.launch {
            repository.saveParentItems(items)
        }
    }

    fun addParentItem() {
        val updatedParentItems = _parentItems.value.toMutableList()
        updatedParentItems.add(ParentItem("", mutableListOf()))
        _parentItems.value = updatedParentItems
        saveItems(updatedParentItems)
    }

    fun addSubItem(parentIndex: Int) {
        val updatedParentItems = _parentItems.value.toMutableList()
        val parentItem = updatedParentItems[parentIndex]
        val updatedSubItems = parentItem.subItems.toMutableList()
        updatedSubItems.add(SubItem("", 0.0))

        updatedParentItems[parentIndex] = parentItem.copy(subItems = updatedSubItems)
        _parentItems.value = updatedParentItems
        saveItems(updatedParentItems)
    }

    fun deleteParentItem(parentIndex: Int) {
        val updatedParentItems = _parentItems.value.toMutableList()
        updatedParentItems.removeAt(parentIndex)
        _parentItems.value = updatedParentItems
        saveItems(updatedParentItems)
    }

    fun deleteSubItem(parentIndex: Int, subItemIndex: Int) {
        val updatedParentItems = _parentItems.value.toMutableList()
        val parentItem = updatedParentItems[parentIndex]
        val updatedSubItems = parentItem.subItems.toMutableList()
        updatedSubItems.removeAt(subItemIndex)

        updatedParentItems[parentIndex] = parentItem.copy(subItems = updatedSubItems)
        _parentItems.value = updatedParentItems
        saveItems(updatedParentItems)
    }
}