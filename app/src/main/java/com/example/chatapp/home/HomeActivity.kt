package com.example.chatapp.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatapp.R
import com.example.chatapp.addRoom.AddRoomActivity
import com.example.chatapp.home.ui.theme.ChatAppTheme

class HomeActivity : ComponentActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                // A surface container using the 'background' color from the theme
                HomeContent(navigator = this)
            }
        }
    }

    override fun goToAddRoomScreen() {
        val intent = Intent(this@HomeActivity, AddRoomActivity::class.java)
        startActivity(intent)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeContent(viewModel: HomeViewModel = viewModel(), navigator: Navigator) {
    viewModel.navigator = navigator
    Scaffold(
        topBar = {
            Text(
                text = "Home",
                modifier = Modifier
                    .padding(vertical = 28.dp)
                    .fillMaxWidth(),
                style = TextStyle(
                    color = colorResource(id = R.color.white),
                    textAlign = TextAlign.Center,
                    fontSize = 26.sp
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.navigator?.goToAddRoomScreen()
                },
                containerColor = colorResource(id = R.color.blue),
                contentColor = colorResource(
                    id = R.color.white
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_add),
                    contentDescription = "icon add"
                )
            }
        }, modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.bg_app),
                    contentScale = ContentScale.FillBounds
                )
        ) {

        }

    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview3() {
    ChatAppTheme {
        HomeContent(navigator = object : Navigator {
            override fun goToAddRoomScreen() {

            }

        })

    }
}