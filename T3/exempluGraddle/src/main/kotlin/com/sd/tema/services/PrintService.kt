package com.sd.tema.services

import com.sd.tema.interfaces.PrintInterface
import org.springframework.stereotype.Service
import java.lang.StringBuilder

@Service
class PrintService: PrintInterface {

    private var nextService: Any = Any()

    override fun setNextService(o: Any) {
        nextService = o
    }

    override fun getHTML(arguments: Any): String {
        val methods = nextService.javaClass.declaredMethods
        if(methods.isNotEmpty()){
            var methodToCall = methods[0]
            if(methods.size > 1) {
                for (method in methods) {
                    if(method.name.toUpperCase().startsWith("GET")){
                        methodToCall = method
                        break
                    }
                }
            }
            var result: Any = methodToCall.invoke(nextService, arguments)
            var html: StringBuilder = StringBuilder()
            html.append("<html>")
            html.append("<head><title>Forecast</title></head>")
            html.append("<body>")
            html.append("<h3>${result.javaClass.simpleName}</h3>")
            var fields = result.javaClass.declaredFields
            html.append("<ul>")
            for(field in fields){
                if (!field.isAccessible){
                    field.isAccessible = true
                }
                var value = field.get(result)
                var isImage : Boolean = value.toString().endsWith("png") || value.toString().endsWith("jpg")
                var representation = if(isImage) "<img src=\"${value}\" height=\"35\">" else value.toString()
                html.append("<li>${field.name}: ${representation}</li>")
            }
            html.append("</ul>")
            html.append("</body>")
            html.append("</html>")
            return html.toString()
        }
        return "Error"
    }
}