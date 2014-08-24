#Laboratorio sobre el manejo de Logging en JEE

##Objetivo
El presente documento tiene como objetivo ayudar al estudiante a comprender la necesidad del uso de logs en las aplicaciones empresariales. Por otra parte, se explicará como utilizar la herramienta Log4j para la creación de logs.

##Requisitos previos
Para este laboratorio son necesarios los siguientes items.
- NetBeans IDE o similar
- Archivo .jar de Log4j

##Introducción
Muchas veces cuando programamos no tenemos en cuenta muchos factores, pero algo que se suele obviar es el uso de logs. Por qué son tan importantes? Los logs representan la forma convencional de “debuggear” en entornos de producción. La idea es colocar trazas en lugares específicos del código que nos puedan ayudar a detectar dónde está ocurriendo un problema y poder solucionarlo, o bien saber qué está haciendo nuestra aplicación o los clientes que la consumen, etc. En fin, hay muchas razones por las cuales debemos usar logs.

##Log4j
Es un framework open source desarrollado sobre Java para la creación de logs de forma muy sencilla, flexible y fácil de configurar. Trabaja en base a propiedades configurables que extrae de un archivo de configuración que permiten cambiar los niveles de logs, o el formato en que se muestran, etc.

Sitio de descarga: https://logging.apache.org/log4j/1.2/download.html

Log4j maneja 3 conceptos fundamentales para la creación de logs: Appenders, Levels y Layouts. Veremos en detalle que son cada uno y cómo se configuran.


##Ejemplo de uso
Para ver el uso de log4j desarrollaremos un ejemplo paso a paso utilizando las distintas funcionalidades de esta herramienta. La idea del ejemplo consiste en crear un programa que genere diferentes mensajes de log y mediante log4j guardar esos logs en diferentes ubicaciones, diferentes formatos y diferentes filtros.

####1) Creación del proyecto
Para simplificar, este ejemplo será desarrollado como una Java Application para que se ejecute por consola. Cabe destacar que los ejemplos que veremos son análogos para los EJB y otros componentes en donde se quieran utilizar.

Abrir el NetBeans IDE, ir a “New Project”, se desplegará una nueva ventana donde seleccionamos “Java Application” dentro de la carpeta “Java”, luego presionamos el botón “Next”. En la siguiente pantalla nos pide un nombre para el proyecto, en este caso se llamará “Log4jTest”, luego presionamos el botón “Finish”.

####2) Agregar Log4j al proyecto
Para poder utilizar el framework es necesario incluir la librería de Log4j en el build path de nuestro proyecto, para esto seguimos el siguiente procedimiento:

Dentro del panel “Projects”, abrimos el proyecto creado anteriormente y sobre la carpeta “Libraries” le damos botón derecho y luego la opción “Add JAR/Folder”



Se desplegará una ventana para buscar el archivo .jar de log4j. Buscamos y seleccionamos el archivo. Al aceptar, lo veremos listado dentro de las librerías del proyecto.

####3) Configuración del Logger
Como mencionamos anteriormente, log4j trabaja en base a un archivo de propiedades donde nos permite determinar qué tipo de logueo queremos tener. Dicho archivo lo creamos en la raíz del directorio del proyecto para poder cargarlo automáticamente.

En el ejemplo, el archivo log4j.properties contendrá lo siguiente:
```properties
# EJEMPLO 1 
# Define una variable para la ruta del log
log = C://log4j
# Define el root logger con un Appender llamado FILE 
log4j.rootLogger = DEBUG, FILE, HTML
# Define el Appender FILE 
log4j.appender.FILE=org.apache.log4j.FileAppender 
#Define la ruta al archivo de log que va a generar
log4j.appender.FILE.File=${log}/log.out 
# Define el layout para el appender 
log4j.appender.FILE.layout=org.apache.log4j.PatternLayout 
# Define el layout para las lineas de log
log4j.appender.FILE.layout.conversionPattern=%d{yyyy-MM-dd}-%p : %m%n
# Solo aceptará mensajes de mayor nivel que DEBUG
log4j.appender.FILE.threshold=DEBUG

# EJEMPLO 2
# Define un nuevo appender 
log4j.appender.HTML=org.apache.log4j.FileAppender 
log4j.appender.HTML.File=${log}/log.html 
# Define un layout de tipo HTML
log4j.appender.HTML.layout=org.apache.log4j.HTMLLayout 
log4j.appender.HTML.layout.Title=Logs en html
log4j.appender.HTML.layout.LocationInfo=true
# Solo aceptará mensajes de mayor nivel que WARN
log4j.appender.HTML.threshold=WARN


```
En este caso estamos definiendo dos nuevos Appenders (son objetos que maneja Log4j que se encargan de loguear nuestras trazas en diferentes destinos que se deseen, en diferentes formatos, etc) para guardar los logs generados por la aplicación. 

El appender llamado “FILE” guardará los logs en un archivo de texto plano y cada línea tendrá un formato de tipo “Fecha (en formato yyyy-MM-dd) - Nivel de log : Mensaje de log”. Aceptará todo mensaje cuyo nivel de log sea mayor a “DEBUG”

El appender llamado “HTML” guardará los logs en un archivo con formato HTML y  aceptará todo mensaje cuyo nivel de log sea mayor a “WARN”.

La jerarquía de niveles de log es la siguiente: 

ALL < DEBUG < INFO < WARN < ERROR < FATAL < OFF

Por lo que el appender ‘FILE’ mostrará todos los niveles y el appender “HTML” considerará solo los de tipo “ERROR”, “FATAL” y “WARN”. Los casos particulares de “ALL” y “OFF” no son niveles de log pero se utilizan para mostrar todos los posibles (ya que ALL es el de menor nivel en la jerarquía) o no mostrar ninguno (ya que todos son menores que OFF).

####4) Código de la aplicación
En la clase “Main” del proyecto, incluiremos el siguiente código cuya intención es generar un mensaje de log para cada nivel posible, luego comprobaremos que se estén filtrando correctamente según los appenders definidos.
```java
public class Log4jTest {

    static Logger log = Logger.getLogger(Log4jTest.class.getName());
    
    public static void main(String[] args) {
        log.debug("ESTO ES DE NIVEL DEBUG"); 
        log.info("ESTO ES DE NIVEL INFO");
        log.error("ESTO ES DE NIVEL ERROR!");
        log.fatal("ESTO ES DE NIVEL FATAL");
        log.warn("ESTO ES DE NIVEL WARN");
    }
}

```
La variable estática “log” se instancia con el rootLogger definido en la configuración, luego se utiliza para generar un mensaje de log para cada nivel. Si vamos a ver la carpeta destino de nuestros logs (configurada en el log4j.properties) veremos dos archivos, uno de texto plano y otro de tipo HTML. En el primero se verá una salida de por cada uno, y en el segundo se verán solo los filtrados mayores a WARN.

##Material de referencia

http://www.tutorialspoint.com/log4j/log4j_tutorial.pdf
