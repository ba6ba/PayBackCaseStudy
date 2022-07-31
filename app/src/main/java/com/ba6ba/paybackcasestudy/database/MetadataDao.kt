package com.ba6ba.paybackcasestudy.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MetadataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(metaData: List<Metadata>)

    @Query("SELECT TOP 1 FROM meta_data")
    suspend fun getMetaData(query: String): Metadata?

    @Query("DELETE FROM meta_data")
    suspend fun clearMetaData()
}