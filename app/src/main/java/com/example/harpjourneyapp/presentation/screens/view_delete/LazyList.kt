//package com.example.harpjourneyapp.presentation.screens.view_delete
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.itemsIndexed
////noinspection UsingMaterialAndMaterial3Libraries
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//
//@Composable
//fun LazyList(vm: HomeViewModel,
//             onIndexChange: (Int) -> Unit){
//    LazyColumn{
//        itemsIndexed(vm.items) { index, item ->
//            ItemView(
//                index = index,
//                item = item.toString(),
//                selected = vm.selectedContactIndex == index,
//                onClick = {
//                    onIndexChange(index)
//                    vm.selectedContactIndex = index
//                }
//            )
//        }
//    }
//}
//@Composable
//fun ItemView(index: Int,
//             item: String,
//             selected: Boolean,
//             onClick: (Int) -> Unit){
//    val itemBackgroundColour = when {
//        selected -> Color.Yellow
//        index % 2 == 0 -> Color.LightGray
//        else -> Color.Gray
//    }
//    Text(
//        text = item,
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { onClick.invoke(index) }
//            .background(itemBackgroundColour)
//            .padding(10.dp)
//    )
//}