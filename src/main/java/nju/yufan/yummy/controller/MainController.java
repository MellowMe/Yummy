package nju.yufan.yummy.controller;

import nju.yufan.yummy.model.*;
import nju.yufan.yummy.service.*;
import nju.yufan.yummy.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
	@Autowired
	private RestaurantService restaurantService;
	@Autowired
	private UserService userService;
	@Autowired
	private MailService mailService;
	@Autowired
	private TemplateEngine templateEngine;
	@Autowired
	private OrderService orderService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private DishService dishService;
	@Autowired
	private AdminService adminService;

	@RequestMapping("/user/login")
	public void login(HttpSession session, HttpServletResponse response, String email, String password) {
		User user = userService.login(email, password);
		if (user != null && user.isAlive()) {
			session.setAttribute("user", user);
			response.setStatus(200);
		} else {
			response.setStatus(404);
		}
	}

	@RequestMapping("/")
	public String homePage(Model model) {
		model.addAttribute("restaurants", restaurantService.getAll());
		return "yummy";
	}

	@RequestMapping("/restaurants")
	public String chooseCategory(Model model, String cate) {
		model.addAttribute("restaurants", restaurantService.getByCate(cate));
		return "restaurants";
	}

	@PostMapping("/user/register")
	public void register(String email, String username, String password, String phone, HttpServletResponse response) {
		User user = new User(email, username, password, phone);
		String token = Utils.generateToken();
		user.setToken(token);
		user.setTokenExpireTime(new Timestamp(System.currentTimeMillis() + 24 * 3600000));
		if (userService.insert(user) > 0) {
			Context context = new Context();
			context.setVariable("uid", user.getId());
			context.setVariable("token", token);
			String content = templateEngine.process("activateMail", context);
			mailService.sendHTMLMail(email, "Yummy Account", content);
			response.setStatus(200);
		} else {
			response.setStatus(400);
		}

	}

	@RequestMapping("/user/register/activation")
	public String activateAccount(Model model, int uid, String token) {
		Token t = userService.getToken(uid);
		if (token.equals(t.getToken()) && new Timestamp(System.currentTimeMillis()).before(t.getExpireTime())) {
			model.addAttribute("registerActivation", "账户激活成功");
			userService.activate(uid);
		} else {
			model.addAttribute("registerActivation", "账户激活失败：已超过激活期限");
		}
		return "message";
	}

	@RequestMapping("/restaurant")
	public String visitRestaurant(Model model, int id) {
		model.addAttribute("rest", restaurantService.getById(id));
		model.addAttribute("dishes", dishService.getSortedDishes(id));
		return "shop";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/add2cart")
	public void add2cart(int id, int num, HttpServletResponse response, HttpSession session) {
		Map<Restaurant, Map<Dish, Integer>> cart = (Map<Restaurant, Map<Dish, Integer>>) session.getAttribute("cart");
		Dish dish = dishService.getById(id);
		Map<Dish, Integer> items;
		if (dish != null) {
			Restaurant restaurant = restaurantService.getById(dish.getRestid());
			if (cart == null) {
				cart = new HashMap<>();
				items = new HashMap<>();
				items.put(dish, num);
				cart.put(restaurant, items);
				session.setAttribute("cart", cart);
			} else {
				if (cart.containsKey(restaurant)) {
					items = cart.get(restaurant);
					if (items.containsKey(dish)) {
						int old = items.get(dish);
						items.put(dish, old + num);
					} else {
						items.put(dish, num);
					}
				} else {
					items = new HashMap<>();
					items.put(dish, num);
					cart.put(restaurant, items);
				}

			}
			response.setStatus(200);
		} else
			response.setStatus(400);

	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/resetCart")
	public void resetCart(int restid, int did, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Map<Restaurant, Map<Dish, Integer>> cart = (Map<Restaurant, Map<Dish, Integer>>) session.getAttribute("cart");
		Restaurant restaurant = new Restaurant();
		Dish dish = new Dish();
		restaurant.setId(restid);
		dish.setId(did);
		Map<Dish, Integer> items = cart.get(restaurant);
		String num = request.getParameter("num");
		if (num == null) {
			items.remove(dish);
			if (items.size() == 0)
				cart.remove(restaurant);
		} else {
			items.put(dish, Integer.parseInt(num));
		}
	}


	@SuppressWarnings("unchecked")
	@RequestMapping("/user/cart")
	public String myCart(Model model, HttpSession session) {
		Map<Restaurant, Map<Dish, Integer>> cart = (Map<Restaurant, Map<Dish, Integer>>) session.getAttribute("cart");
		if (cart != null) {
			Map<Restaurant, Double> money = new HashMap<>();
			double sum;
			Map<Dish, Integer> items;
			for (Restaurant rest : cart.keySet()) {
				sum = 0;
				items = cart.get(rest);
				for (Dish dish : items.keySet()) {
					sum = sum + dish.getPrice() * items.get(dish);
				}
				money.put(rest, sum);
			}
			model.addAttribute("money", money);
		}
		if (session.getAttribute("user") != null)
			model.addAttribute("addresses", addressService.getAddresses(((User) session.getAttribute("user")).getId()));
		return "cart";
	}


	@SuppressWarnings("unchecked")
	@RequestMapping("makeOrder")
	@ResponseBody
	public int makeOrder(int restid, int aid, double money, String address, HttpSession session) {
		Map<Restaurant, Map<Dish, Integer>> cart = (Map<Restaurant, Map<Dish, Integer>>) session.getAttribute("cart");
		int uid = ((User) session.getAttribute("user")).getId();
		Restaurant rest = new Restaurant();
		rest.setId(restid);
		Map<Dish, Integer> items = cart.get(rest);
		if (aid == 0 && !address.equals("")) {
			Address address1;
			if (addressService.getDefault(uid) == null)
				address1 = new Address(uid, address, true);
			else
				address1 = new Address(uid, address, false);
			addressService.insert(address1);
			aid = address1.getId();
		}
		Order order = new Order(uid, restid, aid, new Timestamp(System.currentTimeMillis()), money);
		orderService.insert(order);
		int oid = order.getId();
		List<OrderItem> list = new ArrayList<>();
		for (Dish dish : items.keySet()) {
			list.add(new OrderItem(oid, dish.getId(), items.get(dish), dish.getPrice()));
		}
		orderService.insertItems(list);
		cart.remove(rest);
		return oid;
	}

	@RequestMapping("/user/pay")
	@Transactional
	public void doPay(int oid, HttpServletResponse response) {
		if (orderService.setStatus(oid, 2) == 1) {
			Order order = orderService.getOrder(oid);
			Dish dish;
			for (OrderItem item : order.getItems()) {
				dish = item.getDish();
				dishService.setNum(dish.getId(), dish.getNum() - item.getNumber());
			}
			userService.pay(order.getUserid(), order.getMoney());
			restaurantService.receiveMoneyFromUser(order.getRestid(), order.getMoney() * 0.8);
			adminService.recharge(order.getMoney() * 0.2);
			response.setStatus(200);
		} else
			response.setStatus(400);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/user/orders")
	public String queryOrders(Model model, HttpSession session) {
		if (session.getAttribute("user") != null) {
			int uid = ((User) session.getAttribute("user")).getId();
			List<Order> orders = orderService.getOrders(uid);
			long time = System.currentTimeMillis();
			for (Order order : orders) {
				if (order.getStatus() == 1 && time - order.getTime().getTime() > 120000) {
					orderService.setStatus(order.getId(), 0);
				}
			}
			model.addAttribute("orders", orders);
		}
		return "orders";
	}

	@RequestMapping("/user/deleteOrder")
	public void deleteOrder(int oid, HttpServletResponse response) {
		int status = orderService.getOrder(oid).getStatus();
		if (status == 0) {
			if (orderService.delete(oid) == 1) {
				response.setStatus(200);
			} else
				response.setStatus(400);
		} else
			response.setStatus(400);
	}

	@RequestMapping("/user/cancelOrder")
	public void cancelOrder(int oid, HttpServletResponse response) {
		int status = orderService.getOrder(oid).getStatus();
		if (status == 1) {
			if (orderService.setStatus(oid, 0) == 1) {
				response.setStatus(200);
			} else
				response.setStatus(400);
		} else
			response.setStatus(400);
	}

	@RequestMapping("/user/chargeBack")
	@Transactional
	public void chargeBack(int oid, HttpServletResponse response) {
		Order order = orderService.getOrder(oid);
		int status = order.getStatus();
		if (status == 2) {
			if (orderService.setStatus(oid, 0) == 1) {
				userService.recharge(order.getUserid(), order.getMoney());
				restaurantService.moneyBack(order.getRestid(), order.getMoney() * 0.8);
				adminService.moneyBack(order.getMoney() * 0.2);
				response.setStatus(200);
			} else
				response.setStatus(400);
		} else if (status == 3) {
			if (orderService.setStatus(oid, 0) == 1) {
				userService.recharge(order.getUserid(), order.getMoney() * 0.5);
				restaurantService.moneyBack(order.getRestid(), order.getMoney() * 0.5 * 0.8);
				adminService.moneyBack(order.getMoney() * 0.5 * 0.2);
				response.setStatus(200);
			} else
				response.setStatus(400);
		} else
			response.setStatus(400);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/user/zone")
	public String userZone(Model model, HttpSession session) {
		if (session.getAttribute("user") != null) {
			int uid = ((User) session.getAttribute("user")).getId();
			model.addAttribute("addresses", addressService.getAddresses(uid));
		}
		return "userinfo";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/user/deleteAddress")
	@Transactional
	public void deleteAddress(int aid, HttpServletResponse response, HttpSession session) {
		int uid = ((User) session.getAttribute("user")).getId();
		if (addressService.deleteById(aid) == 1) {
			List<Address> addresses = addressService.getAddresses(uid);
			if (addresses != null && addresses.size() > 0)
				addressService.setDefault(addresses.get(0).getId(), 1);
			response.setStatus(200);
		} else {
			response.setStatus(400);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/user/addAddress")
	public void addAddress(String address, HttpSession session, HttpServletResponse response) {
		int uid = ((User) session.getAttribute("user")).getId();
		Address a;
		if (addressService.getDefault(uid) == null)
			a = new Address(uid, address, true);
		else
			a = new Address(uid, address, false);
		if (addressService.insert(a) == 1) {
			response.setStatus(200);
		} else {
			response.setStatus(400);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/user/nullify")
	public void nullify(HttpSession session, HttpServletResponse response) {
		int uid = ((User) session.getAttribute("user")).getId();
		if (userService.logoff(uid) == 1) {
			response.setStatus(200);
			session.removeAttribute("user");
		} else {
			response.setStatus(400);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/user/modify")
	@Transactional
	public void modifyUser(String username, String password, String phone, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		User user = (User) session.getAttribute("user");
		user.setUsername(username);
		user.setPassword(password);
		user.setPhone(phone);
		userService.update(user);
		if (request.getParameter("defaultAid") != null) {
			int defaultAid = Integer.parseInt(request.getParameter("defaultAid"));
			int uid = user.getId();
			Address address = addressService.getDefault(uid);
			if (defaultAid != address.getId()) {
				addressService.setDefault(address.getId(), 0);
				addressService.setDefault(defaultAid, 1);
			}
		}
		response.setStatus(200);
	}

	@RequestMapping("/rest/login")
	public void restLogin(int code, String password, HttpSession session, HttpServletResponse response) {
		Restaurant restaurant = restaurantService.getById(code);
		if (restaurant != null && restaurant.getPassword().equals(password)) {
			session.setAttribute("rest", restaurant);
			response.setStatus(200);
		} else {
			response.setStatus(400);
		}
	}

	@RequestMapping("/rest/register")
	@ResponseBody
	public String restRegister(String name, String password, String location, String phone) {
		Restaurant restaurant = new Restaurant(name, password, location, phone);
		restaurantService.insert(restaurant);
		return String.valueOf(restaurant.getId());
	}

	@RequestMapping("/rest/operatePage")
	public String restAct(Model model, HttpSession session) {
		Restaurant restaurant = (Restaurant) session.getAttribute("rest");
		if (restaurant != null) {
			model.addAttribute("orders", orderService.getOrders4Rest(restaurant.getId()));
		}
		return "rest_act";
	}

	@RequestMapping("/rest/zone")
	public String restZonePage(HttpSession session, Model model) {
		RestaurantBack back = null;
		if (session.getAttribute("rest") != null) {
			back = restaurantService.selectBack(((Restaurant) session.getAttribute("rest")).getId());
		}
		model.addAttribute("back", back);
		return "rest_zone";
	}

	@RequestMapping("/rest")
	public String rest(Model model, HttpSession session) {
		Restaurant restaurant = (Restaurant) session.getAttribute("rest");
		if (restaurant != null) {
			model.addAttribute("dishes", dishService.getSortedDishes(restaurant.getId()));
		}
		return "rest_home";
	}

	@RequestMapping("/rest/deliver")
	public void deliverOrder(int oid, HttpServletResponse response) {
		int status = orderService.getOrder(oid).getStatus();
		if (status == 2) {
			if (orderService.setStatus(oid, 3) == 1) {
				response.setStatus(200);
			} else
				response.setStatus(400);
		} else
			response.setStatus(400);
	}

	@RequestMapping("/rest/done")
	public void doneOrder(int oid, HttpServletResponse response) {
		int status = orderService.getOrder(oid).getStatus();
		if (status == 3) {
			if (orderService.setStatus(oid, 4) == 1) {
				response.setStatus(200);
			} else
				response.setStatus(400);
		} else
			response.setStatus(400);
	}

	@RequestMapping("/rest/dish/modifyNum")
	public void modifyDishNum(int did, int num, HttpServletResponse response) {
		dishService.setNum(did, num);
		response.setStatus(200);
	}

	@RequestMapping("/rest/dish/delete")
	public void deleteDish(int did, HttpServletResponse response) {
		dishService.delete(did);
		response.setStatus(200);
	}

	@RequestMapping("/rest/modify")
	@Transactional
	public void modifyRest(String name, String password, String location, String phone, MultipartFile sign, String openhours, String type, HttpSession session, HttpServletResponse response) {
		Restaurant restaurant = (Restaurant) session.getAttribute("rest");
		int id = restaurant.getId();
		Restaurant back;
		String path = "C:\\Users\\yufan\\workspace\\yummy\\src\\main\\resources\\static\\img\\";
		if (sign == null || "".equals(sign.getOriginalFilename())) {
			back = new Restaurant(id, name, password, location, type, phone, openhours, restaurant.getSign());
		} else {
			back = new Restaurant(id, name, password, location, type, phone, openhours, "rest_sign_" + id + sign.getOriginalFilename());
			File dest = new File(path + "rest_sign_" + id + sign.getOriginalFilename());
			try {
				sign.transferTo(dest);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		RestaurantBack oldModify = restaurantService.selectBack(id);
		if (oldModify == null) {
			restaurantService.addModify2back(back);
		} else {
			restaurantService.updateBack(back);
			File old_modify_sign = new File(path + oldModify.getSign());
			if (old_modify_sign.exists())
				old_modify_sign.delete();
		}
		response.setStatus(200);
	}

	@RequestMapping("/admin")
	public String adminLogin(Model model, HttpSession session) {
		Administrator admin = (Administrator) session.getAttribute("admin");
		if (admin != null) {
			List<RestaurantBack> backs = restaurantService.selectAllBack();
			List<RestAndBack> list = new ArrayList<>();
			RestAndBack pair;
			Restaurant rest;
			for (RestaurantBack back : backs) {
				rest = restaurantService.getById(back.getId());
				pair = new RestAndBack(rest, back);
				list.add(pair);
			}
			model.addAttribute("pairs", list);
		}
		return "admin_home";
	}

	@RequestMapping("/admin/login")
	public void adminPage(String name, String password, HttpSession session, HttpServletResponse response) {
		Administrator admin = adminService.login(name, password);
		if (admin != null) {
			session.setAttribute("admin", admin);
			response.setStatus(200);
		} else {
			response.setStatus(400);
		}
	}

	@RequestMapping("/admin/rest/modifyInfo")
	@Transactional
	public void validateRestModify(HttpSession session, int restid, boolean accept, HttpServletResponse response) {
		RestaurantBack back = restaurantService.selectBack(restid);
		if (back != null) {
			Restaurant restaurant = new Restaurant(back.getId(), back.getName(), back.getPassword(), back.getLocation(), back.getType(), back.getPhone(), back.getOpenhours(), back.getSign());
			if (accept) {
				if (restaurantService.update(restaurant) == 1) {
					if(session.getAttribute("rest")!=null){
						session.setAttribute("rest",restaurantService.getById(restid));
					}
					restaurantService.deleteBack(restid);
					response.setStatus(200);
				} else {
					response.setStatus(400);
				}
			} else {
				restaurantService.deleteBack(restid);
				response.setStatus(200);
			}
		} else {
			response.setStatus(400);
		}
	}

	@RequestMapping("/rest/addDish")
	public String addDishPage() {
		return "addDish";
	}

	@PostMapping("/rest/dish/add")
	public void addDish(String name,double price, String cate, MultipartFile picture,HttpSession session,HttpServletResponse response) {
		if(picture==null||"".equals(picture.getOriginalFilename())){
			response.setStatus(400);
		}else{
			if(session.getAttribute("rest")!=null){
				int restid = ((Restaurant)session.getAttribute("rest")).getId();
				String path =  "C:\\Users\\yufan\\workspace\\yummy\\src\\main\\resources\\static\\img\\";
				File dest = new File(path+"rest_dish_"+restid+picture.getOriginalFilename());
				try {
					picture.transferTo(dest);
				} catch (IOException e) {
					e.printStackTrace();
				}
				Dish dish = new Dish(restid,name,cate,price,"rest_dish_"+restid+picture.getOriginalFilename());
				if(dishService.insert(dish)==1){
					response.setStatus(200);
				}
			}else {
				response.setStatus(400);
			}
		}
	}

}
