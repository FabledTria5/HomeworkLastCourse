package com.fabledt5.reddit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fabledt5.reddit.data.local.entities.RemoteKeyEntity

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addKeys(remoteKeyEntity: RemoteKeyEntity)

    @Query(value = "SELECT * FROM remote_keys_table ORDER BY id DESC")
    suspend fun getRemoteKeys(): List<RemoteKeyEntity>

}