package com.example.chatapp.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatapp.R
import com.example.chatapp.home.HomeActivity
import com.example.chatapp.register.RegisterActivity
import com.example.chatapp.register.ui.theme.ChatAppTheme

class LoginActivity : ComponentActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                LoginContent(navigator = this)
            }
        }
    }

    override fun openHomeActivity() {
        val intent = Intent(this@LoginActivity,HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun openRegisterActivity() {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginContent(viewModel: LoginViewModel = viewModel(),navigator: Navigator) {
    viewModel.navigator = navigator
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            Text(
                text = "Login",
                modifier = Modifier
                    .padding(vertical = 28.dp)
                    .fillMaxWidth(),
                style = TextStyle(
                    color = colorResource(id = R.color.white),
                    textAlign = TextAlign.Center,
                    fontSize = 26.sp
                )
            )
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.bg_app),
                    contentScale = ContentScale.FillBounds
                )
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.35F))
            ChatAuthTextField(
                state = viewModel.emailState,
                label = "Email",
                errorState = viewModel.emailError
            )
            ChatAuthTextField(
                state = viewModel.passwordState,
                label = "Password",
                errorState = viewModel.passwordError,
                isPassword = true
            )
            Spacer(modifier = Modifier.height(40.dp))
            ChatButton(buttonText = "Login") {
                viewModel.sendAuthDataToFirebase()
            }
            TextButton(onClick = {
                viewModel.navigateToRegisterActivity()
            }, modifier = Modifier.padding(horizontal = 16.dp))
            {
                Text(
                    text = "Create a New Account",
                    style = TextStyle(color = Color.Gray), fontSize = 16.sp
                )
            }
            LoadingDialog()
            ChatAlertDialog()
        }
    }
}

@Composable
fun ChatButton(buttonText: String, onButtonClick: () -> Unit) {
    Button(
        onClick = {
            onButtonClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.blue),
            contentColor = colorResource(
                id = R.color.white
            )
        ),
        contentPadding = PaddingValues(vertical = 16.dp),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Text(
            text = buttonText, style = TextStyle(
                color = colorResource(id = R.color.white),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.width(160.dp))
        Icon(
            painter = painterResource(id = R.drawable.ico_arrow_right),
            contentDescription = "icon arrow"
        )
    }
}

@Composable
fun LoadingDialog(viewModel: LoginViewModel = viewModel()) {

    if (viewModel.showLoading.value)
        Dialog(onDismissRequest = { }) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
                    .background(
                        color = colorResource(id = R.color.white),
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(35.dp),
                    color = colorResource(id = R.color.blue)
                )

            }

        }
}

@Composable
fun ChatAuthTextField(
    state: MutableState<String>,
    label: String,
    errorState: MutableState<String>,
    isPassword: Boolean = false
) {

    OutlinedTextField(
        value = state.value,
        onValueChange = {
            state.value = it
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = (colorResource(id = R.color.blue))
        ),
        label = {
            Text(
                text = label, style = TextStyle(color = Color.Gray), fontSize = 14.sp
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, 14.dp),
        isError = errorState.value.isNotEmpty(),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = if (isPassword) KeyboardOptions(keyboardType = KeyboardType.Password)
        else KeyboardOptions()
    )
    if (errorState.value.isNotEmpty()) {
        Text(
            text = errorState.value,
            style = TextStyle(color = Color.Red),
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}


@Composable
fun ChatAlertDialog(viewModel: LoginViewModel = viewModel()) {
    if (viewModel.message.value.isNotEmpty())
        AlertDialog(onDismissRequest = {
            viewModel.message.value = ""
        }, confirmButton = {
            TextButton(onClick = {
                viewModel.message.value = ""
            }) {
                Text(text = "ok")
            }
        },
            text = {
                Text(text = viewModel.message.value)
            }
        )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview2() {
    ChatAppTheme {
        // RegisterContent()
        ChatAlertDialog()

    }
}