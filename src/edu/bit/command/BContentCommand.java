package edu.bit.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.bit.dao.BDao;
import edu.bit.dto.BDto;

public class BContentCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		String bId = request.getParameter("bId"); // url로 넘어오는 것은 getParameter이다.
		
		BDao dao = new BDao();
		BDto dto = dao.contentView(bId);
		
		// 핵심 코드!!!!!!!!
		request.setAttribute("content_view", dto); // jsp에 dto정보를 넘기는 코드

	}

}
