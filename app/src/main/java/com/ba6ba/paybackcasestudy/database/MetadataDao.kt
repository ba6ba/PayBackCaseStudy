package com.ba6ba.paybackcasestudy.database

import androidx.room.*

@Dao
interface MetadataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg metadata: Metadata)

    @Query("SELECT * FROM meta_data WHERE `query` = :query")
    suspend fun get(query: String): Metadata?

    @Query("SELECT * FROM meta_data")
    suspend fun getAll(): List<Metadata>?

    @Update
    suspend fun update(imagesMetadata: Metadata)

    @Query("DELETE FROM meta_data")
    suspend fun delete()
}