package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.MemberDAO;
import DTO.Vote;

@WebServlet("/")
public class VoteController extends HttpServlet {
   private static final long serialVersionUID = 1L;

   
    public VoteController() {
       super();
    }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) 
         throws ServletException, IOException {
      doPro(request,response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) 
         throws ServletException, IOException {
      request.setCharacterEncoding("UTF-8");
      doPro(request, response);
   }
   protected void doPro(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
      request.setCharacterEncoding("UTF-8");
      String context = request.getContextPath(); //톰캣의 contextpath를 가져온다.
      
      String command = request.getServletPath(); //경로 맨 끝 파일명을 가져온다.
      String site = null;
      
      System.out.println(context + ", " + command);
      
      MemberDAO member = new MemberDAO();
      
      switch(command) {
      case "/list":
         site = member.selectAll(request, response);
         break;
      case "/add":
    	  site = "add.jsp";
    	  break;
      case "/insert":
    	  int result = member.insert(request, response);
    	  response.setContentType("text/html; charset=UTF-8");
    	  PrintWriter out = response.getWriter();
			if (result == 1) {
				out.println("<script>");
				out.println(" alert('후보자투표가 완료 되었습니다!'); location.href='" + context + "';  ");
				out.println("</script>");
				out.flush();
			} else {
				out.println("<script>");
				out.println("alert('정보를 지우고 처음부터 다시 입력합니다!'); location.reload();='" + context + "'; ");
				out.println("</script>");
				out.flush();
			}
        break;
      case "/votelist":
    	  site = member.selectVote(request, response);
    	  break;
      }
      
      
      
      
      getServletContext().getRequestDispatcher("/" + site).forward(request, response);
   }
}