package com.example.budgettingtool

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.toList

@Composable
// Displays a list of parent items
fun ItemListScreen(viewModel: ItemListViewModel) {

    val parentItems by viewModel.parentItems.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray) // Light gray background
            .padding(0.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier
                .padding(top = 50.dp),
            onClick = { viewModel.addParentItem() }) {
            Text("Add New Parent Item")
        }

        // The LazyColumn for displaying parent items
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(parentItems) { index, parentItem ->
                ParentItemCard(parentItem, index, viewModel)
            }
        }
    }
}