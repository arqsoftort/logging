/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package log4jtest;

import static log4jtest.Log4jTest.log;
import org.apache.log4j.Logger;

/**
 *
 * @author Usuario
 */
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
