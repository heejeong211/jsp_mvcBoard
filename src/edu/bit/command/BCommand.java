package edu.bit.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BCommand { // command �������̽� ���� ������ ����
	void execute(HttpServletRequest request, HttpServletResponse response);
}
