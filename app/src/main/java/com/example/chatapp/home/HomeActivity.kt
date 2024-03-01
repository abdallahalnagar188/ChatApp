package com.example.chatapp.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatapp.R
import com.example.chatapp.addRoom.AddRoomActivity
import com.example.chatapp.chat.ChatActivity
import com.example.chatapp.home.ui.theme.ChatAppTheme
import com.example.chatapp.model.Category
import com.example.chatapp.model.Constance
import com.example.chatapp.model.Room

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

    override fun navigateToAddRoomScreen() {
        val intent = Intent(this@HomeActivity, AddRoomActivity::class.java)
        startActivity(intent)
    }

    override fun navigateToChatScreen(room: Room) {
        val intent = Intent(this@HomeActivity, ChatActivity::class.java)
        intent.putExtra(Constance.EXTRA_ROOM,room)
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
                    navigator.navigateToAddRoomScreen()
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
            Spacer(modifier = Modifier.height(120.dp))
            ChatRoomLazyGrid(navigator = navigator)

        }

    }

}

@Composable
fun ChatRoomLazyGrid(
    viewModel: HomeViewModel = viewModel(), navigator: Navigator
) {
    viewModel.navigator = navigator
    viewModel.getRoomFromFirestore()

    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(viewModel.roomListState.value.size) {
            ChatRoomCard(room = viewModel.roomListState.value.get(it), navigator = navigator)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomCard(room: Room, viewModel: HomeViewModel = viewModel(), navigator: Navigator) {
    Card(
        onClick = {
            viewModel.navigator = navigator
            navigator.navigateToChatScreen(room)
        },
        modifier = Modifier
            .height(200.dp)
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(
                    id = Category.fromId(room.categoryId ?: Category.MOVIES)?.imageId
                        ?: R.drawable.icon_movie
                ),
                contentDescription = "Room Image",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 8.dp)
                    .height(80.dp)
                    .width(80.dp),
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = room.name ?: "",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 8.dp),
                style = TextStyle(fontSize = 14.sp, color = Color.Black)
            )
            Text(
                text = "Room Image",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 8.dp),
                style = TextStyle(fontSize = 12.sp, color = Color.Gray)
            )

        }

    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview3() {
    ChatAppTheme {
//        HomeContent(navigator = object : Navigator {
//            override fun goToAddRoomScreen() {
//
//            }
//
//        })


    }
}