package com.ba6ba.paybackcasestudy.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images_meta_data")
data class ImagesMetadata(
    @PrimaryKey
    val query: String,
    val lastFetchedPage: Int
)