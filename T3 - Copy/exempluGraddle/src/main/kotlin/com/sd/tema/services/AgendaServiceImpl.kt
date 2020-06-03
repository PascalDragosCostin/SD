//package com.sd.tema.services
//
//import com.sd.temaAdevarata.interfaces.CheltuieliService
//import com.sd.temaAdevarata.pojo.Membru
//import org.springframework.stereotype.Service
//import java.util.concurrent.ConcurrentHashMap
//
//@Service
//class AgendaServiceImpl : CheltuieliService {
//    companion object {
//        val initialAgenda = arrayOf(
//            Membru(1, "Hello", "Kotlin", "1234"),
//            Membru(2, "Hello", "Spring", "5678"),
//            Membru(3, "Hello", "Microservice", "9101112")
//        )
//    }
//
//    private val agenda = ConcurrentHashMap<Int, Membru>(initialAgenda.associateBy { membru: Membru -> membru.id })
//
//    override fun getPerson(id: Int): Membru? {
//        return agenda[id]
//    }
//
//    override fun createPerson(membru: Membru) {
//        agenda[membru.id] = membru
//    }
//
//    override fun deletePerson(id: Int) {
//        agenda.remove(id)
//    }
//
//    override fun updatePerson(id: Int, membru: Membru) {
//        deletePerson(id)
//        createPerson(membru)
//    }
//
//    override fun searchAgenda(lastNameFilter: String, firstNameFilter: String, telephoneNumberFilter: String): List<Membru> {
//        return agenda.filter {
//            it.value.lastName.toLowerCase().contains(
//                lastNameFilter,
//                ignoreCase = true
//            ) && it.value.firstName.toLowerCase().contains(
//                firstNameFilter,
//                ignoreCase = true
//            ) && it.value.telephoneNumber.contains(telephoneNumberFilter)
//        }.map { it.value }.toList()
//    }
//}