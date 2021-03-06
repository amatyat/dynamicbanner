package magnon.hp.banner.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import magnon.hp.banner.db.JDBCConncetionProvider;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		JDBCConncetionProvider jConncetionProvider = new JDBCConncetionProvider();
		Connection con = jConncetionProvider.connect();
		// read form fields
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String lastname = request.getParameter("lastname");
		String manager = request.getParameter("manager");
		String email = request.getParameter("email");

		System.out.println("username: " + username);
		System.out.println("password: " + password);
		System.out.println("lastname: " + lastname);
		System.out.println("manager: " + manager);
		System.out.println("email: " + email);
		// get response writer
		PrintWriter writer = response.getWriter();
		String SQL_INSERT = "INSERT INTO banneruser (name, password) VALUES (?,?)";

		// build HTML code
		String htmlRespone = "<html>";
		try {
			//TBD change
			PreparedStatement ps = con.prepareStatement
					(SQL_INSERT);
			ps.setString(1, username);
			ps.setString(2, password);
			int i = ps.executeUpdate();

			if(i > 0) {
				htmlRespone += "You are sucessfully registered";
			}
			htmlRespone += "<h2>Your username is: " + username + "<br/>";	
			htmlRespone += "</html>";


		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("/BannerServlet/LoginForm.html");  
		  

		// return response
		writer.println(htmlRespone);

	}

}