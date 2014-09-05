package com.hanapsim.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hanapsim.model.User;

/**
 * Servlet Filter implementation class UserCheckFilter
 */
public class LoginCheckFilter extends AbstractFilter implements Filter {
	private static List<String> allowedURIs;

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		if (allowedURIs == null) {
			allowedURIs = new ArrayList<String>();
			allowedURIs.add(fConfig.getInitParameter("loginActionURI"));
			allowedURIs.add("/ActivPOS/javax.faces.resource/main.css.xhtml");
			allowedURIs.add("/ActivPOS/javax.faces.resource/login.css.xhtml");
			allowedURIs.add("/ActivPOS/javax.faces.resource/theme.css.xhtml");
			allowedURIs
					.add("/ActivPOS/javax.faces.resource/primefaces.js.xhtml");
			allowedURIs
					.add("/ActivPOS/javax.faces.resource/primefaces.css.xhtml");
			allowedURIs
					.add("/ActivPOS/javax.faces.resource/jquery/jquery.js.xhtml");
			allowedURIs
					.add("/ActivPOS/javax.faces.resource/messages/messages.png.xhtml");
			allowedURIs
					.add("/ActivPOS/javax.faces.resource/images/favicon.ico.xhtml");
			allowedURIs
					.add("/ActivPOS/javax.faces.resource/images/activpos_logo.png.xhtml");
			allowedURIs
					.add("/ActivPOS/javax.faces.resource/images/activpos_login_body.png.xhtml");
			allowedURIs
					.add("/ActivPOS/javax.faces.resource/images/ui-icons_2e83ff_256x240.png.xhtml");
			allowedURIs
					.add("/ActivPOS/javax.faces.resource/images/ui-icons_38667f_256x240.png.xhtml");
			allowedURIs
					.add("/ActivPOS/javax.faces.resource/watermark/watermark.js.xhtml");
			allowedURIs
					.add("/ActivPOS/javax.faces.resource/images/activpos_login_background.jpg.xhtml");
			allowedURIs
					.add("/ActivPOS/javax.faces.resource/watermark/watermark.css.xhtml");
			allowedURIs
					.add("/ActivPOS/javax.faces.resource/jquery/jquery-plugins.js.xhtml");
			allowedURIs
					.add("/ActivPOS/javax.faces.resource/images/ui-icons_ffffff_256x240.png.xhtml");
			allowedURIs
					.add("/ActivPOS/javax.faces.resource/images/login.png.xhtml");
			allowedURIs
					.add("/ActivPOS/javax.faces.resource/ActivPOS/images/login.png");
			allowedURIs
					.add("/ActivPOS/javax.faces.resource/ActivPOS/images/icon/close.png");
		}
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();

		if (session.isNew()) {
			doLogin(request, response, req);
			return;
		}

		User user = (User) session.getAttribute("user");

		if (user == null && !allowedURIs.contains(req.getRequestURI())) {
//			System.out.println(req.getRequestURI());
			doLogin(request, response, req);
			return;
		}

		chain.doFilter(request, response);
	}
}