package DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DTO.Member;
import DTO.Vote;

public class MemberDAO {
   Connection conn = null;
   PreparedStatement ps = null;
   ResultSet rs = null;

   public static Connection getConnection() throws Exception {
      Class.forName("oracle.jdbc.OracleDriver");
      Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xe","system","sys1234");
      return con;
   }
   public String selectAll(HttpServletRequest request, HttpServletResponse response) 
         throws ServletException, IOException{
      ArrayList<Member> list = new ArrayList<Member>();
      try {
         conn = getConnection();
         String sql = "SELECT M1.M_NO,M1.M_NAME,P1.P_NAME, ";
         sql += "DECODE(M1.P_SCHOOL,'1','고졸','2','학사','3','석사','4','박사')P_SCHOOL, ";
         sql += "SUBSTR(M_JUMIN,1,6)||'-'||SUBSTR(M_JUMIN,7)M_JUMIN, M_CITY, ";
         sql += "P1.P_TEL1||'-'||P1.P_TEL2||'-'||P1.P_TEL3 AS TEL ";
         sql += "FROM TBL_PARTY_202005 P1, TBL_MEMBER_202005 M1 ";
         sql += "WHERE P1.P_CODE = M1.P_CODE";
      
          ps = conn.prepareStatement(sql);
          rs = ps.executeQuery();
          
          while (rs.next()) {
             Member member = new Member();
             member.setM_no(rs.getString(1));
             member.setM_name(rs.getString(2));
             member.setP_code(rs.getString(3));
             member.setP_school(rs.getString(4));
             member.setM_jumin(rs.getString(5));
             member.setM_city(rs.getString(6));
             member.setP_tel(rs.getString(7));
             
      
             list.add(member);
          }
          System.out.println(list);
          request.setAttribute("list", list);
          
          conn.close();
          ps.close();
          rs.close();
          
      }catch (Exception e) {
         e.printStackTrace();
      }
      return "list.jsp";
   }

   
   public int insert(HttpServletRequest request, HttpServletResponse response)
		   throws ServletException {
	   int result = 0;
	   try {
		   conn = getConnection();
		   // prepareStatement는 순서에 따라 값이 등록된다.
	   String v_jumin = request.getParameter("v_jumin");
	   String v_name = request.getParameter("v_name");
	   String m_no = request.getParameter("m_no");
	   String v_time = request.getParameter("v_time");
	   String v_area = request.getParameter("v_area");
	   String v_confirm = request.getParameter("v_confirm");
	   
		   String sql = "INSERT INTO tbl_vote_202005 VALUES(?,?,?,?,?,?)";
		   ps = conn.prepareStatement(sql);
		   ps.setString(1, v_jumin);
		   ps.setString(2, v_name);
		   ps.setString(3, m_no);
		   ps.setString(4, v_time);
		   ps.setString(5, v_area);
		   ps.setString(6, v_confirm);
		   
			result = ps.executeUpdate(); // 0실패, 1성공
			System.out.println(result);	
			conn.close();
			ps.close();
		   

	   }catch (Exception e) {
		   e.printStackTrace();
	   }
	   return result;  
	   
   }
   
   public String selectVote(HttpServletRequest request, HttpServletResponse response){
	 	
		ArrayList<Vote> list = new ArrayList<Vote>();	
		try {
		conn = getConnection();
				
			//투표검수조회 화면 쿼리
		String sql = "SELECT V_NAME,";
		sql += "'19'||SUBSTR(V_JUMIN,1,2)||'년'||SUBSTR(V_JUMIN,3,2)||'월'||SUBSTR(V_JUMIN,5,2)||'일생',";
		sql += "'만 '||(TO_NUMBER(TO_CHAR(SYSDATE,'YYYY'))";
		sql += "-TO_NUMBER('19'||SUBSTR(V_JUMIN,1,2)))||'세' V_AGE,";
		sql += "DECODE(SUBSTR(V_JUMIN,7,1),'1','남','2','여')V_GENDER,";
		sql += "M_NO,";
		sql += "SUBSTR(V_TIME,1,2)||':'||SUBSTR(V_TIME,3,2) V_TIME,";
		sql += "DECODE(V_CONFIRM,'Y','확인','N','미확인') V_CONFIRM ";
		sql += "FROM TBL_VOTE_202005";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
			
		while(rs.next()) {
			Vote vote = new Vote();
				
			vote.setV_name(rs.getString(1));
			vote.setV_jumin(rs.getString(2));
			vote.setV_age(rs.getString(3));
			vote.setV_gender(rs.getString(4));
			vote.setM_no(rs.getString(5));
			vote.setV_time(rs.getString(6));
			vote.setV_confirm(rs.getString(7));
			
			list.add(vote);
		}
		request.setAttribute("list",list);
		conn.close();
		ps.close();
		rs.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}	
		return "votelist.jsp";
	}
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
}