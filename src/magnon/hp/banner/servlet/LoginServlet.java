package magnon.hp.banner.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import magnon.hp.banner.db.JDBCConncetionProvider;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

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

		System.out.println("username: " + username);
		System.out.println("password: " + password);
		// get response writer
		PrintWriter writer = response.getWriter();

		// build HTML code
		String htmlRespone = "<html>";
		try {

			Statement statement = con.createStatement();
			String queryString = "select * from user where name='"+username+"'";
			ResultSet rs = statement.executeQuery(queryString);
			while (rs.next()) {
				System.out.print("\tName: "+rs.getString(2));
				System.out.print("\tPassword: "+rs.getString(3));
			
				htmlRespone += "<h2>Your username is: " + username + "<br/>";	
				if(password.equals(rs.getString(3))) {
					htmlRespone += "Your Login is Successful</h2>";
					response.sendRedirect("/BannerServlet/BannerForm.html");  
				}else {
					htmlRespone += "Your Password is incorrect. Please try again.</h2>";
				}
				htmlRespone += "</html>";
			}


		} catch (Exception e) {
			e.printStackTrace();
		}


		// return response
		writer.println(htmlRespone);

	}

}