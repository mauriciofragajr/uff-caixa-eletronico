package caixaeletronico.dao;

import caixaeletronico.db.Conexao;
import caixaeletronico.models.Conta;
import caixaeletronico.models.Movimentacao;
import caixaeletronico.models.Usuario;
import caixaeletronico.utils.StringUtil;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DAO {

    private Connection conn;

    public DAO() {
        this.conn = Conexao.getConexao();
    }

    public Usuario getUsuario(String cpf) {
        String sql = "select cpf, nome from usuario where "
                + "cpf = ?";

        String sqlConta = "select saldo from conta where "
                + "cpf = ?";

        Usuario usuario = new Usuario();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            PreparedStatement stmtConta = conn.prepareStatement(sqlConta);

            stmt.setString(1, cpf);
            stmtConta.setString(1, cpf);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                usuario.setCPF(rs.getString("cpf"));
                usuario.setNome(rs.getString("nome"));
            }

            rs = stmtConta.executeQuery();

            Conta conta = new Conta();
            while (rs.next()) {
                conta.setSaldo(rs.getInt("saldo"));
            }

            usuario.setConta(conta);

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return usuario;
    }

    public void adiciona(Usuario usuario) throws SQLException {
        String sql = "insert into usuario "
                + "(cpf, senha, nome)"
                + "values (?,?,?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, usuario.getCPF());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getCPF());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Usuario login(String CPF, String senha) {
        String sql = "select cpf, nome from usuario where "
                + "cpf = ? and senha = ?";

        Usuario usuario = new Usuario();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, CPF);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                usuario.setCPF(rs.getString("cpf"));
                usuario.setNome(rs.getString("nome"));
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return usuario;
    }

    public void depositar(Usuario usuario, int valor) throws SQLException {

        Date data = new java.util.Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataStr = df.format(data);

        String sqlConta = "update conta set "
                + "saldo = saldo + ? "
                + "where cpf = ?";

        String sqlMovimentacao = "insert into movimentacao "
                + "(tipo, conta_origem, valor, criado_em) "
                + "values (?,?,?,?)";

        try {
            conn.setAutoCommit(false);

            PreparedStatement stmt1 = conn.prepareStatement(sqlConta);
            PreparedStatement stmt2 = conn.prepareStatement(sqlMovimentacao);

            stmt1.setInt(1, valor);
            stmt1.setString(2, usuario.getCPF());

            stmt1.executeUpdate();

            stmt2.setString(1, "DEPOSITO");
            stmt2.setString(2, usuario.getCPF());
            stmt2.setInt(3, valor);
            stmt2.setString(4, dataStr);

            stmt2.executeUpdate();

            conn.commit();

            stmt1.close();
            stmt2.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            conn.setAutoCommit(true);
        }
    }

    public List<Movimentacao> getExtrato(Usuario usuario, String dataInicio, String dataFinal) throws SQLException {
        String sql = "select * from movimentacao where "
                + "(conta_origem = ? or conta_destino = ?) and criado_em between ? and ?";

        List<Movimentacao> list = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, usuario.getCPF());
            stmt.setString(2, usuario.getCPF());
            stmt.setString(3, dataInicio + " 00:00:00");
            stmt.setString(4, dataFinal + " 23:59:59");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Movimentacao mov = new Movimentacao();
                mov.setTipo(rs.getString("tipo"));
                mov.setValor(rs.getInt("valor"));
                mov.setContaOrigem(rs.getString("conta_origem"));
                mov.setContaDestino(rs.getString("conta_destino"));
                mov.setCriadoEm(rs.getDate("criado_em"));
                list.add(mov);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public void sacar(Usuario usuario, int valor) throws SQLException, Exception {

        Date data = new java.util.Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataStr = df.format(data);

        String sqlConta = "update conta set "
                + "saldo = saldo - ? "
                + "where cpf = ?";

        String sqlMovimentacao = "insert into movimentacao "
                + "(tipo, conta_origem, valor, criado_em) "
                + "values (?,?,?,?)";

        try {
            if (usuario.getConta().getSaldo() < valor) {
                throw new Exception("Saldo insuficiente");
            }

            conn.setAutoCommit(false);

            PreparedStatement stmt1 = conn.prepareStatement(sqlConta);
            PreparedStatement stmt2 = conn.prepareStatement(sqlMovimentacao);

            stmt1.setInt(1, valor);
            stmt1.setString(2, usuario.getCPF());

            stmt1.executeUpdate();

            stmt2.setString(1, "SAQUE");
            stmt2.setString(2, usuario.getCPF());
            stmt2.setInt(3, valor);
            stmt2.setString(4, dataStr);

            stmt2.executeUpdate();

            conn.commit();

            stmt1.close();
            stmt2.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public void transferir(Usuario usuario, int valor, String cpfDestino) throws SQLException, Exception {

        Date data = new java.util.Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataStr = df.format(data);

        String sqlContaOrigem = "update conta set "
                + "saldo = saldo - ? "
                + "where cpf = ?";

        String sqlContaDestino = "update conta set "
                + "saldo = saldo + ? "
                + "where cpf = ?";

        String sqlMovimentacao = "insert into movimentacao "
                + "(tipo, conta_origem, conta_destino, valor, criado_em) "
                + "values (?,?,?,?,?)";

        try {
            if (usuario.getConta().getSaldo() < valor) {
                throw new Exception("Saldo insuficiente");
            } else if (usuario.getCPF().equals(cpfDestino)) {
                throw new Exception("Não pode transferir para a própria conta");
            }

            Usuario usuarioDestino = getUsuario(cpfDestino);

            if (StringUtil.isEmpty(usuarioDestino.getCPF())) {
                throw new Exception("Conta destino não encontrada");
            }

            conn.setAutoCommit(false);

            PreparedStatement stmt1 = conn.prepareStatement(sqlContaOrigem);
            PreparedStatement stmt2 = conn.prepareStatement(sqlContaDestino);
            PreparedStatement stmt3 = conn.prepareStatement(sqlMovimentacao);

            stmt1.setInt(1, valor);
            stmt1.setString(2, usuario.getCPF());

            stmt1.executeUpdate();

            stmt2.setInt(1, valor);
            stmt2.setString(2, cpfDestino);

            stmt2.executeUpdate();

            stmt3.setString(1, "TRANSFERENCIA");
            stmt3.setString(2, usuario.getCPF());
            stmt3.setString(3, cpfDestino);
            stmt3.setInt(4, valor);
            stmt3.setString(5, dataStr);

            stmt3.executeUpdate();

            conn.commit();

            stmt1.close();
            stmt2.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            conn.setAutoCommit(true);
        }
    }
}
