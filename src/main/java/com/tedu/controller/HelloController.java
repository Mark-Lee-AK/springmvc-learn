package com.tedu.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.pojo.User;

/*
 * @Controller 
 * 	在当前包下带有@Controller的类
 *	将来创建对象都是有spring容器负责创建
 *	同时通过@Controller表示当前类属于controller层
 */
@Controller
public class HelloController {

	/* * * * * * * * * * * * * * * * * * * * * * * 
	 * 	1.测试spring简单类型参数绑定	         *	
	 *	/testParam1?name=张三&age=20&addr=北京	 *
	 * * * * * * * * * * * * * * * * * * * * * * */
	@RequestMapping("/testParam1")
	public String testParam1(String name,int age,String addr) {
		System.out.println("name: " + name);
		System.out.println("age: " + age);
		System.out.println("addr: " + addr);
		return "home";
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 	测试spring简单类型参数绑定									 *
	 * 	/testParam2?username=李四&password=123&like=篮球&like=足球	 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@RequestMapping("/testParam2")
	public String testParam2(String username,String password,String[] like) {
		System.out.println("username: " + username);
		System.out.println("password: " + password);
		System.out.println("like: "+Arrays.toString(like));
		return "home";
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 	测试spring包装类型参数绑定							 	 *
	 * 	/testParam3?name=张飞&age=30&addr=东莞高埗	 			 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@RequestMapping("/testParam3")
	public String testParam3(User user) {
		System.out.println("name: " + user.getName());
		System.out.println("age: " + user.getAge());
		System.out.println("addr: " + user.getAddr());
		System.out.println(user);
		return "home";
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 测试spring日期类型参数绑定							 	 *
	 * /testParam4?date=2019-11-20 17:36:45 					 *
	 * /testParam4?date=2019/11/20 17:36:45  					 *
	 * --------------------------------------------------------- *
	 * 通过springmvc接收日期类型的参时,默认是以'/'分割,否则报400 *
	 * 错误,解决方法:要么使用'/'分割,要么使用@InitBinder对格式进 *
	 * 行自定义,详见以下InitBinder()方法                         *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@RequestMapping("/testParam4")
	public String testParam4(Date date) {
		System.out.println("date: " + date.toLocaleString());
		return "home";
	}
	// 自定义日期装换格式,默认是用'/'分割,加上后默认的分割就无法使用了
	@InitBinder
	public void InitBinder(ServletRequestDataBinder binder) {
		binder.registerCustomEditor(java.util.Date.class, 
				new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	
	/* * * * * * * * * * * * * * * * * * * *
	 * 测试通过Model带数据到jsp	           *
	 * 将User对象带到test.jsp中并取出显示  *
	 * * * * * * * * * * * * * * * * * * * */
	@RequestMapping("/testMode1")
	public String testMode1(Model model) {
		User u1 = new User();
		u1.setName("司马懿");
		u1.setAge(24);
		u1.setAddr("北魏");
		model.addAttribute("user", u1);
		return "test";
	}
	
	/* * * * * * * * * * * * * * * * * * * * * *
	 * 测试通过Model带数据到jsp	               *
	 * 将User对象集合带到test.jsp中并取出显示  *
	 * --------------------------------------- *
	 * Model带数据到springmvc提供的一个对象    *
	 * 底层是request域对象,可以通过	           *
	 * model.addAttribute(attrName,attrValue)  *
	 * 往域中添加数据,再通过最后的	           *
	 * return "jsp的名字" 转发到jsp中	       *
	 * * * * * * * * * * * * * * * * * * * * * */
	@RequestMapping("/testMode2")
	public String testMode2(Model model) {
		User u1 = new User();
		u1.setName("司马懿");
		u1.setAge(24);
		u1.setAddr("北魏");
		User u2 = new User();
		u2.setName("诸葛亮");
		u2.setAge(25);
		u2.setAddr("巴蜀");
		ArrayList<User> list = new ArrayList<>();
		list.add(u1);
		list.add(u2);
		list.add(new User("周瑜",23,"东吴"));
		list.add(new User("庞统",23,"巴蜀"));
		list.add(new User("徐庶",23,"北魏"));
		// 把集合存入域中
		model.addAttribute("list", list);
		// 转发到test.jsp
		return "test";
	}
	
	/* * * * * * * * * * * * * * * * * * * *
	 * 测试通过JSON带数据到客户端		   *
	 * 将User对象以JSON格式响应给客户端	   *
	 * * * * * * * * * * * * * * * * * * * */
	@RequestMapping("/testJson1")
	@ResponseBody //将返回值类型的数据转成Json对象输出
	public User testJson1() {
		User u1 = new User();
		u1.setName("吴国太");
		u1.setAge(88);
		u1.setAddr("东吴");
		return u1;
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * *
	 * 测试通过JSON带数据到客户端2				   *
	 * 将List<User>以Json格式响应给客户端浏览器	   *
	 * * * * * * * * * * * * * * * * * * * * * * * */
	@RequestMapping("/testJson2")
	@ResponseBody //将返回值类型的数据转成Json对象输出
	public List<User> testJson2() {
		User u1 = new User();
		u1.setName("司马懿");
		u1.setAge(24);
		u1.setAddr("北魏");
		User u2 = new User();
		u2.setName("诸葛亮");
		u2.setAge(25);
		u2.setAddr("巴蜀");
		ArrayList<User> list = new ArrayList<>();
		list.add(u1);
		list.add(u2);
		list.add(new User("周瑜",23,"东吴"));
		list.add(new User("庞统",23,"巴蜀"));
		list.add(new User("徐庶",23,"北魏"));
		return list;
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *	
	 *  预想访问路径: http://localhost/day16-springmvc/hello	         *
	 * 	用于映射请求的资源路径(/hello)和当前方法(testHello)的对应关系	 *
	 * 	当浏览器请求 .../hello 路径时,就会访问(执行)当前这个方法	     *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@RequestMapping("/hello")
	public String testHello() {
		System.out.println("HelloController.testHello()");
		return "home";
	}
	
	/* * * * * * * * * * * * * * * * * * * *
	 * 测试springmvc中的请求转发		   *
	 * 从/testForward转发到/hello路径	   *
	 * * * * * * * * * * * * * * * * * * * */
	@RequestMapping("/testForward")
	public String testForward() {
		System.out.println("HelloController.testForward()...");
		return "forward:/hello";
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * *
	 * 测试springmvc中的重定向			   			 *
	 * 从/testRedirect重定向到http//www.baidu.com	 *
	 * * * * * * * * * * * * * * * * * * * * * * * * */
	@RequestMapping("/testRedirect")
	public String testRedirect() {
		System.out.println("HelloController.testRedirect()");
		return "redirect:http://www.baidu.com";
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 测试springmvc中GET和POST提交的乱码问题				 *
	 * GET提交在tomcat8.0以后没有中文乱码问题				 *
	 * POST提交不管哪个版本的tomcat都会有中文参数乱码问题	 *
	 * Servlet的处理方式:request.setCharacterEncoding(...)	 *
	 * springmvc中是通过过滤器处理POST提交的参数乱码问题	 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@RequestMapping("/testParam5")
	public String testParam5(String username,String password) {
		System.out.println("username: "+ username);
		System.out.println("password: "+ password);
		return "home";
	}
	
	
	
	
	
	
}
