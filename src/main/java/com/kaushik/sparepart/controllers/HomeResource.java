package com.kaushik.sparepart.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kaushik.sparepart.exception.ResourceNotFoundException;
import com.kaushik.sparepart.models.AccountEntity;
import com.kaushik.sparepart.models.CreateOrderRequest;
import com.kaushik.sparepart.models.Orders;
import com.kaushik.sparepart.models.Role;
import com.kaushik.sparepart.models.RoleEnum;
import com.kaushik.sparepart.models.Spareparts;
import com.kaushik.sparepart.repository.AccountRepository;
import com.kaushik.sparepart.repository.CreateOrderRequestRepository;
import com.kaushik.sparepart.repository.OrdersRepository;
import com.kaushik.sparepart.repository.RoleRepository;
import com.kaushik.sparepart.repository.SparepartsRepository;
import com.kaushik.sparepart.service.SparepartsService;

@RestController
public class HomeResource {
	
	Logger logger = LoggerFactory.getLogger(HomeResource.class);

	@Autowired
	SparepartsService spserv;
	
	@Autowired
	SparepartsRepository sprepo;

	@Autowired
	OrdersRepository orepo;

	@Autowired
	CreateOrderRequestRepository correpo;	
	

	@Autowired
	private AccountRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public void creatUserIfNotFound(String userName, String password,Role[] role) {
		if(userRepository.findByEmail(userName) == null) {
			AccountEntity user = new AccountEntity();
			user.setFirstName("Test");
			user.setLasttName("usercin");
			user.setPassword(passwordEncoder.encode(password));
			user.setEmail(userName);

			Set<Role> roles = new HashSet<Role>(Arrays.asList(role));
			user.setRoles(roles);
			userRepository.save(user);
		}
		
	}

	@Transactional
	public void createRoleIfNotFound(String name) {

		Role role = roleRepository.findByName(name);
		if (role == null) {
			role = new Role();
			role.setName(name);
			roleRepository.save(role);
		}
	}
	 
	@GetMapping("/init")
	public String init() {
		SecurityContextHolder.clearContext();
		
		createRoleIfNotFound(RoleEnum.ROLE_ADMIN.name());
		createRoleIfNotFound(RoleEnum.ROLE_USER.name());


		Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN.name());
		Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER.name());

		
		creatUserIfNotFound("admin@test.com", "test123", new Role[]{adminRole,userRole});
		creatUserIfNotFound("user@test.com", "test123", new Role[]{userRole});
		
		return "Initialised the App";
	}

    @GetMapping("/")
    public String home() {
		logger.trace("A TRACE Message");
		logger.debug("A DEBUG Message");
		logger.info("An INFO Message");
		logger.warn("A WARN Message");
		logger.error("An ERROR Message");
        
        return ("<h1>Welcome! You can buy Spareparts here!!</h1>");
    }

    @GetMapping("/user")
    public String user() {
        return ("<h1>Welcome User</h1>");
    }

    @GetMapping("/user/getInventory")
    public List<Spareparts> getInventory() {
        return sprepo.findAll();
    }

    @GetMapping("/user/getPart/{partId}")
    public Spareparts getPart(@PathVariable int partId) throws Exception {
        return spserv.findPartById(partId);
    }

    @GetMapping("/user/getOrders")
    public List<Orders> getOrders() {
        return orepo.findAll();
    }
    
    @PostMapping("/user/bookParts")
    public Orders bookParts(@RequestBody List<CreateOrderRequest> req) throws Exception {
    	Orders orderBuilder=new Orders();
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (!(authentication instanceof AnonymousAuthenticationToken)) {
    	    String currentUserName = authentication.getName();
    	    AccountEntity account = userRepository.findByEmail(currentUserName);
        	orderBuilder.setAccount(account);
    	}
    	for(int i=0;i<req.size();i++) {
    		CreateOrderRequest p=req.get(i);
    	   	int need=p.getQuantity();
    	   	
    	   	Spareparts dbpart=spserv.findPartById(p.getPartId());
    	   	int available=dbpart.getAvailablePartCount();
    	   	
    	   	int c = available-(available>=need?need:available);
    	   	dbpart.setAvailablePartCount(c);
    	   	sprepo.save(dbpart);
    	   	
    	   	orderBuilder.addOrderRequest(p);
    	   	
    	};

    	Orders order=orepo.save(orderBuilder);
    	return order;
    }

//    @GetMapping("/user/getOrderedParts/{orderId}")
//    public List<Spareparts> getOrderedParts(@PathVariable int orderId) throws Exception {
//    	List<CreateOrderRequest> req=orepo.findById(orderId).get().getOrderRequest();
//    	List<Spareparts> resp=new ArrayList<>();
//    	for(int i=0;i<req.size();i++) {
//    		resp.add(req.get(i).getParts());
//    	}
//        return resp;
//    }
    
    @GetMapping("/user/getOrderRequest/{orderId}")
    public List<CreateOrderRequest> getOrderRequest(@PathVariable int orderId) throws Exception {
        return orepo.findById(orderId).get().getOrderRequest();
    }

    @GetMapping("/admin")
    public String admin() {
        return ("<h1>Welcome Admin</h1>");
    }

    @GetMapping("/admin/getAllParts")
    public List<Spareparts> getAllParts() {
        return sprepo.findAll();
    }
    
    @PostMapping("/admin/addPart")
    public Spareparts addPart(@RequestBody Spareparts part) {
        return sprepo.save(part);
    }
    
    @DeleteMapping("/admin/deletePart")
    public ResponseEntity<HttpStatus> deletePart(@RequestParam int partId) throws Exception {
    	 Spareparts part=spserv.findPartById(partId);
         sprepo.delete(part);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PutMapping("/admin/updatePart")
    public Spareparts updatePart(@RequestBody Spareparts part) throws Exception {
    	 int partId=part.getId();
    	 if (!sprepo.existsById(partId)) {
    	     throw new ResourceNotFoundException("Not found = " + partId);
    	 }
         return sprepo.save(part);
    }
    
    @PutMapping("/admin/updatePartStock/{partId}")
    public Spareparts updatePartStock(@RequestParam int stock, @PathVariable int partId) throws Exception {
   	 	 Spareparts part=spserv.findPartById(partId);
   	 	 part.setAvailablePartCount(stock);
   	 	 return sprepo.save(part);
    }
}

