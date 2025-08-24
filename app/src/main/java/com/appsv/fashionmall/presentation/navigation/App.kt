package com.appsv.fashionmall.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.appsv.fashionmall.R
import com.appsv.fashionmall.presentation.LoginScreen
import com.appsv.fashionmall.presentation.SignUpScreen
import com.appsv.fashionmall.presentation.screens.CartScreen
import com.appsv.fashionmall.presentation.screens.CategoriesScreen
import com.appsv.fashionmall.presentation.screens.CheckoutScreen
import com.appsv.fashionmall.presentation.screens.EachCategoryProductScreen
import com.appsv.fashionmall.presentation.screens.EachProductDetailsScreen
import com.appsv.fashionmall.presentation.screens.GetAllFavScreen
import com.appsv.fashionmall.presentation.screens.GetAllProductScreen
import com.appsv.fashionmall.presentation.screens.HomeScreen
import com.appsv.fashionmall.presentation.screens.ProfileScreen
import com.example.bottombar.AnimatedBottomBar
import com.example.bottombar.components.BottomBarItem
import com.example.bottombar.model.IndicatorDirection
import com.example.bottombar.model.IndicatorStyle
import com.google.firebase.auth.FirebaseAuth


data class BottomNavItem(val name: String, val icon: ImageVector, val unselectedIcon: ImageVector)

@Composable
fun App(

    firebaseAuth: FirebaseAuth
) {

    val navController = rememberNavController()

    var selectedItem by remember { mutableStateOf(0) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    val shouldShowBottomBar = remember { mutableStateOf(false) }

    LaunchedEffect(currentDestination) {

        shouldShowBottomBar.value = when (currentDestination) {
            Routes.LoginScreen::class.qualifiedName, Routes.SignUpScreen::class.qualifiedName -> false
            else -> true
        }
    }

    val BottomNavItem = listOf(

        BottomNavItem("Home", Icons.Default.Home, Icons.Outlined.Home),
        BottomNavItem("FavList", Icons.Default.Favorite, Icons.Outlined.Favorite),
        BottomNavItem("Cart", Icons.Default.ShoppingCart, Icons.Outlined.ShoppingCart),
        BottomNavItem("Profile", Icons.Default.Person, Icons.Outlined.Person)

    )

    var startScreen = if (firebaseAuth.currentUser == null) {
        SubNavigation.LoginSignUpScreen
    } else {
        SubNavigation.MainHomeScreen
    }

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar.value) {

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = WindowInsets.navigationBars.asPaddingValues()
                                .calculateBottomPadding()
                        )
                ) {
                    AnimatedBottomBar(
                        selectedItem = selectedItem,
                        itemSize = BottomNavItem.size,
                        containerColor = Color.Transparent,
                        indicatorColor = colorResource(id = R.color.button_color),
                        indicatorDirection = IndicatorDirection.BOTTOM,
                        indicatorStyle = IndicatorStyle.FILLED
                    ) {

                        BottomNavItem.forEachIndexed { index, navigationItem ->

                            BottomBarItem(
                                selected = selectedItem == index,
                                onClick = {
                                    selectedItem = index
                                    when(index){

                                        0 -> navController.navigate(Routes.HomeScreen)
                                        1 -> navController.navigate(Routes.FavListScreen)
                                        2 -> navController.navigate(Routes.CartScreen)
                                        3 -> navController.navigate(Routes.ProfileScreen)

                                    }
                                },
                                imageVector = navigationItem.icon,
                                label = navigationItem.name,
                                containerColor = Color.Transparent
                            )
                        }

                    }
                }
            }
        }

    ) { innerPadding ->

        Box(
            modifier = Modifier.fillMaxSize()
                .padding(bottom = if(shouldShowBottomBar.value) 60.dp else 0.dp)
        ){
            NavHost(navController = navController, startDestination = startScreen) {

                navigation<SubNavigation.LoginSignUpScreen>(startDestination = Routes.LoginScreen){

                    composable<Routes.LoginScreen> {
                        LoginScreen(navController = navController)
                    }

                    composable<Routes.SignUpScreen>{
                        SignUpScreen(navController = navController)
                    }

                }

                navigation<SubNavigation.MainHomeScreen>(startDestination = Routes.HomeScreen){

                    composable<Routes.HomeScreen>{
                        HomeScreen(navController = navController)
                    }

                    composable<Routes.ProfileScreen>{
                        ProfileScreen(navController = navController, firebaseAuth = firebaseAuth)
                    }

                    composable<Routes.FavListScreen>{
                        GetAllFavScreen(navController = navController)
                    }

                    composable<Routes.CartScreen>{
                        CartScreen(navController = navController)
                    }

                    composable<Routes.SeeAllProductScreen>{
                        GetAllProductScreen(navController = navController)
                    }

                    composable<Routes.AllCategoriesScreen>{
                        CategoriesScreen(navController = navController)
                    }

                    composable<Routes.ProductDetailsScreen>{
                        val product : Routes.ProductDetailsScreen = it.toRoute()
                        EachProductDetailsScreen(navController = navController, productId = product.productId)
                    }

                    composable<Routes.EachCategoryItemsScreen>{
                        val category : Routes.EachCategoryItemsScreen = it.toRoute()
                        EachCategoryProductScreen(navController = navController, categoryName = category.categoryName)
                    }

                    composable<Routes.CheckoutScreen>{
                        val product : Routes.CheckoutScreen = it.toRoute()
                        CheckoutScreen(navController = navController, productId = product.productId)
                    }

                }
            }
        }

    }

}