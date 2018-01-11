/**
 * 
 */
var currentPage;
window.onload = function(){
	homePageDashboard();
	loadNavbar();
}

function loadNavbar(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status ==200){
			document.getElementById('appNav').innerHTML = xhr.responseText;
			document.getElementById('myAccount').addEventListener('click', myAccountPage, false);
			document.getElementById('home').addEventListener('click', homePageDashboard, false);
			if(document.getElementById('requestForm'))
				document.getElementById('requestForm').addEventListener('click', requestFormPage, false);
			if(document.getElementById('myRequests'))
				document.getElementById('myRequests').addEventListener('click', myRequestsPage, false);
			if(document.getElementById('viewPending'))
				document.getElementById('viewPending').addEventListener('click', viewPendingPage, false);
			if(document.getElementById('viewResolved'))
				document.getElementById('viewResolved').addEventListener('click', viewResolvedPage, false);
			if(document.getElementById('viewEmployees'))
				document.getElementById('viewEmployees').addEventListener('click', viewEmployeesPage, false);
			
			currentPage = document.getElementById("home");
		}
	}
	xhr.open("GET", "navBar?r=" + new Date().getTime(), true);
	xhr.send();
}

function homePageDashboard(){

	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status ==200){
			document.getElementById('main').innerHTML = xhr.responseText;
			currentPage = navActiveStatus(currentPage,document.getElementById('home'));
			homeUserInfo();
		}
	}
	xhr.open("GET", "homePage?r=" + new Date().getTime(), true);
	xhr.send();
	
}

function homeUserInfo(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status ==200){
			var user = JSON.parse(xhr.responseText);	//returns a JSON from the servlet
			document.getElementById('name').innerHTML = user.firstName;
			document.getElementById('last').innerHTML = user.lastName;
		}
	}
	xhr.open("GET", "ajaxUserInfo", true);
	xhr.send();
}


function myAccountPage(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status ==200){
			document.getElementById('main').innerHTML = xhr.responseText;
			currentPage = navActiveStatus(currentPage,document.getElementById('myAccount'));	// nav selection
			myAccountInfo();
		}
	}
	xhr.open("GET", "myAccountPage", true);
	xhr.send();
}

function myAccountInfo(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status ==200){
			var user = JSON.parse(xhr.responseText);	//returns a JSON from the servlet
			document.getElementById('fname').innerHTML = user.firstName;// id's must not equal an old one
			document.getElementById('lname').innerHTML = user.lastName;
			document.getElementById('email').innerHTML = '<a href="mailto:'+user.email+'">'+user.email+'</a>';
			document.getElementById('uname').innerHTML = user.username;
			document.getElementById('updateBtn').addEventListener('click', updateInfoPage, false);
		}
	}
	xhr.open("GET", "myAccountInfo", true);
	xhr.send();
}

function updateInfoPage()
{
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status ==200){
			document.getElementById('main').innerHTML = xhr.responseText;
			currentPage = navActiveStatus(currentPage,document.getElementById('myAccount'));
			updateInfo();
		}
	}
	xhr.open("GET", "updateInfoPage", true);
	xhr.send();
}
function updateInfo()
{
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status ==200){
			var user = JSON.parse(xhr.responseText);	//returns a JSON from the servlet
			document.getElementById('u_usrid').value = user.user_ID;
			document.getElementById('ufname').value = user.firstName;// id's must not equal an old one
			document.getElementById('ulname').value = user.lastName;
			document.getElementById('uemail').value = user.email;
			document.getElementById('uname').value = user.username;
			document.getElementById('cancelUpdate').addEventListener('click', homePageDashboard, false);
			document.getElementById('submitUpdate').addEventListener('click', verifyUpdateInput, false);
		}
	}
	xhr.open("GET", "myAccountInfo", true);//can be reused since all we need is account info
	xhr.send();
}

function processUpdateInfo(updateInfo){
	
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status ==200){
			
			document.getElementById('myAccountUpdate').innerHTML = xhr.responseText;
			document.getElementById('refreshAccount').addEventListener('click',myAccountPage,false);
		}
		if(xhr.status == 400)
		{
			document.getElementById('myAccountUpdate').innerHTML = "<h1>400 Bad Request</h1>";
		}
		
	}
	xhr.open("POST", "processUpdateInfo", true);
	xhr.setRequestHeader("key",updateInfo);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");//Must set the content-type
	xhr.send(updateInfo);
}

function verifyUpdateInput(){
	var u_usrid = document.getElementById('u_usrid').value;
	var uname = document.getElementById('uname').value;
	var ufname = document.getElementById('ufname').value;
	var ulname = document.getElementById('ulname').value;
	var uemail = document.getElementById('uemail').value;
	
	var updateInfo = {
			userId: u_usrid,
			username: uname,
			firstName: ufname,
			lastName: ulname,
			email: uemail
	}
	updateInfo = JSON.stringify(updateInfo);
	
	document.getElementById('submitUpdate').setAttribute("disabled","disabled");
	
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status ==200){
			document.getElementById('updateMsg').innerHTML = xhr.responseText;
			eval(document.getElementById('script').innerHTML);
		}
		
		document.getElementById('submitUpdate').removeAttribute("disabled");
	}
	xhr.open("POST", "verifyUpdateInput", true);
	xhr.setRequestHeader("key",updateInfo);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");//Must set the content-type
	xhr.send(updateInfo);
}

function requestFormPage(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status ==200){
			document.getElementById('main').innerHTML = xhr.responseText;
			document.getElementById('submitReq').addEventListener('click', verifyRequest, false);
			document.getElementById('cancelReq').addEventListener('click', homePageDashboard, false);
			currentPage = navActiveStatus(currentPage,document.getElementById('requestForm'));
			
			loadRequestFormTypes();
		}
	}
	xhr.open("GET", "requestFormPage?r=" + new Date().getTime(), true);
	xhr.send();
}

function loadRequestFormTypes(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status ==200){
			var types = JSON.parse(xhr.responseText);
			
			let legend = document.createElement('legend');
			legend.className = "col-form-label col-sm-4 pt-0";
			legend.innerHTML = "Type of reimbursement";

			let divMaster = document.createElement('div');
			divMaster.className = "col-sm-8";

			for(let i = 0; i< types.size; i++)
			{
				let formDiv = document.createElement('div');
				let input = document.createElement('input');
				let lable = document.createElement('lable');

				formDiv.className = "form-check";
				input.className = "form-check-input";
				input.type = "radio";
				input.name = "type";
				input.id = "radio"+(i+1);
				input.value = types.ersTypes[i][0];
				lable.className = "form-check-label";
				lable.innerHTML = types.ersTypes[i][1];

				formDiv.appendChild(input);
				formDiv.appendChild(lable);
				divMaster.appendChild(formDiv);	
			}
			document.getElementById("ERSTypes").appendChild(legend);
			document.getElementById("ERSTypes").appendChild(divMaster);
		}
	}
	xhr.open("GET", "requestFormTypes?r=" + new Date().getTime(), true);
	xhr.send();
}

function verifyRequest(){
	var amount = document.getElementById('amount').value;
	var description = document.getElementById('description').value;
	var type;
	
	if(document.getElementById('radio1').checked)
		type = document.getElementById('radio1').value;
	else if(document.getElementById('radio2').checked)
		type = document.getElementById('radio2').value;
	else if(document.getElementById('radio3').checked)
		type = document.getElementById('radio3').value;
	
	var requestInfo = {
			amount: amount,
			description: description,
			rt_type: type
	}
	requestInfo = JSON.stringify(requestInfo);
	
	document.getElementById('submitReq').setAttribute("disabled","disabled");
	
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status ==200){
			document.getElementById('updateMsg').innerHTML = xhr.responseText;
			eval(document.getElementById('script').innerHTML);
		}
		
		document.getElementById('submitReq').removeAttribute("disabled");
	}
	xhr.open("POST", "verifyRequestInput", true);
	xhr.setRequestHeader("key",requestInfo);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");//Must set the content-type
	xhr.send(requestInfo);
}

function processRequestInfo(){
	
	//blob = new Blob([document.querySelector('input[type=file]').files[0]], { type: "image/jpeg" })
	
	//var file = document.querySelector('input[type=file]').files[0];
	
	//var reader = new FileReader();
	   
	   //reader.readAsDataURL(file);
	   //reader.onload = function () {
		  //blob = reader.result;
	   //};
	   //reader.onerror = function (error) {
	     //console.log('Error: ', error);
	   //};
	
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status ==200){			
			document.getElementById('requestFormPage').innerHTML = xhr.responseText;
		}
		if(xhr.status == 400)
		{
			document.getElementById('requestFormPage').innerHTML = "<h1>400 Bad Request</h1>";
		}
		
	}
	xhr.open("POST", "processRequestInfo", true);
	xhr.setRequestHeader("Content-type", "text/plain");//Must set the content-type
	xhr.send("blob=blah");
}

function navActiveStatus(obj1,obj2)
{
	if(obj1!=obj2)
	{
		obj2.className += " active";
		obj1.classList.remove("active");
	}
	return obj2;
}

function myRequestsPage(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status ==200){
			document.getElementById('main').innerHTML = xhr.responseText;
			currentPage = navActiveStatus(currentPage,document.getElementById('myRequests'));
			
			getMyRequests();
		}
	}
	xhr.open("GET", "myRequestsPage?r=" + new Date().getTime(), true);
	xhr.send();
}

function getMyRequests(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status ==200){
			var myRequests = JSON.parse(xhr.responseText);	//returns a JSON from the servlet
			
			var pending = document.getElementById('viewMyRequestsPending');
			var resolved = document.getElementById('viewMyRequestsResolved');
			
			for(var i = 0;i<myRequests.requests.length;i++)
			{
				var tr = document.createElement('tr');
				var tr2 = document.createElement('tr');
				var arr = ["id","amount","description","receipt","submitted","resolved","rtType","rt_status"];
				for(var j = 0;j<8;j++)
				{
					if(myRequests.requests[i]["rt_status"]=="Pending"){
						if(j==5){
							continue;
						}
						var id = document.createElement('td');
						
						if(arr[j]=="id"||arr[j]=="description" || arr[j]=="rtType" || arr[j]=="rt_status")
						{
							id.innerHTML = myRequests.requests[i][arr[j]];
						}
						else if(arr[j]=="amount"){
							id.innerHTML = "$"+myRequests.requests[i][arr[j]].toFixed(2);
						}
						else if(arr[j]=="receipt")
						{
							//show image!!!!!!!!!!
						}						
						else if(arr[j]=="submitted")
						{
							var options = {weekday: "short", year: "numeric", month: "short", day: "numeric", hour: "2-digit", minute: "2-digit"}; 
							var date = new Date(myRequests.requests[i][arr[j]]);
							id.innerHTML = date.toLocaleTimeString("en-us", options);
						}					
						tr.appendChild(id);
					}
					else if(myRequests.requests[i]["rt_status"]!="Pending")
					{
						var id = document.createElement('td');
						if(arr[j]!="submitted" && arr[j]!="resolved" && arr[j]!="amount")
						{
							id.innerHTML = myRequests.requests[i][arr[j]];
						}
						else if(arr[j]=="amount"){
							id.innerHTML = "$"+myRequests.requests[i][arr[j]].toFixed(2);
						}
						else
						{
							var options = {weekday: "short", year: "numeric", month: "short", day: "numeric", hour: "2-digit", minute: "2-digit"}; 
							var date = new Date(myRequests.requests[i][arr[j]]);
							id.innerHTML = date.toLocaleTimeString("en-us", options);
						}					
						tr2.appendChild(id);
					}
				}				
				pending.appendChild(tr);
				resolved.appendChild(tr2);
			}
			
		}
	}
	xhr.open("GET", "myRequests", true);
	xhr.send();
}
function viewPendingPage(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status ==200){
			document.getElementById('main').innerHTML = xhr.responseText;
			currentPage = navActiveStatus(currentPage,document.getElementById('viewPending'));
			
			viewPendingRequests();
		}
	}
	xhr.open("GET", "viewPendingPage?r=" + new Date().getTime(), true);
	xhr.send();
}
function viewPendingRequests(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status ==200){
			var pendingRequests = JSON.parse(xhr.responseText);	//returns a JSON from the servlet
			
			var pending = document.getElementById('viewPendingReimbursements');
			
			for(var i = 0;i<pendingRequests.requests.length;i++)
			{
				var tr = document.createElement('tr');
				tr.className += "requestRow";
				var arr = ["id","id_author","amount","description","receipt","submitted","rtType","rt_status"];
				for(var j = 0;j<7;j++)
				{
					var id = document.createElement('td');
					
					if(arr[j]=="id"){
						tr.id=pendingRequests.requests[i][arr[j]];
					}
					
					if(arr[j]!="submitted"&&arr[j]!="amount")
					{
						id.innerHTML = pendingRequests.requests[i][arr[j]];
					}
					else if(arr[j]=="amount"){
						id.innerHTML = "$"+pendingRequests.requests[i][arr[j]].toFixed(2);
					}
					else
					{
						var options = {weekday: "short", year: "numeric", month: "short", day: "numeric", hour: "2-digit", minute: "2-digit"}; 
						var date = new Date(pendingRequests.requests[i][arr[j]]);
						id.innerHTML = date.toLocaleTimeString("en-us", options);
					}					
					tr.appendChild(id);
				}				
				pending.appendChild(tr);
			}
			var pending = document.getElementsByClassName("requestRow");
			
			for (var i = 0; i < pending.length; i++) {
				pending[i].addEventListener('click',function(){selectThisrequest(this.id);},false);
			}
		}
	}
	xhr.open("GET", "pendingRequests?r=" + new Date().getTime(), true);
	xhr.send();
}

function viewResolvedPage(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status ==200){
			document.getElementById('main').innerHTML = xhr.responseText;
			currentPage = navActiveStatus(currentPage,document.getElementById('viewResolved'));
			
			viewResolvedRequests();
		}
	}
	xhr.open("GET", "viewResolvedPage?r=" + new Date().getTime(), true);
	xhr.send();
}
function viewResolvedRequests(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status ==200){
			var resolvedRequests = JSON.parse(xhr.responseText);	//returns a JSON from the servlet
			
			var resolved = document.getElementById('viewResolvedReimbursements');
			
			for(var i = 0;i<resolvedRequests.requests.length;i++)
			{
				var tr = document.createElement('tr');
				var arr = ["id","id_author","amount","description","receipt","submitted","resolved","u_id_resolver","rtType","rt_status"];
				for(var j = 0;j<9;j++)
				{					
					var id = document.createElement('td');
					if(arr[j]!="submitted" && arr[j]!="resolved"&&arr[j]!="amount")
					{
						id.innerHTML = resolvedRequests.requests[i][arr[j]];
					}
					else if(arr[j]=="amount"){
						id.innerHTML = "$"+resolvedRequests.requests[i][arr[j]].toFixed(2);
					}
					else
					{
						var options = {weekday: "short", year: "numeric", month: "short", day: "numeric", hour: "2-digit", minute: "2-digit"}; 
						var date = new Date(resolvedRequests.requests[i][arr[j]]);
						id.innerHTML = date.toLocaleTimeString("en-us", options);
					}					
					tr.appendChild(id);					
				}				
				resolved.appendChild(tr);
			}			
		}
	}
	xhr.open("GET", "resolvedRequests?r=" + new Date().getTime(), true);
	xhr.send();
}

function viewEmployeesPage(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status ==200){
			document.getElementById('main').innerHTML = xhr.responseText;
			currentPage = navActiveStatus(currentPage,document.getElementById('viewEmployees'));
			
			getEmployees();
		}
	}
	xhr.open("GET", "viewEmployeesPage?r=" + new Date().getTime(), true);
	xhr.send();
}

function getEmployees(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status ==200){
			var employeeList = JSON.parse(xhr.responseText);	//returns a JSON from the servlet
			
			var employees = document.getElementById('viewAllEmployees');
			
			for(var i = 0;i<employeeList.employees.length;i++)
			{
				var tr = document.createElement('tr');
				var arr = ["id","firstName","lastName","email"];
				
				tr.className += "employeeRow";
				
				for(var j = 0;j<4;j++)
				{					
					var id = document.createElement('td');
					if(arr[j]=="id"){
						tr.id=employeeList.employees[i][arr[j]];
					}
					id.innerHTML = employeeList.employees[i][arr[j]];		
					tr.appendChild(id);				
				}				
				employees.appendChild(tr);
			}
			var employees = document.getElementsByClassName("employeeRow");
			
			for (var i = 0; i < employees.length; i++) {
				employees[i].addEventListener('click',function(){selectThisEmployee(this.id);},false);
			}
		}
	}
	xhr.open("GET", "viewAllEmployees?r=" + new Date().getTime(), true);
	xhr.send();
}

function selectThisEmployee(id){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status ==200){
			document.getElementById('main').innerHTML = xhr.responseText;
			currentPage = navActiveStatus(currentPage,document.getElementById('viewEmployees'));
			
			getEmployeeRequests();
		}
	}
	xhr.open("POST", "viewEmployeeRequestsPage", true);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send("employeeId="+id);
}

function getEmployeeRequests(){

	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status ==200){
			var pendingRequests = JSON.parse(xhr.responseText);	//returns a JSON from the servlet
			
			if(pendingRequests.requests.length==0)
			{
				document.getElementById('viewMyRequests').innerHTML = "<h1>Employee has no pending requests</h1>";
				return;
			}
			document.getElementById("employeeName").innerHTML = pendingRequests.requests[0]["id_author"];
			var pending = document.getElementById('viewEmployeePendingRequests');
			
			for(var i = 0;i<pendingRequests.requests.length;i++)
			{
				var tr = document.createElement('tr');
				var arr = ["id","amount","description","receipt","submitted","rtType","rt_status"];
				tr.className += "requestRow";
				for(var j = 0;j<7;j++)
				{
					if(pendingRequests.requests[i]["rt_status"]=="Pending"){
						
						if(arr[j]=="id"){
							tr.id=pendingRequests.requests[i][arr[j]];
						}
						
						var id = document.createElement('td');
						if(arr[j]!="submitted"&& arr[j]!="amount")
						{
							id.innerHTML = pendingRequests.requests[i][arr[j]];
						}
						else if(arr[j]=="amount"){
							id.innerHTML = "$"+pendingRequests.requests[i][arr[j]].toFixed(2);
						}
						else
						{
							var options = {weekday: "short", year: "numeric", month: "short", day: "numeric", hour: "2-digit", minute: "2-digit"}; 
							var date = new Date(pendingRequests.requests[i][arr[j]]);
							id.innerHTML = date.toLocaleTimeString("en-us", options);
						}					
						tr.appendChild(id);
					}
				}				
				pending.appendChild(tr);
			}
			var employees = document.getElementsByClassName("requestRow");
			
			for (var i = 0; i < employees.length; i++) {
				employees[i].addEventListener('click',function(){selectThisrequest(this.id);},false);
			}			
		}
	}
	xhr.open("GET", "viewEmployeeRequests?r=" + new Date().getTime(), true);
	xhr.send();
}

function selectThisrequest(id){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status ==200){
			document.getElementById('main').innerHTML = xhr.responseText;
			currentPage = navActiveStatus(currentPage,document.getElementById('viewEmployees'));
			
			getEmployeeRequest();
		}
	}
	xhr.open("POST", "respondRequest", true);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send("requestId="+id);
}

function getEmployeeRequest(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status ==200){
			var employee = JSON.parse(xhr.responseText);	//returns a JSON from the servlet
			document.getElementById('amount').innerHTML = employee.requests[0]["amount"];
			document.getElementById('description').innerHTML = employee.requests[0]["description"];
			document.getElementById('type').innerHTML = employee.requests[0]["rtType"];
			
			var options = {weekday: "short", year: "numeric", month: "short", day: "numeric", hour: "2-digit", minute: "2-digit"}; 
			var date = new Date(employee.requests[0]["submitted"]);			
			document.getElementById('submitted').innerHTML = date.toLocaleTimeString("en-us", options);
			
			document.getElementById('employeeName').innerHTML = employee.requests[0]["id_author"];

			document.getElementById('cancelResponse').addEventListener('click', viewEmployeesPage, false);
			//document.getElementById('backResponse').addEventListener('click', selectThisEmployee(id), false);
		}
	}
	xhr.open("GET", "viewEmployeeRequest", true);
	xhr.send();
}