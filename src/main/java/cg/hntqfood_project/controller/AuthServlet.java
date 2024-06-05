package cg.hntqfood_project.controller;

import cg.hntqfood_project.service.AuthService;
import cg.hntqfood_project.service.UsersService;
import cg.hntqfood_project.service.impl.AuthServiceImpl;
import cg.hntqfood_project.service.impl.UsersServiceImp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AuthServlet", value = "/HNQTFood.com.vn/*")
public class AuthServlet extends HttpServlet {
    private AuthService authService;

    @Override
    public void init() throws ServletException {
       authService = new AuthServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String url = request.getPathInfo();
        switch (url){
            case "/sign_in":
                authService.renderSignIn(request,response);
                break;
            case "/sign_up":
                authService.renderSignUp(request,response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String url = request.getPathInfo();
        switch (url){
            case "":
                break;
        }
    }
}