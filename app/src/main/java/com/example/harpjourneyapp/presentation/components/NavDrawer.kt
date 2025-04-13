//package com.example.harpjourneyapp.presentation.components
//
//import androidx.compose.runtime.Composable
//import androidx.navigation.NavHostController
//
//@Composable
//private fun createListOfItemsForMenu(): List<NavItem> {
//    return listOf(
//        NavItem.Home,
//        NavItem.Add,
//        NavItem.Globe,
//        NavItem.Idea,
//        NavItem.Rocket,
//        NavItem.Exit
//    )
//}
//
//
//@Composable
//fun DrawerContent(
//    menuTitle: String,
//    navController: NavHostController,
//    closeDrawer: () -> Unit
//) {
//    val itemsList = createListOfItemsForMenu()
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        contentPadding = PaddingValues(vertical = 36.dp)
//    ) {
//        item {
//            Text(
//                modifier = Modifier
//                    .padding(top = 12.dp),
//                text = menuTitle,
//                fontSize = 26.sp,
//                fontWeight = FontWeight.Bold,
//                color = Color.Black,
//            )
//        }
//        items(itemsList) { item ->
//            ListItem(item = item,
//                itemClickAction = { navController.navigate(item.route)
//                    closeDrawer()
//                })
//        }
//    }
//}
//@Composable
//private fun ListItem(
//    item: NavItem,
//    itemClick: () -> Unit
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable {
//                itemClick() //What action to perform when the menu item is clicked
//            }
//            .padding(horizontal = 24.dp, vertical = 10.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
////add an icon for each item
//        Box {
//            Icon(
//                modifier = Modifier
//                    .padding(all = 2.dp)
//                    .size(size = 28.dp),
//                painter = (painterResource(id = item.icon)),
//                contentDescription = item.route,
//                tint = Color.Black
//            )
//        } //add the text for each item
//        Text(
//            modifier = Modifier.padding(start = 20.dp),
//            text = item.route,
//            color = Color.Black,
//            fontSize = 18.sp,
//            fontWeight = FontWeight.Normal
//        )
//    }
//}