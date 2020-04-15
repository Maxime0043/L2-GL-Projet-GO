package log;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Classe utilitaire utilisée pour générer un Log4j logger.
 * 
 * On peut générer des logs en fichier texte ou html.
 * 
 * @author Maxime, Micael et Houssam
 */
public class LoggerUtility {
	private static final String TEXT_LOG_CONFIG = "src/log/log4j-text.properties";
	private static final String HTML_LOG_CONFIG = "src/log/log4j-html.properties";

	public static Logger getLogger(Class<?> logClass, String logFileType) {
		if (logFileType.equals("text")) {
			PropertyConfigurator.configure(TEXT_LOG_CONFIG);
		} else if (logFileType.equals("html")) {
			PropertyConfigurator.configure(HTML_LOG_CONFIG);
		} else {
			throw new IllegalArgumentException("Fichier log de type inconnu !");
		}

		String className = logClass.getName();
		return Logger.getLogger(className);
	}
}
