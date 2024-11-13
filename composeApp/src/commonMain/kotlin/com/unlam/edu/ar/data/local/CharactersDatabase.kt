package com.unlam.edu.ar.data.local

import com.unlam.edu.ar.data.models.Character

interface CharactersDatabase {
    suspend fun insertCharacters(characters: List<Character>): Unit
    suspend fun deleteAllCharacters(): Unit
    suspend fun deleteCharacter(id: Long): Unit
    suspend fun getAllCharacters(): List<Character>
    suspend fun getCharacter(id: Long): Character
}