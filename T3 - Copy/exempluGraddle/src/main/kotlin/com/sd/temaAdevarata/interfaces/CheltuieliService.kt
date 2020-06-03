package com.sd.temaAdevarata.interfaces

import com.sd.temaAdevarata.pojo.Membru

interface CheltuieliService
{
    fun getPerson(id: Int) : Membru?
    fun createPerson(membru: Membru)
    fun deletePerson(id: Int)
    fun updatePerson(id: Int, membru: Membru)
    fun updateAmount(id:Int, amount:Array<Int>)
    fun searchAgenda( firstNameFilter: String): List<Membru>
}