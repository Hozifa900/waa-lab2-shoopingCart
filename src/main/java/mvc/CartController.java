package mvc;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CartController {

    @PostMapping("/showcart")
    public ModelAndView add(HttpSession session, @RequestParam("pnumber") String pnumber) {
        Map<String, Object> params = new HashMap<>();

        if (pnumber != null) {
            // get the productlist from the session
            Map<String, Product> productList = (Map<String, Product>) session.getAttribute("productList");
            // get the product from the productlist
            Product product = productList.get(pnumber);

            // get the cartlist from the session
            Map<String, CartItem> cartList = (Map<String, CartItem>) session.getAttribute("cartList");
            // if there is no cartlist in the session, create one.
            if (cartList == null) {
                cartList = new HashMap<String, CartItem>();
                session.setAttribute("cartList", cartList);
            }
            // check if the product is already in the cart
            CartItem cartItem = cartList.get(pnumber);
            if (cartItem == null) {
                cartItem = new CartItem(product, 1);
            } else {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
            }
            // update the cartlist
            cartList.put(pnumber, cartItem);
            params.put("cartList", cartList.values());
        }
        return new ModelAndView("cart", params);
    }

    @PostMapping("/removecartitem")
    public ModelAndView removeProduct(@RequestParam("pnumber") String pnumber,
            HttpSession session) {
        Map<String, Object> params = new HashMap<>();
        if (pnumber != null) {
            // get the cartlist from the session
            Map<String, CartItem> cartList = (Map<String, CartItem>) session.getAttribute("cartList");
            // if there is no cartlist in the session, create one.
            if (cartList == null) {
                cartList = new HashMap<String, CartItem>();
                session.setAttribute("cartlist", cartList);
            }
            // remove the product from the cartlist
            if (cartList.get(pnumber).getQuantity() > 1) {
                cartList.get(pnumber).setQuantity(cartList.get(pnumber).getQuantity() - 1);
            } else {
                cartList.remove(pnumber);
            }
            params.put("cartList", cartList.values());
        }
        return new ModelAndView("cart", params);
    }
}
