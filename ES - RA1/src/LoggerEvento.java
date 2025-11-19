import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

// calsse para registrar eventos dos proxies em arquivo de log.
public class LoggerEvento {
    private static final String ARQUIVO_LOG = "eventos_heroi.txt";
    private static final DateTimeFormatter formatter = 
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", new Locale("pt", "BR"));

        public static void registrar(String evento) {
            String timestamp = LocalDateTime.now().format(formatter);
            String linha = timestamp + " " + evento;
            try (FileWriter fw = new FileWriter(ARQUIVO_LOG, true)) {
                fw.write(linha + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

}
