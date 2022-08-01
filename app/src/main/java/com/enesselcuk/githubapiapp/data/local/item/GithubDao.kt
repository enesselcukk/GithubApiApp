package com.enesselcuk.githubapiapp.data.local.item

import androidx.paging.PagingSource
import androidx.room.*
import com.enesselcuk.githubapiapp.data.remote.model.searchModel.Item


@Dao
interface GithubDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGithub(githubEntity: Item)

    @Query("DELETE FROM githubEntity")
    suspend fun clearRepos()

    @Query("SELECT * FROM githubEntity WHERE login LIKE :name ")
    fun searchByName(name: String?): PagingSource<Int, Item>

    @Query("SELECT * FROM githubEntity")
    fun allGithub(): List<Item>

}
