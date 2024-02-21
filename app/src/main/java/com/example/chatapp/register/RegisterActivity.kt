package com.example.chatapp.register

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatapp.R
import com.example.chatapp.register.ui.theme.ChatAppTheme

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                RegisterContent()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterContent(viewModel: RegisterViewModel = viewModel()) {
    Scaffold(modifier = Modifier.fillMaxSize()) {
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
                state = viewModel.nameState,
                label = "First Name", errorState = viewModel.nameError
            )
            ChatAuthTextField(
                state = viewModel.emailState,
                label = "Email", errorState = viewModel.emailError
            )
            ChatAuthTextField(
                state = viewModel.passwordState,
                label = "Password", errorState = viewModel.passwordError
            )
            Spacer(modifier = Modifier.height(40.dp))
            ChatButton(buttonText = "Register") {
                viewModel.sendAuthDataToFirebase()
            }
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
            containerColor = colorResource(id = R.color.blue), contentColor = colorResource(
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
fun ChatAuthTextField(
    state: MutableState<String>, label: String, errorState: MutableState<String>
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
        isError = errorState.value.isNotEmpty()
    )
    if (errorState.value.isNotEmpty()) {
        Text(
            text = errorState.value,
            style = TextStyle(color = Color.Red),
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview2() {
    ChatAppTheme {
        RegisterContent()
    }
}