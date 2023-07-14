package com.mareq.ispitmealapp.data.repositories.implementation

import com.mareq.ispitmealapp.data.model.datasources.AreaDataSource
import com.mareq.ispitmealapp.data.model.entities.Area
import com.mareq.ispitmealapp.data.repositories.AreaRepository
import io.reactivex.Observable

class AreaRepositoryImpl(private val areaDataSource : AreaDataSource) : AreaRepository {
    override fun getAreas(): Observable<List<Area>> {
        return areaDataSource.getAreas().map {areasResponse ->
            areasResponse.meals.map {area ->
                Area(
                    areaName = area.strArea
                )
            }
        }
    }
}