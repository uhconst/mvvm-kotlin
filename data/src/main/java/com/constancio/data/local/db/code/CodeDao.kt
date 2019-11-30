package com.constancio.data.local.db.code

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

/** Database access interface for [ResponseCodeEntity] and [PathEntity]. */
@Dao
interface CodeDao {

    /** Insert the [PathEntity] to the database. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNextPath(pathEntity: PathEntity)

    /** Insert the [ResponseCodeEntity] to the database. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertResponseCode(responseCodeEntity: ResponseCodeEntity)

    /** Return a single [PathEntity] ordering by id to fetch the last one]. */
    @Query("SELECT * FROM path ORDER BY id DESC LIMIT 1")
    fun getNextPath(): Single<PathEntity>
}