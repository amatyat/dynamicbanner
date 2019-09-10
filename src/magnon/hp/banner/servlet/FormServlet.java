package magnon.hp.banner.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class FormServlet
 */
@WebServlet("/FormServlet")
public class FormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FormServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		System.out.println("username is: " + username);
		System.out.println("password is: " + password);

		String languages[] = request.getParameterValues("language");
		String langHtml = "";
		
		if (languages != null) {
			System.out.println("Languages are: ");
			for (String lang : languages) {
				langHtml += lang + ",";
				System.out.println("\t" + lang);
			}
		}
		
		String gender = request.getParameter("gender");
		System.out.println("Gender is: " + gender);

		
		String feedback = request.getParameter("feedback");
		System.out.println("Feed back is: " + feedback);

		String jobCategory = request.getParameter("jobCat");
		System.out.println("Job category is: " + jobCategory);
		
		PrintWriter writer = response.getWriter();
		
		String htmlRespone = "<html><h3>";
		htmlRespone += "username is: " + username + "<br/>";		
		htmlRespone += "password is: " + password + "<br/>";		
		htmlRespone += "language is: " + langHtml + "<br/>";		
		htmlRespone += "gender is: " + gender + "<br/>";		
		htmlRespone += "feedback is: " + feedback + "<br/>";		
		htmlRespone += "job category is : " + jobCategory + "<br/>";		
		htmlRespone += "</h3></html>";
		
		htmlRespone+=BannerCreator.createHTML();
		
		// return response
		writer.println(htmlRespone);		
	}

}