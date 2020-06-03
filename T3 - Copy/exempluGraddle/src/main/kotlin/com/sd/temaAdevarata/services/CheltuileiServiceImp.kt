package com.sd.temaAdevarata.services

import com.sd.temaAdevarata.interfaces.CheltuieliService
import com.sd.temaAdevarata.pojo.Membru
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class CheltuileiServiceImp : CheltuieliService {
    companion object {
        val initialAgenda = arrayOf(
            Membru(1, "Dragos",  arrayOf(1500,1300,1200)),
            Membru(2, "Danut", arrayOf(1500,1900,2200)),
            Membru(3, "Luminita",  arrayOf(400,2300,1250))
        )
    }

    private val agenda = ConcurrentHashMap<Int, Membru>(initialAgenda.associateBy { membru: Membru -> membru.id })

    override fun getPerson(id: Int): Membru? {
        return agenda[id]
    }

    override fun createPerson(membru: Membru) {
        agenda[membru.id] = membru
    }

    override fun deletePerson(id: Int) {
        agenda.remove(id)
    }

    override fun updatePerson(id: Int, membru: Membru) {
        deletePerson(id)
        createPerson(membru)
    }

    override fun updateAmount(id: Int, amount: Array<Int>) {
        getPerson(id)?.amount = amount
    }

    override fun searchAgenda( firstNameFilter: String): List<Membru> {
        return agenda.filter { it.value.firstName.toLowerCase().contains(
                firstNameFilter,
                ignoreCase = true
            )
        }.map { it.value }.toList()
    }
}