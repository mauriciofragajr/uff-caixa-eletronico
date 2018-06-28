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

@WebServlet(name = "Operacao", urlPatterns = {"/Operacao"})
public class Operacao extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        RequestDispatcher resposta;

        try {
            String cpfLogado = (String) session.getAttribute("cpfLogado");

            DAO dao = new DAO();

            Usuario usuarioLogado = dao.getUsuario(cpfLogado);

            String valor = request.getParameter("valor");
            String tipo = request.getParameter("tipo");
            String cpfDestino = request.getParameter("cpfDestino");

            double valorDecimal = Double.parseDouble(valor);
            valorDecimal *= 100;

            int valorCentavos = (int) valorDecimal;

            switch (tipo) {
                case "DEPOSITO":
                    dao.depositar(usuarioLogado, valorCentavos);
                    request.setAttribute("tipo", "Depósito");
                    resposta = request.getRequestDispatcher("WEB-INF/pages/menu/confirmacao.jsp");
                    resposta.forward(request, response);
                    break;
                case "SAQUE":
                    dao.sacar(usuarioLogado, valorCentavos);
                    request.setAttribute("tipo", "Saque");
                    resposta = request.getRequestDispatcher("WEB-INF/pages/menu/confirmacao.jsp");
                    resposta.forward(request, response);
                    break;
                case "TRANSFERENCIA":
                    dao.transferir(usuarioLogado, valorCentavos, cpfDestino);
                    request.setAttribute("tipo", "Transferência");
                    resposta = request.getRequestDispatcher("WEB-INF/pages/menu/confirmacao.jsp");
                    resposta.forward(request, response);
                    break;
                default:
                    System.out.println("Nenhuma operação selecionada");
            }
        } catch (Exception e) {
            System.out.println("Erro ao realizar operação " + e);
            request.setAttribute("tipo", e.getMessage());
            resposta = request.getRequestDispatcher("WEB-INF/pages/menu/falha.jsp");
            resposta.forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
