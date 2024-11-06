package com.unlam.edu.ar.data.repository

import com.unlam.edu.ar.data.models.Comic
import com.unlam.edu.ar.data.models.Character


interface IMarvelRepository {

   //interfaces:
   suspend fun getCharacters(timestamp: Long, md5: String): List<Character>
   suspend fun getComics(timestamp: Long, md5: String): List<Comic>
   suspend fun getCharacterById(timestamp: Long, md5: String, characterId: Int): Character
   suspend fun getComicById(timestamp: Long, md5: String, comicId: Int): Comic
   suspend fun getCharacterFromComic(timestamp: Long, md5: String, comicId: Int): List<Character>
    suspend fun getComicsFromCharacter(timestamp: Long, md5: String, characterId: Int): List<Comic>
}