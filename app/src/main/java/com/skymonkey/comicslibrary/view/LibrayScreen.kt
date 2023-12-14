package com.skymonkey.comicslibrary.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.skymonkey.comicslibrary.Destination
import com.skymonkey.comicslibrary.model.CharactersApiResponse
import com.skymonkey.comicslibrary.model.CharactersData
import com.skymonkey.comicslibrary.model.api.NetworkResult
import com.skymonkey.comicslibrary.viewmodel.LibraryViewModel

@Composable
fun LibraryScreen(
    navHostController: NavHostController,
    viewModel: LibraryViewModel,
    paddingValues: PaddingValues
) {
    val result by viewModel.result.collectAsState()
    val text by viewModel.queryText.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = text,
            onValueChange = viewModel::onQueryUpdate,
            label = { Text(text = "Character search") },
            placeholder = { Text(text = "Character") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when(result) {
                is NetworkResult.Initial -> {
                    Text(text = "Search for a character")
                }
                is NetworkResult.Success -> {
                    result.data?.also {
                        ShowCharacterList(it.data, it.attributionText, navHostController)
                    } ?: run {
                        Text(text = "Error, no character data: ${result.message}")
                    }
                }
                is NetworkResult.Loading -> {
                    CircularProgressIndicator()
                }
                is NetworkResult.Error -> {
                    Text(text = "Error: ${result.message}")
                }
            }
        }

    }
}

@Composable
fun ShowCharacterList(
    data: CharactersData?,
    attributionText: String?,
    navHostController: NavHostController
) {
   data?.results?.let { characters ->
        LazyColumn(
            modifier = Modifier.background(Color.LightGray),
            verticalArrangement = Arrangement.Top
        ) {
            attributionText?.let {
                item {
                    AttributionText(text = it)
                }
            }

            // key parameter must be provided to optimize recomposition
            // key is used to know which item has moved or changed
            // key not REQUIRED but the character position will be used instead
            items(items = characters, key = {it.id}) { character ->
                val imageUrl = character.thumbnail?.path + "." + character.thumbnail?.extension
                val title = character.name
                val description = character.description
                val context = LocalContext.current
                val id = character.id

                Column(modifier = Modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.White)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable {
                        if (character.id != null)
                            navHostController.navigate(Destination.CharacterDetail.createRoute(id))
                        else
                            Toast.makeText(context, "Sorry, character not available!", Toast.LENGTH_SHORT).show()
                    }
                ){
                    Row(modifier = Modifier.fillMaxWidth()) {
                        CharacterImage(
                            url = imageUrl,
                            modifier = Modifier.padding(4.dp).width(100.dp)
                        )
                        Column(modifier = Modifier.padding(4.dp)) {
                            Text(text = title ?: "", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        }
                    }
                    Text(text = description ?: "", maxLines = 4, fontSize = 14.sp)
                }

            }
        }
    }
}