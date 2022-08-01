package com.enesselcuk.githubapiapp.data.local.detail

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface DetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetail(detailEntity: DetailEntity)

    @Query("SELECT * FROM detailEntity WHERE login LIKE :name ")
    fun searchByName(name: String?): List<DetailEntity>

    @Query("DELETE FROM detailEntity WHERE id LIKE :id")
    suspend fun deleteDetail(id: Int?)

}
