package com.example.chatapp.chat

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatapp.R
import com.example.chatapp.chat.ui.theme.ChatAppTheme
import com.example.chatapp.model.Room

class ChatActivity : ComponentActivity(), Navigator {
lateinit var room: Room

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                // A surface container using the 'background' color from the theme
                ChatScreenContent(navigator = this, room = room)
            }
        }
    }

    override fun navigateUp() {
        finish()
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatScreenContent(viewModel: ChatViewModel = viewModel(),navigator: Navigator,room: Room) {
    viewModel.navigator = navigator
    viewModel.room = room
    Scaffold(contentColor = Color.White,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    navigator.navigateUp()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_back),
                        contentDescription = "Icon back"
                    )
                }
                Text(
                    text = "Movie Room",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp),
                    textAlign = TextAlign.Center
                )
                IconButton(onClick = {

                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_option),
                        contentDescription = "Icon option"
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.bg_app),
                    contentScale = ContentScale.FillBounds
                )
        ) {
            ChatLazyColumn()

        }
    }

}

@Composable
fun ChatLazyColumn(viewModel: ChatViewModel = viewModel()) {
LazyColumn(){
    items(viewModel.messageListState.value.size){
        val item = viewModel.messageListState.value.get(it)
        //if (item)
    }
}

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview5() {
    ChatAppTheme {

    }
}