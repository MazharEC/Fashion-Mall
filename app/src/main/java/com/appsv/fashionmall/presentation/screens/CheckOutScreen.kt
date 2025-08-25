package com.appsv.fashionmall.presentation.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.appsv.fashionmall.presentation.viewmodel.FashionMallViewModel

@Composable
fun CheckoutScreen(
    viewModel: FashionMallViewModel = hiltViewModel(),
    navController: NavController,
    productId: String
) {

    val state = viewModel.getProductByIdState.collectAsStateWithLifecycle()
    val productdata = state.value.userData

    val email = remember { mutableStateOf("") }
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val zipCode = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    val selectedMethod = remember { mutableStateOf("Standard FREE delivery over $50") }


    LaunchedEffect(key1 = Unit){
        viewModel.getProductById(productId)
    }

    Scaffold(

    ){innerPadding ->

    }
}