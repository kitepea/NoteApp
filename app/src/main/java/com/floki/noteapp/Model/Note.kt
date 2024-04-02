package com.floki.noteapp.Model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.floki.noteapp.R
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Parcelize
@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "note") val note: String?,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "color") val color: Int = randomColor()
) : Parcelable {
    companion object {
        fun randomColor(): Int {
            val list = ArrayList<Int>()

            list.add(R.color.noteColor1)
            list.add(R.color.noteColor2)
            list.add(R.color.noteColor3)
            list.add(R.color.noteColor4)
            list.add(R.color.noteColor5)
            list.add(R.color.noteColor6)

            val seed = System.currentTimeMillis().toInt()
            val random = Random(seed).nextInt(list.size)
            return list[random]
        }
    }
}