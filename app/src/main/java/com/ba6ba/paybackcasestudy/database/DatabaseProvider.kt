package com.ba6ba.paybackcasestudy.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ba6ba.paybackcasestudy.images.data.ImageResponseItem
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

private const val PAY_BACK_DATABASE = "pay_back_database"

@Database(
    entities = [ImageResponseItem::class, Metadata::class],
    version = 1
)
abstract class PayBackCaseStudyDatabase : RoomDatabase() {
    abstract fun getImagesDao(): ImagesDao
    abstract fun getMetadataDao(): MetadataDao
}

interface DatabaseProvider : Provider<PayBackCaseStudyDatabase>

@Singleton
class DefaultDatabaseProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : DatabaseProvider {
    override fun get(): PayBackCaseStudyDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            PayBackCaseStudyDatabase::class.java,
            PAY_BACK_DATABASE
        ).build()
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface DatabaseModule {

    @Binds
    fun bindDatabaseProvider(default: DefaultDatabaseProvider): DatabaseProvider
}