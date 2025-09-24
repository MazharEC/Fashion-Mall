package com.appsv.fashionmall

import com.appsv.fashionmall.presentation.navigation.Routes
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appsv.fashionmall.presentation.navigation.App
import com.appsv.fashionmall.presentation.utils.NotificationPermissionRequest
import com.appsv.fashionmall.ui.theme.FashionMallTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FashionMallTheme {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Black)
                ) {
                    MainScreen(firebaseAuth = firebaseAuth)
                }
            }
        }
    }
}

@Composable
fun MainScreen(firebaseAuth: FirebaseAuth) {
    val showSplash = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            showSplash.value = false
        }, 2000) // Reduced from 3000ms to 2000ms for better UX
    }

    if (showSplash.value) {
        SplashScreen()
    } else {
        NotificationPermissionRequest()
        App(firebaseAuth)
    }
}


@Composable
fun SplashScreen() {

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.splashimg),
            contentDescription = "App logo",
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.Center)
        )
        Spacer(modifier = Modifier.height(16.dp))

        BasicText(
            text = "Welcome to the Fashion Mall",
            style = MaterialTheme.typography.titleLarge.copy(
                color = colorResource(id = R.color.button_color),
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "From", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Row {
                Icon(
                    painter = painterResource(id = R.drawable.mazhar),
                    contentDescription = "logo of M",
                    modifier = Modifier.size(24.dp)

                )
                Text(text = "azhar", modifier = Modifier.padding(top = 5.dp), fontSize = 18.sp, fontWeight = FontWeight.Bold)

            }
        }
    }
}


