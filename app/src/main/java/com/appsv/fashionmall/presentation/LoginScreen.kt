package com.appsv.fashionmall.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.appsv.fashionmall.R
import com.appsv.fashionmall.domain.models.UserData
import com.appsv.fashionmall.presentation.navigation.SubNavigation
import com.appsv.fashionmall.presentation.utils.CustomTextField
import com.appsv.fashionmall.presentation.utils.SuccessAlertDialog
import com.appsv.fashionmall.presentation.viewmodel.FashionMallViewModel

@Composable
fun LoginScreen(navController: NavHostController, viewModel: FashionMallViewModel = hiltViewModel()) {

    val state = viewModel.loginScreenState.collectAsStateWithLifecycle()
    val showDialog = remember { mutableStateOf(false) }

    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    if(state.value.isLoading){

        Box(modifier = Modifier.fillMaxSize()){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }else if(state.value.errorMessage != null){

        Box(modifier = Modifier.fillMaxSize()){
            Text(text = state.value.errorMessage!!)
        }
    }else if(state.value.userData != null){

        SuccessAlertDialog (onClick = {

            navController.navigate(SubNavigation.MainHomeScreen){
                popUpTo(SubNavigation.LoginSignUpScreen){
                    inclusive = true
                }
            }
        })
    }else{

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){

            Text(
                text = "Login",
                fontSize = 24.sp,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.Start)
            )

            CustomTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                singleLine = true,
                leadingIcon = Icons.Default.Email,

                )
            Spacer(modifier = Modifier.height(8.dp))

            CustomTextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                leadingIcon = Icons.Default.Lock,

                )


            Text(
                text = "Forget Password?",
                modifier = Modifier.align(Alignment.End)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if(email.isNotBlank() && password.isNotBlank()){

                        val userData = UserData(
                            firstName = "",
                            lastName = "",
                            email = email,
                            password = password,
                            phoneNumber = ""
                        )
                        viewModel.loginUser(userData)
                        //verify the user
                        Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    colorResource(id = R.color.button_color)
                ),
            ){
                Text(
                    text = "Login",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.button_text_color)
                    )
                )
            }


            Row(verticalAlignment = Alignment.CenterVertically){

                Text(text = "Don't have an account?")

                TextButton(
                    onClick = {
                        navController.navigate(SubNavigation.LoginSignUpScreen){
                            popUpTo(SubNavigation.LoginSignUpScreen){
                                inclusive = true
                            }
                        }
                    },

                    ) {
                    Text(text = "SignUp", style = TextStyle(color = colorResource(id = R.color.button_color)))
                }

            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ){

                HorizontalDivider(modifier = Modifier.weight(1f))

                Text(text = "Or", modifier = Modifier.padding(horizontal = 8.dp))

                HorizontalDivider(modifier = Modifier.weight(1f))

            }

            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),

                ){
                Image(painter = painterResource(id = R.drawable.ic_google), contentDescription = null, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = "Login  with Google")
            }


        }

    }


}