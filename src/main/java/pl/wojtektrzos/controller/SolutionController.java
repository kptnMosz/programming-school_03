package pl.wojtektrzos.controller;

import pl.wojtektrzos.model.Solution;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "SolutionController", urlPatterns = "/")
public class SolutionController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            String init = getServletContext().getInitParameter("number-solutions");
            int limit = Integer.parseInt(init);
            PrintWriter pisak = response.getWriter();

            List<Solution> solutionList = Solution.loadAll(limit);
            request.setAttribute("solutions", solutionList);
            request.getRequestDispatcher("/index.jsp").forward(request,response);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
