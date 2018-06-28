package caixaeletronico.servlet;

import caixaeletronico.dao.DAO;
import caixaeletronico.models.Usuario;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Saldo", urlPatterns = {"/Saldo"})
public class Saldo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher resposta;
        HttpSession session = request.getSession();

        try {
            String cpfLogado = (String) session.getAttribute("cpfLogado");

            DAO dao = new DAO();
            Usuario usuario = dao.getUsuario(cpfLogado);

            int centavos = usuario.getConta().getSaldo();

            request.setAttribute("centavos", centavos);

            resposta = request.getRequestDispatcher("WEB-INF/pages/menu/saldo.jsp");
            resposta.forward(request, response);
        } catch (Exception e) {
            System.out.println("Erro ao carregar saldo" + e);
            response.sendRedirect("/CaixaEletronico");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}