package com.unlam.edu.ar.ui.screens



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.unlam.edu.ar.Character
import com.unlam.edu.ar.MarvelApiClient
import com.unlam.edu.ar.MarvelRepositoryImpl
import com.unlam.edu.ar.data.CharactersService
import com.unlam.edu.ar.data.Comic
import kotlinx.coroutines.launch


@Composable
fun SuperheroDetailsScreen(paddingValue: PaddingValues, controller: NavController, superheroid: Int?) {
    var superhero by remember { mutableStateOf<Character?>(null) }
    val scope = rememberCoroutineScope()
    var comics by remember { mutableStateOf<List<Comic>>(emptyList()) }

    LaunchedEffect(key1 = Unit) {
        scope.launch {
            val client = MarvelApiClient()
            val repository = MarvelRepositoryImpl(client)
            val service = CharactersService(repository)
            superhero = service.getCharacterById(superheroid!!)
            comics = service.getComicsFromCharacter(superheroid!!)
        }
    }

      Scaffold { paddingValue ->
        Surface {
            LazyColumn(
                contentPadding = paddingValue,
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    AsyncImage(
                        modifier = Modifier.fillMaxWidth().height(450.dp),
                        contentScale = ContentScale.Crop,
                        model = superhero?.thumbnailUrl ?: "",
                        contentDescription = superhero?.name ?: "",
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(text = superhero?.name ?: "Loading...", style = TextStyle(fontSize = 20.sp))
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = if(superhero?.description == null || superhero?.description == ""){
                            "No description"
                        }  else {
                            superhero?.description!!
                        },
                        style = TextStyle(fontSize = 16.sp),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Column (
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Comics", style = TextStyle(fontSize = 20.sp))
                        if(comics.isEmpty()){
                            Text("No comics available")
                        }
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .padding(
                                    start = 12.dp,
                                    end = 12.dp
                                )
                        ){
                            items(comics.size){ index ->
                                Column (
                                    modifier = Modifier.padding(4.dp).width(120.dp)
                                ){
                                    AsyncImage(
                                        model = comics[index]?.thumbnailUrl,
                                        contentDescription = comics[index]?.title ?: "",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.clip(RoundedCornerShape(8.dp)).height(200.dp).width(120.dp).aspectRatio(0.6f).clickable {
                                            controller.navigate("comic_details/${comics[index]?.id}"){
                                                launchSingleTop = true

                                            }
                                        },
                                    )
                                    Spacer( modifier = Modifier.height(4.dp))
                                    Text(
                                        comics[index]?.title ?: "Error",
                                        fontSize = 12.sp,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        comics[index]?.description ?: "Error",
                                        fontSize = 10.sp,
                                        maxLines = 5,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }
            }

      }
}

