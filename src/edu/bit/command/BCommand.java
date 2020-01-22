package edu.bit.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BCommand { // command 인터페이스 만들어서 다형성 적용
	void execute(HttpServletRequest request, HttpServletResponse response);
}
