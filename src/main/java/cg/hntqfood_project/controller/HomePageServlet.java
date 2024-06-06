package cg.hntqfood_project.controller;

import cg.hntqfood_project.service.HomePageService;
import cg.hntqfood_project.service.impl.HomePageServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "HomeFoodServlet", value = "/HNQTFood.com.vn/v1/*")
public class HomePageServlet extends HttpServlet {
    private HomePageService homePageService;

    @Override
    public void init() throws ServletException {
        homePageService = new HomePageServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String url = request.getPathInfo();
        switch (url) {
            case "home_page":
                homePageService.renderHomePage(request,response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}