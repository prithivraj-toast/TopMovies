package com.zestworks.data.db

import androidx.room.TypeConverter

class ListOfIntTypeConverter {
    @TypeConverter
    fun gettingListFromString(genreIds: String): List<Int> {
        val list: MutableList<Int> = ArrayList()
        val array = genreIds.split(",").toTypedArray()
        for (s in array) {
            if (s.isNotEmpty()) {
                list.add(s.toInt())
            }
        }
        return list
    }

    @TypeConverter
    fun writingStringFromList(list: List<Int>): String {
        var genreIds = ""
        for (i in list) {
            genreIds += ",$i"
        }
        return genreIds
    }
}