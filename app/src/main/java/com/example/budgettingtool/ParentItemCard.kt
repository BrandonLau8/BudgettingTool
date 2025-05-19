package com.example.budgettingtool

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// Displays each parent item and its total cost. It expands to show the subitem list
fun ParentItemCard(parentItem: ParentItem, parentIndex: Int, viewModel: ItemListViewModel) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column (modifier = Modifier.padding(8.dp)){
            BasicTextField(
                value = parentItem.name,
                onValueChange = {
                    viewModel.updateParentItem(parentIndex, it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                textStyle = TextStyle(
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                cursorBrush = SolidColor(Color.White),
                decorationBox = { innerTextField ->
                    if (parentItem.name.isEmpty()) {
                        Text("Item Name", color = Color.Gray, fontSize = 18.sp)
                    }
                    innerTextField()
                }
            )
            Text(text = "Total Cost: $${parentItem.totalCost}")
            if(expanded) {
                SubItemList(parentItem.subItems, parentIndex, viewModel)
            }
            Spacer(modifier = Modifier.height(8.dp))
            SimpleButton(text = if (expanded) "Hide Details" else "Show Details") {
                expanded = !expanded
            }
            SimpleButton(text = "Add SubItem") {
                viewModel.addSubItem(parentIndex)
            }
            DeleteButton {
                viewModel.deleteParentItem(parentIndex)
            }
        }
    }
}

@Composable
fun SimpleButton(text: String, onClick:() -> Unit) {
    Button(onClick = onClick) {
        Text(text)
    }
}

@Composable
fun DeleteButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("Delete")
    }
}