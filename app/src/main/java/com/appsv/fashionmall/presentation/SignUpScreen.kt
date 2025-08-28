package com.appsv.fashionmall.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
import androidx.navigation.NavController
import com.appsv.fashionmall.R
import com.appsv.fashionmall.domain.models.UserData
import com.appsv.fashionmall.presentation.navigation.Routes
import com.appsv.fashionmall.presentation.navigation.SubNavigation
import com.appsv.fashionmall.presentation.utils.CustomTextField
import com.appsv.fashionmall.presentation.utils.SuccessAlertDialog
import com.appsv.fashionmall.presentation.viewmodel.FashionMallViewModel

@Composable
fun SignUpScreen(navController: NavController, viewModel: FashionMallViewModel = hiltViewModel()) {

    val state = viewModel.signUpScreenState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

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

            navController.navigate(SubNavigation.MainHomeScreen)
        })
    }else{

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Text(
                text = "Sign Up",
                fontSize = 24.sp,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.Start)
            )

            CustomTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = "First Name",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                singleLine = true,
                leadingIcon = Icons.Default.Person,

                )

            CustomTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = "Last Name",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                singleLine = true,
                leadingIcon = Icons.Default.Person,

                )

            CustomTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                singleLine = true,
                leadingIcon = Icons.Default.Email,

                )

            CustomTextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                leadingIcon = Icons.Default.Lock,

                )

            CustomTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirm Password",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                leadingIcon = Icons.Default.Lock,

                )

            CustomTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = "Phone Number",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                singleLine = true,
                leadingIcon = Icons.Default.Phone,

                )

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = {
                    if(firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && phoneNumber.isNotEmpty()){

                        if(password == confirmPassword){

                            val userData = UserData(
                                firstName = firstName,
                                lastName = lastName,
                                email = email,
                                password = password,
                                phoneNumber = phoneNumber
                            )
                            viewModel.createUser(userData)

                            Toast.makeText(context, "Sign Up Successful", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(context, "Password does not match", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    colorResource(id = R.color.button_color)),
            ){
                Text(
                    text = "Sign Up",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.button_text_color)
                    )
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically){

                Text(text = "Already have an account?")

                TextButton(
                    onClick = {
                        navController.navigate(Routes.LoginScreen){
                            popUpTo(Routes.LoginScreen){
                                inclusive = true
                            }
                        }
                    },

                    ) {
                    Text(text = "Login", style = TextStyle(color = colorResource(id = R.color.button_color)))
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