package com.ba6ba.paybackcasestudy.images.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class Images(
    @PrimaryKey val id: Long,
    val tags: String,
    val previewURL: String,
    val largeImageURL: String,
    val downloads: Long,
    val likes: Long,
    val comments: Long,
    val user: String
)