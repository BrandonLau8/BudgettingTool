package com.example.budgettingtool

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
// Displays the subitems of the parent
fun SubItemList(subItems: List<SubItem>, parentIndex: Int, viewModel: ItemListViewModel){
    Column (modifier = Modifier.padding(start = 12.dp, top = 8.dp)){
        subItems.forEachIndexed { index, subItem ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                var name by remember { mutableStateOf(subItem.name) }
                var cost by remember { mutableStateOf(subItem.cost.toString()) }

                TextField(
                    value = name,
                    onValueChange = {
                        name = it
                        viewModel.updateSubItem(parentIndex, index, it, cost.toDoubleOrNull() ?: 0.0)
                    },
                    modifier = Modifier.weight(1f),
//                    label = { Text("Name") }
                )

                Spacer(modifier = Modifier.width(8.dp))

                TextField(
                    value = cost,
                    onValueChange = {
                        cost = it
                        viewModel.updateSubItem(parentIndex, index, name, it.toDoubleOrNull() ?: 0.0)
                    },
                    modifier = Modifier.weight(1f),
//                    label = { Text(text = "Cost") }
                )

                Spacer(modifier = Modifier.width(8.dp))

                DeleteButton {
                    viewModel.deleteSubItem(parentIndex, index)
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}