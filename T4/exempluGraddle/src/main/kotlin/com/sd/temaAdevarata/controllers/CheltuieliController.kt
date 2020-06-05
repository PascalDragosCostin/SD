package com.sd.temaAdevarata.controllers

import com.sd.temaAdevarata.interfaces.CheltuieliService
import com.sd.temaAdevarata.pojo.Membru
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class CheltuieliController {

    @Autowired
    private lateinit var cheltuiliService: CheltuieliService

    @RequestMapping(value=["/membru"], method=[RequestMethod.POST])
    fun createPerson(@RequestBody membru: Membru): ResponseEntity<Unit> {
        cheltuiliService.createPerson(membru)
        return ResponseEntity(Unit, HttpStatus.CREATED)
    }

    @RequestMapping(value=["/membru/{id}"], method=[RequestMethod.GET])
    fun getPerson(@PathVariable id: Int): ResponseEntity<Membru?> {
        val membru: Membru? = cheltuiliService.getPerson(id)
        val status = if (membru == null) {
            HttpStatus.NOT_FOUND
        } else {
            HttpStatus.OK
        }
        return ResponseEntity(membru, status)
    }


    @RequestMapping(value=["/membru/{id}"], method=[RequestMethod.PUT])
    fun updatePerson(@PathVariable id: Int, @RequestBody membru: Membru): ResponseEntity<Unit> {
        cheltuiliService.getPerson(id)?.let {
            cheltuiliService.updatePerson(it.id, membru)
            return ResponseEntity(Unit, HttpStatus.ACCEPTED)
        } ?: return ResponseEntity(Unit, HttpStatus.NOT_FOUND)
    }

    @RequestMapping(value=["/amount/{id}"], method=[RequestMethod.PATCH])
    fun updateAmount(@PathVariable id: Int, @RequestBody amount: Array<Int>): ResponseEntity<Unit> {
        cheltuiliService.getPerson(id)?.let {
            cheltuiliService.updateAmount(it.id, amount)
            return ResponseEntity(Unit, HttpStatus.ACCEPTED)
        } ?: return ResponseEntity(Unit, HttpStatus.NOT_FOUND)
    }

    @RequestMapping(value=["/membru/{id}"], method=[RequestMethod.DELETE])
    fun deletePerson(@PathVariable id: Int): ResponseEntity<Unit> {
        if (cheltuiliService.getPerson(id) != null) {
            cheltuiliService.deletePerson(id)
            return ResponseEntity(Unit, HttpStatus.OK)
        } else {
            return ResponseEntity(Unit, HttpStatus.NOT_FOUND)
        }
    }

    @RequestMapping(value=["/familie"], method=[RequestMethod.GET])
    fun search(@RequestParam(required = false, name = "firstName", defaultValue = "") firstName: String):ResponseEntity<List<Membru>> {
        val personList = cheltuiliService.searchAgenda(firstName)
        var httpStatus = HttpStatus.OK
        if (personList.isEmpty()) {
            httpStatus = HttpStatus.NO_CONTENT
        }
        return ResponseEntity(personList, httpStatus)
    }
}