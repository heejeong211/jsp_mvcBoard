package edu.bit.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.bit.command.BCommand;
import edu.bit.command.BContentCommand;
import edu.bit.command.BDeleteCommand;
import edu.bit.command.BListCommand;
import edu.bit.command.BModifyCommand;
import edu.bit.command.BReplyCommand;
import edu.bit.command.BReplyViewCommand;
import edu.bit.command.BWriteCommand;

/**
 * Servlet implementation class BFrontController
 */
@WebServlet("*.do") // 확장자 패턴으로 url을 받아냄.
public class BFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BFrontController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet");
		actionDo(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost");
		actionDo(request, response);
		
	}
	
	// request랑 response를 그대로 넘기기 때문에 그냥 위에꺼 복붙하는 거임.
	protected void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("actionDo"); // 디버깅 코드
		
		request.setCharacterEncoding("EUC-KR"); // request에서 한글이 넘어올 수 있기 때문에
		
		String viewPage = null;
		BCommand command = null;
		
		String uri = request.getRequestURI();
		String conPath = request.getContextPath(); // 톰캣이 관리하는 프로젝트
		String com = uri.substring(conPath.length());
		
		System.out.println("테스트 URI: " + uri + ":" + conPath + ":" + com);
		
		if(com.equals("/write_view.do")) {
			
			viewPage = "write_view.jsp"; // 입력하는 게 들어감, 클라이언트에게 보여주는 파일
		
		} else if(com.equals("/write.do")) { // FrontController 에서 Command 객체 생성 하는 것.
			
			command = new BWriteCommand(); // 다형성 적용. 폴리모피즘.
			command.execute(request, response);
			
			viewPage = "list1.do"; // 목록보기 파일!!
		
		} else if(com.equals("/list1.do")) { 
			command = new BListCommand();  // 다형성 적용.
			command.execute(request, response);
			
			viewPage = "list1.jsp"; // 목록보기 파일!! 이 상태에서 jsp 파일에  new해서 dto, dao의 객체생성하면 mvc 모델1 버전임. 모델2 버전에서는 var="dto" 처럼 객체 또는 데이터만 전달해야 한다, 클라이언트에게 보여주는 파일
		
		} else if(com.equals("/content_view.do")) {
			command = new BContentCommand();
			command.execute(request, response);
			
			viewPage = "content_view.jsp";
			
		} else if(com.equals("/modify.do")) {
			command = new BModifyCommand();
			command.execute(request, response);
			
			viewPage = "list1.do";
			
		} else if(com.equals("/delete.do")) {
			command = new BDeleteCommand();
			command.execute(request, response);
			
			viewPage = "list1.do";
	
		} else if(com.equals("/reply_view.do")) {
			command = new BReplyViewCommand();
			command.execute(request, response);
			
			viewPage = "reply_view.jsp";
		
		} else if(com.equals("/reply.do")) {
			command = new BReplyCommand();
			command.execute(request, response);
			
			viewPage = "list1.do";
		}
		
		// 핵심코드!!!!!!!!! - forwarding 해서 유저한테 직접적으로 보여주는 파일은 write_view.jsp 파일이다.
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage); // viewPage에서 .do url이 오면 이 서블릿을 한 번 더 탐.
		dispatcher.forward(request, response);
	}

}
