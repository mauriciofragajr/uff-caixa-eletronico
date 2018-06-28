package caixaeletronico.servlet;

import caixaeletronico.dao.DAO;
import caixaeletronico.models.Usuario;
import caixaeletronico.utils.StringUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ValidarLogin", urlPatterns = {"/ValidarLogin"})
public class ValidarLogin extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuarioLogado;
        
        session.removeAttribute("msgErro");

        String cpf = request.getParameter("cpf");
        String senha = request.getParameter("senha");

        System.out.println("Chamando Validar Login: " + cpf + " " + senha);

        if (cpf != null && senha != null) {
            System.out.println("Tentando logar...");
            DAO dao = new DAO();

            usuarioLogado = dao.login(cpf, senha);

            if (!StringUtil.isEmpty(usuarioLogado.getCPF())) {
                System.out.println("Logado com sucesso: " + usuarioLogado);
                session.setAttribute("usuarioLogado", usuarioLogado.getNome());
                session.setAttribute("cpfLogado", usuarioLogado.getCPF());
//                request.setAttribute("usuarioLogado", usuarioLogado);
                response.sendRedirect("/CaixaEletronico/Home");
            } else {
                session.setAttribute("msgErro", "CPF ou senha inválida!");
                response.sendRedirect("/CaixaEletronico/Login");
            }
        } else {
            response.sendRedirect("/CaixaEletronico/Login");
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
