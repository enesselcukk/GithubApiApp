package com.enesselcuk.githubapiapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.enesselcuk.githubapiapp.data.local.detail.DetailDao
import com.enesselcuk.githubapiapp.data.local.detail.DetailEntity
import com.enesselcuk.githubapiapp.data.local.favorite.FavoriteDao
import com.enesselcuk.githubapiapp.data.local.favorite.FavoriteEntity
import com.enesselcuk.githubapiapp.data.local.item.GithubDao
import com.enesselcuk.githubapiapp.data.local.item.pagingLocal.RemoteKeysDao
import com.enesselcuk.githubapiapp.data.local.item.pagingLocal.RemoteKeysEntity
import com.enesselcuk.githubapiapp.data.remote.model.searchModel.Item


@Database(
    entities = [
        Item::class,
        RemoteKeysEntity::class,
        FavoriteEntity::class,
        DetailEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class GithubDataBase : RoomDatabase() {

    abstract fun githubDao(): GithubDao
    abstract fun remoteKeysDao(): RemoteKeysDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun detailDao(): DetailDao

    companion object {
        @Volatile
        private var INSTANCE: GithubDataBase? = null

        fun getDatabase(context: Context): GithubDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GithubDataBase::class.java, "github_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}