package com.constancio.data.local.db.code

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a single Response Code.
 *
 * @property id Unique identifier of the Response Code in the DB.
 * @property nextPath The code returned from API.
 */
@Entity(tableName = "response_code")
data class ResponseCodeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,

    @ColumnInfo(name = "code")
    val code: String
)