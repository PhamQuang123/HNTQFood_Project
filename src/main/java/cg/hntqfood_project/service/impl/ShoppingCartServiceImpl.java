package cg.hntqfood_project.service.impl;

import cg.hntqfood_project.model.entity.Product;
import cg.hntqfood_project.model.entity.ShoppingCart;
import cg.hntqfood_project.repository.ProductRepository;
import cg.hntqfood_project.repository.impl.ProductRepositoryImp;
import cg.hntqfood_project.service.ShoppingCartService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartServiceImpl implements ShoppingCartService {
    private ProductRepository productRepository;

    public ShoppingCartServiceImpl() {
        productRepository = new ProductRepositoryImp();
    }

    @Override
    public boolean add(HttpServletRequest request, HttpServletResponse response) {
        List<ShoppingCart> shoppingCartList = new ArrayList();
        String email = findEmailOnline(request, response);
        HttpSession session = request.getSession(true);
        if (email != null) {
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            int idProduct = Integer.parseInt(request.getParameter("idProduct"));
            Product product = productRepository.findProductById(idProduct);
            if (checkProductInCart(request, response)){
                double totalPrice = quantity * product.getPrice();
                ShoppingCart cart = new ShoppingCart();
                int quantityProduct = findTotalProduct(request, response);
                cart.setId(quantityProduct + 1);
                cart.setTotalPrice(totalPrice);
                cart.setQuantity(quantity);
                cart.setProduct(product);
                shoppingCartList.add(cart);
                session.setAttribute(email,shoppingCartList);
                return true;
            }else {
                shoppingCartList = (List<ShoppingCart>) session.getAttribute(email);
                for (ShoppingCart cart : shoppingCartList){
                    if (cart.getProduct().getProductName().equals(product.getProductName())){
                        cart.setQuantity(cart.getQuantity() + quantity);
                        cart.setTotalPrice(cart.getTotalPrice() + (quantity*product.getPrice()));
                    }
                }
                return true;
            }
        } else {
            return false;
        }
    }


    @Override
    public boolean checkProductInCart(HttpServletRequest request, HttpServletResponse response) {
        String email = findEmailOnline(request, response);
        String product = null;
        List<ShoppingCart> shoppingCartList = new ArrayList();
        if (email != null) {
            String shoppingCart = null;
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(email)) {
                    shoppingCart = cookie.getValue();
                }
            }
            String[] parts = shoppingCart.split(";");
            for (String part : parts) {
                String[] partList = part.split(",");
                product = partList[3];
            }
            int idProduct = Integer.parseInt(request.getParameter("idProduct"));
            Product productNew = productRepository.findProductById(idProduct);
            if (product.equals(productNew.toString())){
                return true;
            }else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public int findTotalProduct(HttpServletRequest request, HttpServletResponse response) {
        String email = findEmailOnline(request, response);
        String shoppingCart = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(email)) {
                shoppingCart = cookie.getValue();
            }
        }
        String[] parts = shoppingCart.split(";");
        int quantityProduct = parts.length;
        if (quantityProduct == 0) {
            return 0;
        } else {
            return quantityProduct;
        }
    }

    @Override
    public String findEmailOnline(HttpServletRequest request, HttpServletResponse response) {
        String email = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("email")) {
                    email = cookie.getValue();
                }
            }
            return email;
        } else {
            return null;
        }
    }
}
