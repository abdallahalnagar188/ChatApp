package com.example.chatapp.chat

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
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
import com.example.chatapp.model.Constants
import com.example.chatapp.model.DataUtils
import com.example.chatapp.model.Message
import com.example.chatapp.model.Room
import java.text.SimpleDateFormat
import java.util.Date

class ChatActivity : ComponentActivity(), Navigator {
lateinit var room: Room

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val room = intent.getParcelableExtra<Room>(Constants.EXTRA_ROOM)!!
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
fun ChatScreenContent(
    viewModel: ChatViewModel = viewModel(),
    navigator: Navigator,
    room: Room
) {
    viewModel.navigator = navigator
    viewModel.room = room
    viewModel.getMessageFromFirestore()
    Scaffold(
        contentColor = Color.White,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    viewModel.navigateUp()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_back),
                        contentDescription = "Icon back"
                    )
                }
                Text(
                    text = room.name ?: "",
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
        }, bottomBar = {
            Row() {
                ChatSendMessageBar()
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
                .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding())
        ) {
            ChatLazyColumn()

        }
    }
}

@Composable
fun ChatSendMessageBar(viewModel: ChatViewModel = viewModel()) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = viewModel.messageFieldState.value,
            onValueChange = {
                viewModel.messageFieldState.value = it
            },
            shape = RoundedCornerShape(
                bottomStart = 0.dp,
                topStart = 0.dp,
                topEnd = 15.dp,
                bottomEnd = 0.dp
            ), modifier = Modifier.padding(12.dp)
        )
        Button(
            onClick = {
                viewModel.addMessageToFirestore()
            },
            modifier = Modifier
                .padding(2.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blue)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Send")

            Icon(
                painter = painterResource(id = R.drawable.icon_send),
                contentDescription = "icon send",
            )
        }
    }
}

@Composable
fun ChatLazyColumn(viewModel: ChatViewModel = viewModel()) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(), reverseLayout = true
    ) {
        items(viewModel.messageListState.value.size) {
            val item = viewModel.messageListState.value[it]
            if (item.senderId == DataUtils.appUser?.id) {
                SendMessageRow(message = item)

            } else {
                ReceivedMessageRow(message = item)

            }
        }
    }

}

@SuppressLint("SimpleDateFormat")
@Composable
fun ReceivedMessageRow(message: Message) {
    val date = Date(message.dateTime ?: 0)
    val simpleTimeFormat = SimpleDateFormat("hh:mm a")
    val dateString = simpleTimeFormat.format(date)
    Column {
        Text(
            text = message.senderName ?: "",
            style = TextStyle(color = Color.Black), fontSize = 14.sp,
            modifier = Modifier.align(
                Alignment.Start
            )
        )
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = message.content ?: "",
                modifier = Modifier
                    .background(
                        colorResource(id = R.color.gray),
                        shape = RoundedCornerShape(
                            bottomStart = 0.dp,
                            topStart = 25.dp,
                            topEnd = 25.dp,
                            bottomEnd = 25.dp
                        )
                    )
                    .padding(vertical = 6.dp, horizontal = 8.dp)
                    .align(Alignment.CenterVertically)
                    .padding(vertical = 8.dp, horizontal = 8.dp),
                style = TextStyle(color = colorResource(id = R.color.black), fontSize = 18.sp)
            )
            Text(
                text = dateString,
                modifier = Modifier.align(Alignment.CenterVertically),
                style = TextStyle(color = Color.Black)
            )
        }
    }

}

@SuppressLint("SimpleDateFormat")
@Composable
fun SendMessageRow(message: Message) {
    val date = Date(message.dateTime ?: 0)
    val simpleTimeFormat = SimpleDateFormat("hh:mm a")
    val dateString = simpleTimeFormat.format(date)
    Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = dateString,
            modifier = Modifier.align(Alignment.CenterVertically),
            style = TextStyle(color = Color.Black)
        )
        Text(
            text = message.content ?: "",
            modifier = Modifier
                .padding(vertical = 6.dp, horizontal = 8.dp)
                .align(Alignment.CenterVertically)
                .background(
                    colorResource(id = R.color.blue),
                    shape = RoundedCornerShape(
                        bottomStart = 25.dp,
                        topStart = 25.dp,
                        topEnd = 25.dp,
                        bottomEnd = 0.dp
                    )
                )
                .padding(vertical = 8.dp, horizontal = 8.dp),
            style = TextStyle(color = colorResource(id = R.color.white), fontSize = 18.sp)
        )
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview5() {
    ChatAppTheme {

    }
}