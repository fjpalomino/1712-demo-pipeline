package com.revature.test;

import com.revature.dto.EmployeesDTO;
import com.revature.dto.RequestDTO;
import com.revature.dto.RequestsDTO;
import com.revature.model.ERSStatus;
import com.revature.model.ERSTypes;
import com.revature.model.Request;
import com.revature.model.Requests;
import com.revature.service.ERSService;

public class TestDriver {

	public static void main(String[] args) {

		ERSService service = new ERSService();
		
		ERSTypes ersTypes = service.getTypes();
		
		Request eR = service.getRequest(1040);
		
		System.out.println(eR.getSubmitted());
		
		for(int i = 1;i<=ersTypes.size;i++)
			System.out.println(ersTypes.getTypeById(i));
		
		for(int i = 1;i<=ersTypes.size;i++)
			System.out.println(ersTypes.getTypeById(i));
		
		ERSStatus ersStatus = service.getStatus();
		for(int i = 1;i<=ersStatus.size;i++)
			System.out.println(ersStatus.getTypeById(i));
		
		Requests r = service.getPendingRequestsByStatus(1);
		RequestsDTO rDto = service.convertToRequestsDTO(r);
		
		for (RequestDTO i : rDto.requests)
		{
			System.out.println(i.getId()+" "+i.getId_author()+ " manager "+i.getU_id_resolver());
		}
		
		System.out.println(r.requests.size());
		
		System.out.println(r.requests.get(1).getAmount());
		
		EmployeesDTO allEmployees = service.convertToEmployeesDTO();
		
		System.out.println(allEmployees.employees.size());
		
//		List<String> al = new ArrayList<>();
//		al.add("Viru");  
//		al.add("Saurav");  
//		al.add("Mukesh");  
//		al.add("Tahir");
//		System.out.println(al);
//		Collections.sort(al);
//		System.out.println(al);
//		
//		StringBuilder sb = new StringBuilder("Hiloedsdf");
//		String s = "Hello";
//		
//		System.out.println(s);
//		s = new StringBuilder(s).reverse().toString();
//		
//		//sb.append("Hello");
//		
//		System.out.println(sb.capacity());
//		
//		System.out.println(sb.reverse());
//		System.out.println(s);
//		
//		System.out.println(fib(5));
//		
//		System.out.println(fac(9));
//		
//		System.out.println(reverse("Hello There"));
//		
	}
	
	static String reverse(String s){
		String res ="";
		
			for(int i = s.length()-1; i>0; i--)
				res += s.charAt(i);
			
		return res;
	}
	
	 static int fibonacci(int n) {
        if (n <= 1) return n;
        else return fibonacci(n-1) + fibonacci(n-2);
    }
	
	 static int fib(int n)
	{
		int f[] = new int[n+1];
		int i;
		
		f[0] = 0;
		f[1] = 1;
		
		for (i = 2; i <= n; i++)
		{
			f[i] = f[i-1] + f[i-2];
		}
	
		return f[n];
	}
	 
	 static int fac(int n)
	 {
		 int res = 1;
		 
		 for(int i = n; i>1;i--)
		 {
			 res *=i;
		 }
		 
		 return res;
	 }
}
