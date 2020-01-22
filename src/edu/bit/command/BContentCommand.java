package edu.bit.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.bit.dao.BDao;
import edu.bit.dto.BDto;

public class BContentCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		String bId = request.getParameter("bId"); // url�� �Ѿ���� ���� getParameter�̴�.
		
		BDao dao = new BDao();
		BDto dto = dao.contentView(bId);
		
		// �ٽ� �ڵ�!!!!!!!!
		request.setAttribute("content_view", dto); // jsp�� dto������ �ѱ�� �ڵ�

	}

}
