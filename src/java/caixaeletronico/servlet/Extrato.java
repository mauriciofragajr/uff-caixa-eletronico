package caixaeletronico.servlet;

import caixaeletronico.dao.DAO;
import caixaeletronico.models.Movimentacao;
import caixaeletronico.models.Usuario;
import caixaeletronico.utils.StringUtil;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Extrato", urlPatterns = {"/Extrato"})
public class Extrato extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher resposta;
        HttpSession session = request.getSession();

        Date data = new java.util.Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String hoje = df.format(data);

        try {
            String cpfLogado = (String) session.getAttribute("cpfLogado");

            String dataInicio = (String) request.getParameter("dataInicio");
            String dataFinal = (String) request.getParameter("dataFinal");

            dataInicio = StringUtil.isEmpty(dataInicio) ? hoje : dataInicio;
            dataFinal = StringUtil.isEmpty(dataFinal) ? hoje : dataFinal;

            System.out.println("Data início: " + dataInicio);
            System.out.println("Data fim: " + dataFinal);

            DAO dao = new DAO();
            Usuario usuario = dao.getUsuario(cpfLogado);

            List<Movimentacao> extrato = dao.getExtrato(usuario, dataInicio, dataFinal);

            int total = 0;

            for (Movimentacao mov : extrato) {
                int valor = mov.getValor();

                if (null != mov.getTipo()) {
                    switch (mov.getTipo()) {
                        case "DEPOSITO":
                            total += valor;
                            break;
                        case "SAQUE":
                            total -= valor;
                            break;
                        case "TRANSFERENCIA":
                            if (cpfLogado.equals(mov.getContaOrigem())) {
                                total -= valor;
                            } else if (cpfLogado.equals(mov.getContaDestino())) {
                                total += valor;
                            }
                            break;
                        default:
                            break;
                    }
                }
            }

            request.setAttribute("extrato", extrato);
            request.setAttribute("total", total);

            request.setAttribute("dataInicio", dataInicio);
            request.setAttribute("dataFinal", dataFinal);

            resposta = request.getRequestDispatcher("WEB-INF/pages/menu/extrato.jsp");
            resposta.forward(request, response);
        } catch (SQLException e) {
            System.out.println("Erro ao carregar extrato" + e);
            response.sendRedirect("/CaixaEletronico");
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
