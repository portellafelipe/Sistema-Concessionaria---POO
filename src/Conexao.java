import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL =
            "jdbc:mysql://mysql-bf47672-concessionaria-db.b.aivencloud.com:21301/defaultdb?useSSL=true&requireSSL=true&serverTimezone=UTC";
    private static final String USUARIO = "avnadmin";
    private static final String SENHA = "AVNS_-DuG625w5KcThKBprA4";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
