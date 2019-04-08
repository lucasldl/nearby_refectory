package cn.lucasldl.nearby_refectory.controller;

import cn.lucasldl.nearby_refectory.entity.Refectory;
import cn.lucasldl.nearby_refectory.service.RefectoryService;
import cn.lucasldl.nearby_refectory.util.Pagination;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Api(tags = "页面地址的重定向")
public class PageController {

	@RequestMapping("/login")
	public String login(){
		return "login";
	}

	@RequestMapping({"/admin", "/admin/", "/index"})
	public String index(){
		return "redirect:/admin/index";
	}

	@RequestMapping("/errors")
	public String error(){
		return "error";
	}

	@RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if(authentication != null){
	        new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
	    return "redirect:/login";
    }

}
