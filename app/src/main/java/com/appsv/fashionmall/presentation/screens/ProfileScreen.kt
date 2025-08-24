package com.appsv.fashionmall.presentation.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.appsv.fashionmall.presentation.viewmodel.FashionMallViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(
    viewModel: FashionMallViewModel = hiltViewModel(),
    navController: NavController,
    firebaseAuth: FirebaseAuth
) {



}