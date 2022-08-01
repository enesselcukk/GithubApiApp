package com.enesselcuk.githubapiapp.data.local.favorite

import androidx.room.*


@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFav(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM favorite WHERE id LIKE :id")
    suspend fun deleteId(id: Int?)

    @Query("SELECT * FROM favorite")
    fun allFav(): List<FavoriteEntity>

    @Delete
    fun delete(fav: FavoriteEntity?):Int


}
