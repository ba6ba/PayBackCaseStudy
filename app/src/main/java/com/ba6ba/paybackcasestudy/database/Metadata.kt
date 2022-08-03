package com.ba6ba.paybackcasestudy.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meta_data")
data class Metadata(
    @PrimaryKey
    val query: String,
    val lastFetchedPage: Int
)