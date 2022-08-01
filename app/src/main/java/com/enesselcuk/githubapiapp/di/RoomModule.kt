package com.enesselcuk.githubapiapp.di

import android.content.Context
import androidx.room.Room
import com.enesselcuk.githubapiapp.data.local.GithubDataBase
import com.enesselcuk.githubapiapp.data.local.detail.DetailDao
import com.enesselcuk.githubapiapp.data.local.favorite.FavoriteDao
import com.enesselcuk.githubapiapp.data.local.item.GithubDao
import com.enesselcuk.githubapiapp.data.local.item.pagingLocal.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@[Module InstallIn(SingletonComponent::class)]
object RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): GithubDataBase {
        return Room.databaseBuilder(context, GithubDataBase::class.java, "github_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideGithubDao(
        db: GithubDataBase,
    ): GithubDao = db.githubDao()

    @Provides
    @Singleton
    fun provideRemoteKeysDao(
        db: GithubDataBase,
    ): RemoteKeysDao = db.remoteKeysDao()

    @Provides
    @Singleton
    fun provideDetailDao(
        db: GithubDataBase,
    ): DetailDao = db.detailDao()


    @Provides
    @Singleton
    fun provideFavoriteDao(
        db: GithubDataBase,
    ): FavoriteDao = db.favoriteDao()
}