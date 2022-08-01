package com.ba6ba.paybackcasestudy.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ba6ba.paybackcasestudy.images.data.ImageResponseItem

@Dao
interface ImagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<ImageResponseItem>)

    @Query("SELECT * FROM images")
    suspend fun getAll(): List<ImageResponseItem>

    @Query("DELETE FROM images")
    suspend fun clearImages()
}