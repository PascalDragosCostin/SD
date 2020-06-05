package com.sd.tema.services

import com.sd.tema.interfaces.PrintInterface
import org.springframework.stereotype.Service
import java.lang.StringBuilder

@Service
class PrintService2: PrintInterface {

    private var nextService: Any = Any()

    override fun setNextService(o: Any) {
        nextService = o
    }

    override fun getHTML(par: Any): String {
        var html: StringBuilder = StringBuilder()
        html.append("<html>")
        html.append("<head><title>Forecast</title></head>")
        html.append("<body>")
        html.append("<h3>${par.javaClass.simpleName}</h3>")
        var fields = par.javaClass.declaredFields
        html.append("<ul>")
        for (field in fields) {
            if (!field.isAccessible) {
                field.isAccessible = true
//            }
                var value = field.get(par)
                var isImage: Boolean = value.toString().endsWith("png") || value.toString().endsWith("jpg")
                var representation = if (isImage) "<img src=\"${value}\" height=\"35\">" else value.toString()
                html.append("<li>${field.name}: ${representation}</li>")
            }
        }
        html.append("</ul>")
        html.append("</body>")
        html.append("</html>")
        return html.toString()
    }
}