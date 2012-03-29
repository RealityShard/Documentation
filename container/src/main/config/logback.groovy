// ========================================================================
// LOGBack configuration file
// Taken from the Java Guild Wars LAN Project: Revived
// written by iDemmel
// ========================================================================
// =======================================================
// Useful API
// =======================================================
// root(Level level, List<String> appenderNames = [])
// logger(String name, Level level, List<String> appenderNames = [], Boolean additivity = null)
// appender(String name, Class clazz, Closure closure = null)


// =======================================================
// Required imports
// =======================================================
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.FileAppender
import ch.qos.logback.classic.html.HTMLLayout
import ch.qos.logback.core.encoder.LayoutWrappingEncoder
import static ch.qos.logback.classic.Level.*


// =======================================================
// General settings
// =======================================================
// The logging directory where all log files are stored (shouldn't end with /)
def logDir = "/var/log/jgwlpr"


// =======================================================
// Appender that will log to a file
// =======================================================
appender("rootLog", FileAppender) {
  file = "${logDir}/rootLog.log"
  encoder(PatternLayoutEncoder) {
    pattern = "%date{yyyy-MM-dd HH:mm:ss,SSS} [%thread] %-5level - \\(%logger\\) - %message%n"
  }
}


// =======================================================
// Example of another appender that can be used
// =======================================================
appender("txnLog", FileAppender) {
  file = "${logDir}/txnLog.log"
  encoder(PatternLayoutEncoder) {
    pattern = "%date{yyyy-MM-dd HH:mm:ss,SSS (Z)};%message%n"
  }
}


// =======================================================
// Appender that will log to an HTML file
// Logs are formatted in a table
// =======================================================
appender("rootLogHtml", FileAppender) {
  encoder(LayoutWrappingEncoder) {
    layout(HTMLLayout) {
      pattern = "%date{yyyy-MM-dd HH:mm:ss,SSS}%thread%-5level%logger%message"
      // A CSS builder can be defined here: cssBuilder(ch.qos.logback.classic.html.UrlCssBuilder) {url="http://..."}
    }
  }
  file = "${logDir}/rootLog.html"
}


// =======================================================
// Rootlogger: Every log goes there by default. 
// It uses the rootLog appender to log to a file
// =======================================================
root(INFO, ["rootLog", "rootLogHtml"])


// =======================================================
// Example of another logger that can be used to log transactions
// =======================================================
logger("be.demmel.fun.jgwlpr.logging.TransactionLogger", INFO, ["txnLog"], false)


// =======================================================
// Example of a logger that's declared just to limit the verbose of a package
// =======================================================
logger("com.mchange", ERROR, ["rootLog"], false) 
