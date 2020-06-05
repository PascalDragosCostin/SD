//package com.sd.tema.services
//
//import com.sd.tema.interfaces.PrintInterface
//import org.springframework.stereotype.Service
//import java.lang.StringBuilder
//
//@Service
//class PrintService: PrintInterface {
//
//    private var nextService: Any = Any()
//
//    override fun setNextService(o: Any) {
//        nextService = o
//    }
//
//    override fun getHTML(par: Any): String {
//        val methods = nextService.javaClass.declaredMethods
//        if(methods.isNotEmpty()){
//            val methodToCall = methods[0]
//            val result: Any = methodToCall.invoke(nextService, par)
//            val html: StringBuilder = StringBuilder()
//            html.append("<html>")
//            html.append("<head><title>Forecast</title></head>")
//            html.append("<body>")
//            html.append("<h3>${result.javaClass.simpleName}</h3>")
//            val fields = result.javaClass.declaredFields
//            html.append("<ul>")
//            for(field in fields){
//                if (!field.isAccessible){
//                    field.isAccessible = true
//                }
//                val value = field.get(result)
//                val isImage : Boolean = value.toString().endsWith("png") || value.toString().endsWith("jpg")
//                val representation = if(isImage) "<img src=\"${value}\" height=\"35\">" else value.toString()
//                html.append("<li>${field.name}: ${representation}</li>")
//            }
//            html.append("</ul>")
//            html.append("</body>")
//            html.append("</html>")
//            return html.toString()
//        }
//        return "Error"
//    }
//}