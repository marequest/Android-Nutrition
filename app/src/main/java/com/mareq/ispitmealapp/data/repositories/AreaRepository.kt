package com.mareq.ispitmealapp.data.repositories

import com.mareq.ispitmealapp.data.model.entities.Area
import io.reactivex.Observable

interface AreaRepository {

    fun getAreas() : Observable<List<Area>>

}