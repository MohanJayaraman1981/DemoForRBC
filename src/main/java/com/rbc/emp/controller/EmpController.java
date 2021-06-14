package com.rbc.emp.controller;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rbc.emp.model.Employee;
import com.rbc.emp.model.User;
import com.rbc.emp.repository.EmployeeRepository;
import com.rbc.emp.repository.UserRepository;



@Controller
public class EmpController implements ErrorController{

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private EmployeeRepository employeeRepo;
	
	@GetMapping("/")
	public String viewIndexPage() {
		return "index";
	}
	
	@GetMapping("/home")
	public String viewHomePage() {
		return "home";
	}
	
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		
		return "signup_form";
	}
	
	@PostMapping("/process_register")
	public String processRegister(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		System.out.println("DATE in Controller---->"+user.getEntryDate());
		userRepo.save(user);
		
		return "register_success";
	}
	
	@GetMapping("/users")
	public String listUsers(Model model) {
		List<User> listUsers = userRepo.findAll();
		model.addAttribute("listUsers", listUsers);
		
		return "users";
	}
	
	@GetMapping("/addEmployee")
	public String addEmployee(Model model) {
		model.addAttribute("user", new Employee());
		
		return "add_employee";
	}
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(Employee entry) {			
		employeeRepo.save(entry);	
		return "employee";		
	}
	
	@GetMapping("/getEmployee")
	public ModelAndView getEmployee(@RequestParam long id) {
		ModelAndView mv = new ModelAndView();
		Employee entry = employeeRepo.findById(id).orElse(new Employee());
		mv.addObject(entry);
		mv.setViewName("getEmployee");
		return mv;			
	}
	
	@GetMapping("/deleteEmployee")
	public String deleteEmployeePage(Model model) {		
		model.addAttribute("user", new Employee());		
		List<Employee> listUsers = employeeRepo.findAll();
		model.addAttribute("listEmployees", listUsers);	
		return "delete_employee";
	}
	
	
	@PostMapping("/deleteEmployee")
	public String deleteEntry(@RequestParam long id) {
		employeeRepo.deleteById(id);	
		return "employee";
	}
	

	@GetMapping("/listEmployees")
	public String listemployees(Model model) {
		model.addAttribute("user", new Employee());	
		return "employee";
	}
	

	@GetMapping("/viewEmployees")
	public String viewEmployees(Model model) {
		List<Employee> listUsers = employeeRepo.findAll();
		model.addAttribute("listEmployees", listUsers);		
		return "view_employee";
	}
	
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	    
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	    
	        if(statusCode == HttpStatus.NOT_FOUND.value()) {
	            return "error-404";
	        }
	        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	            return "error-500";
	        }
	    }
	    return "error";
	}

	@Override
	public String getErrorPath() {
		return null;
	}
	
	
}
