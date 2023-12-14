package com.skymonkey.comicslibrary

import java.math.BigInteger
import java.security.MessageDigest

/**
 * Security algorithm that's required for Marvel API. Returns hash as String.
 */
fun getHash(timestamp: String, privateKey: String, publicKey: String): String {
    val hashStr = timestamp + privateKey + publicKey
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(hashStr.toByteArray()))
        .toString(16)
        .padStart(32, '0')
}

fun List<String>.comicsToString() = this.joinToString(separator = ", ")