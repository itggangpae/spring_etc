package kr.co.adamsoft.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface EtcService {
	public void sendMail(HttpServletRequest request);
	public void htmlMail();
	public void push(HttpServletRequest request, HttpServletResponse response);
	public String download(HttpServletRequest request, HttpServletResponse response);

}
