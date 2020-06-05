package com.sd.tema.controllers

import com.sd.tema.interfaces.PrintInterface
import com.sd.tema.interfaces.LocationSearchInterface
import com.sd.tema.interfaces.WeatherForecastInterface
import com.sd.tema.pojo.WeatherForecastData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class WeatherAppController2 {
    @Autowired
    private lateinit var locationSearchService: LocationSearchInterface

    @Autowired
    private lateinit var weatherForecastService: WeatherForecastInterface

    @Autowired
    private lateinit var printService: PrintInterface

    @RequestMapping("/getforecast/{location}/{html}", method = [RequestMethod.GET])
//    @ResponseBody
    fun getForecast(@PathVariable location: String, @PathVariable html:Boolean): ResponseEntity<String> {
        // se incearca preluarea WOEID-ului locaţiei primite in URL
        val locationId = locationSearchService.getLocationId(location)
        // dacă locaţia nu a fost găsită, răspunsul va fi corespunzător
        if (locationId == -1) {
            return ResponseEntity.ok("Nu s-au putut gasi date meteo pentru cuvintele cheie\"$location\"!")
        }

        val rawForecastData: WeatherForecastData = weatherForecastService.getForecastData(locationId)

        return if(!html){
            ResponseEntity.ok(rawForecastData.toString())
        }else{
            val headers = HttpHeaders()
            headers.contentType = MediaType.TEXT_HTML;
            ResponseEntity.ok().headers(headers).body(printService.getHTML(rawForecastData));
        }
    }
}

//http://localhost:8080/getforecast/Rome/false