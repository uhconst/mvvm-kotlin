package com.constancio.data.local.db.code

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a single Path.
 *
 * @property id Unique identifier of the Path in the DB.
 * @property nextPath The next path to fetch the response code.
 */
@Entity(tableName = "path")
data class PathEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,

    @ColumnInfo(name = "next_path")
    val nextPath: String
)