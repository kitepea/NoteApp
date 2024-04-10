package com.floki.noteapp.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id") val userId: Int?,
    @ColumnInfo(name = "username") private val username: String,
    @ColumnInfo(name = "password") val password: String,
)