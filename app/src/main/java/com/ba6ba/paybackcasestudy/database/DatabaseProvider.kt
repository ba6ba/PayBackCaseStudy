package com.ba6ba.paybackcasestudy.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ba6ba.paybackcasestudy.images.data.ImageResponseItem
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

private const val PAY_BACK_DATABASE = "pay_back_database"

@Database(
    entities = [ImageResponseItem::class, ImagesMetadata::class],
    version = 1, exportSchema = false
)
abstract class PayBackCaseStudyDatabase : RoomDatabase() {
    abstract fun getImagesDao(): ImagesDao
    abstract fun getImagesMetadataDao(): ImagesMetadataDao
}

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PayBackCaseStudyDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            PayBackCaseStudyDatabase::class.java,
            PAY_BACK_DATABASE
        ).build()
}