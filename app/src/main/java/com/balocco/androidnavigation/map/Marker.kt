package com.balocco.androidnavigation.map

import com.balocco.androidnavigation.data.model.Location

interface Marker {

    fun showInfoBubble()

    fun location(): Location

    fun title(): String

    fun tag(): Any?

}