package com.ba6ba.paybackcasestudy.database

import androidx.room.*

@Dao
interface ImagesMetadataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg metadata: ImagesMetadata)

    @Query("SELECT * FROM images_meta_data WHERE `query` = :query")
    suspend fun get(query: String): ImagesMetadata?

    @Query("SELECT * FROM images_meta_data")
    suspend fun getAll(): List<ImagesMetadata>?

    @Update
    suspend fun update(imagesMetadata: ImagesMetadata)

    @Query("DELETE FROM images_meta_data")
    suspend fun delete()
}