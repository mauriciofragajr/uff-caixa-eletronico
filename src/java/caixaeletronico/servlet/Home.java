package caixaeletronico.servlet;

//import caixaeletronico.dao.UsuarioDAO;
import caixaeletronico.utils.StringUtil;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Home", urlPatterns = {"/"})
public class Home extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String cpfLogado = (String) session.getAttribute("cpfLogado");

        if (StringUtil.isEmpty(cpfLogado)) {
            response.sendRedirect(request.getContextPath() + "/Login");
        } else {
            RequestDispatcher resposta = request.getRequestDispatcher("WEB-INF/pages/home.jsp");
            resposta.forward(request, response);
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
