package com.musin.starwarscharacters.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.musin.starwarscharacters.domain.entity.Character

@Composable
fun CharacterDetailScreen(
    characterId: Int,
    onBackClick: () -> Unit,
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    
    LaunchedEffect(characterId) {
        viewModel.loadCharacter(characterId)
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B0C15))
    ) {
        when (val currentState = state) {
            is CharacterDetailState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xFFFFD700)
                )
            }
            is CharacterDetailState.Error -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(
                        text = "Error: ${currentState.message}",
                        color = Color(0xFFFF4444),
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.loadCharacter(characterId) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700))
                    ) {
                        Text("Retry", color = Color(0xFF0B0C15))
                    }
                }
            }
            is CharacterDetailState.Success -> {
                CharacterDetailContent(
                    character = currentState.character,
                    onBackClick = onBackClick
                )
            }
            else -> {}
        }
        
        // Back button
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Text(
                text = "←",
                color = Color(0xFFFFD700),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CharacterDetailContent(
    character: Character,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .padding(top = 60.dp)
    ) {
        // Character avatar
        Box(
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterHorizontally)
                .background(Color(0xFFFFD700), shape = MaterialTheme.shapes.large),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = character.name.first().toString(),
                color = Color(0xFF0B0C15),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Character name
        Text(
            text = character.name,
            color = Color(0xFFFFD700),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Details section
        DetailSection(title = "Basic Info") {
            DetailRow(label = "Height", value = character.height)
            DetailRow(label = "Mass", value = character.mass)
            DetailRow(label = "Hair Color", value = character.hairColor)
            DetailRow(label = "Skin Color", value = character.skinColor)
            DetailRow(label = "Eye Color", value = character.eyeColor)
            DetailRow(label = "Birth Year", value = character.birthYear)
            DetailRow(label = "Gender", value = character.gender)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        DetailSection(title = "Homeworld") {
            DetailRow(label = "Planet", value = character.homeworld)
        }
        
        if (character.films.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            DetailSection(title = "Films") {
                character.films.forEach { film ->
                    DetailRow(label = "", value = film)
                }
            }
        }
        
        if (character.species.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            DetailSection(title = "Species") {
                character.species.forEach { species ->
                    DetailRow(label = "", value = species)
                }
            }
        }
        
        if (character.vehicles.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            DetailSection(title = "Vehicles") {
                character.vehicles.forEach { vehicle ->
                    DetailRow(label = "", value = vehicle)
                }
            }
        }
        
        if (character.starships.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            DetailSection(title = "Starships") {
                character.starships.forEach { starship ->
                    DetailRow(label = "", value = starship)
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun DetailSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1C2E)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                color = Color(0xFFFFD700),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
fun DetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (label.isNotEmpty()) {
            Text(
                text = "$label:",
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
        Text(
            text = value,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

sealed class CharacterDetailState {
    object Loading : CharacterDetailState()
    data class Success(val character: Character) : CharacterDetailState()
    data class Error(val message: String) : CharacterDetailState()
}
