package com.example.chatapp.addRoom

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.chatapp.addRoom.ui.theme.ChatAppTheme
import com.example.chatapp.register.ChatAuthTextField

class AddRoomActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                // A surface container using the 'background' color from the theme
                AddRoomContent()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddRoomContent(viewModel: AddRoomViewModel = viewModel()) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
            ) {
                IconButton(
                    onClick = { viewModel.navigateUp() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_back),
                        contentDescription = "icon back", tint = Color.White
                    )
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 18.dp),
                    text = "Add New Room",
                    style = TextStyle(color = Color.White, fontSize = 26.sp),
                    textAlign = TextAlign.Center
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.bg_app),
                    contentScale = ContentScale.FillBounds
                )
        ) {
            AddRoomCard()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRoomCard(viewModel: AddRoomViewModel = viewModel()) {
    Card(
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, top = 90.dp, end = 30.dp, bottom = 110.dp),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(
                text = "Create New Room",
                style = TextStyle(
                    color = Color(0xFF383838),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(vertical = 14.dp)
                    .align(Alignment.CenterHorizontally),
            )
            Image(
                painter = painterResource(id = R.drawable.icon_new_room),
                contentDescription = "Add Room",
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .height(100.dp)
                    .width(200.dp)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.FillBounds
            )
            ChatAuthTextField(
                state = viewModel.titleState,
                label = "Enter Room Name",
                errorState = viewModel.titleError
            )

            ChatAuthTextField(
                state = viewModel.descriptionState,
                label = "Enter Room Description",
                errorState = viewModel.descriptionError
            )
            ExposedDropdownMenuBox(expanded = viewModel.isExpanded.value,
                onExpandedChange = {
                    viewModel.isExpanded.value = !viewModel.isExpanded.value
                }) {
                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = (colorResource(id = R.color.blue))
                    ),
                    value = viewModel.selectedItem.value.name ?: "",
                    onValueChange = {},
                    modifier = Modifier
                        .menuAnchor()
                        .padding(horizontal = 26.dp)
                        .align(Alignment.CenterHorizontally),
                    readOnly = true,
                    label = { Text(text = "") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewModel.isExpanded.value) },
                )
                ExposedDropdownMenu(
                    expanded = viewModel.isExpanded.value,
                    onDismissRequest = { viewModel.isExpanded.value = false }) {

                }

            }
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue)),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.85F)
                    .padding(vertical = 20.dp)
            ) {
                Text(
                    text = "Create",
                    style = TextStyle(fontSize = 18.sp),
                    color = colorResource(id = R.color.white)
                )
            }
        }

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview4() {
    ChatAppTheme {
        AddRoomContent()

    }
}