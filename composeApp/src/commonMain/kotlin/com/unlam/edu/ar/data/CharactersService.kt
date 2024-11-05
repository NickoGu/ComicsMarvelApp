package com.unlam.edu.ar.data

import GetTimeStamp
import Md5
import com.unlam.edu.ar.Character
import com.unlam.edu.ar.IMarvelRepository
import com.unlam.edu.ar.PRIVATE_KEY
import com.unlam.edu.ar.PUBLIC_KEY
import io.ktor.utils.io.core.toByteArray


class CharactersService(private val repository: IMarvelRepository) {

    suspend fun getCharacters(): List<Character> {
        //TODO: EXPECT ACTUAL
        val timestamp = GetTimeStamp().getTimeStamp()
        val characters = repository.getCharacters(
            timestamp,
            Md5().md5(timestamp.toString() + PRIVATE_KEY + PUBLIC_KEY),

            )
        return sort(characters)
    }

    suspend fun getComics(): List<Comic> {
        val timestamp = GetTimeStamp().getTimeStamp()
        val comics = repository.getComics(
            timestamp,
            Md5().md5(timestamp.toString() + PRIVATE_KEY + PUBLIC_KEY),

            )
        return comics
    }

    suspend fun getCharacterById(characterId: Int): Character {
        val timestamp = GetTimeStamp().getTimeStamp()
        return repository.getCharacterById(
            timestamp,
            Md5().md5(timestamp.toString() + PRIVATE_KEY + PUBLIC_KEY),

            characterId
        )
    }

    suspend fun getComicById(comicId: Int): Comic {
        val timestamp = GetTimeStamp().getTimeStamp()
        return repository.getComicById(
            timestamp,
            Md5().md5(timestamp.toString() + PRIVATE_KEY + PUBLIC_KEY),

            comicId
        )
    }

    suspend fun getCharacterFromComic(comicId: Int): List<Character> {
        val timestamp = GetTimeStamp().getTimeStamp()
        return repository.getCharacterFromComic(
            timestamp,
            Md5().md5(timestamp.toString() + PRIVATE_KEY + PUBLIC_KEY),

            comicId
        )
    }

    suspend fun getComicsFromCharacter(characterId: Int): List<Comic> {
        val timestamp = GetTimeStamp().getTimeStamp()
        return repository.getComicsFromCharacter(
            timestamp,
            Md5().md5(timestamp.toString() + PRIVATE_KEY + PUBLIC_KEY),
            characterId
        )
    }

//    private fun md5(string: String): String {
//        val MD5 = "MD5"
//        try {
//            // Create MD5 Hash
//            val digest = MessageDigest.getInstance(MD5)
//            digest.update(string.toByteArray())
//            val messageDigest = digest.digest()
//
//            // Create Hex String
//            val hexString = StringBuilder()
//            for (aMessageDigest in messageDigest) {
//                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
//                while (h.length < 2) h = "0$h"
//                hexString.append(h)
//            }
//            return hexString.toString()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return ""
//    }

    private fun sort(characters: List<Character>): List<Character> {
        return characters.sortedWith(CharacterComparator())
    }

    /**
     * Los personajes se ordenan de la siguiente manera:
     * - Primero los que tienen descripción, y luego los que no.
     * - Los que tienen descripción a su vez se ordenan ascendentemente por su id.
     * - Los que NO tienen descripción se ordenan descendentemente por su id.
     */
    private class CharacterComparator : Comparator<Character> {
        override fun compare(c1: Character, c2: Character): Int {
            if (c1.description.isEmpty() && c2.description.isEmpty()) {
                return c2.id.compareTo(c1.id)
            }
            if (c1.description.isNotEmpty() && c2.description.isNotEmpty()) {
                return c1.id.compareTo(c2.id)
            }
            if (c1.description.isNotEmpty() && c2.description.isEmpty()) {
                return -1
            }
            return 1
        }

    }
}