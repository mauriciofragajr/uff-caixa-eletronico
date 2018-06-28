package caixaeletronico.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static Connection getConexao() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/sistema_bancario", "mauricio", "123456");

        } catch (SQLException ex) {
            System.out.println("Erro ao conectar" + ex);
        }
        return conn;
    }

}
