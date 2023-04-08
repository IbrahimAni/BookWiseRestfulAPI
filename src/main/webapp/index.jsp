<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>	
	<!-- Header.jsp -->
	<jsp:include page="./html/components/headtag.jsp" />
</head>
<body>
	<jsp:include page="./html/components/alert.jsp" />
	<!-- Navbar.jsp -->
 	<jsp:include page="./html/components/navbar.jsp" />
 	<main class="container mx-auto mt-8">
 		<div class="grid grid-cols-2 gap-8">
 			<div id="insert-here1">
 				<jsp:include page="./html/components/add.jsp" />
 			</div>	
 			<div id="insert-here"></div>
 		</div>
 	</main>
 	 	
 	<!-- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- -->
	<script>
	//Function to show all books
		function sendRequest() {
		    var xhr = getRequestObject();
		    xhr.onreadystatechange = function() {
		        if (this.readyState === 4 && this.status === 200) {
		        	var books = JSON.parse(this.responseText);
		        	handleResponse(books);
		        }
		    };
		    xhr.open("GET", "allbooks", true);
		    xhr.send();
		}
		
	//Function to findbook
		function findBook(){
		  	// Prevent the form from submitting and refreshing the page
		 	event.preventDefault();
			  
			var searchParameter = document.getElementById("searchParameter").value;
			
			var xhr = getRequestObject();
		    xhr.onreadystatechange = function() {
		        if (this.readyState === 4 && this.status === 200) {
		        	var books = JSON.parse(this.responseText);
		        	handleResponse(books);
		        	
		        	document.getElementById("searchParameter").value = "";
		        }
		    };
		    
		 // Add the searchParameter to the request URL
		    var requestURL = "search?query="+encodeURIComponent(searchParameter);

		    xhr.open("GET", requestURL, true);
		    xhr.send();			
		}
		
	//function to submit book
		function submitBook(){
		  // Prevent the form from submitting and refreshing the page
		  event.preventDefault();
		  
		  // Get form data
		  var formData = getFormDeatils("book-form");
		  
		  var xhr = getRequestObject();
		    xhr.onreadystatechange = function() {
		        if (this.readyState === 4 && this.status === 200) {
			        	var books = JSON.parse(this.responseText);
			        	handleResponse(books);	
			        	showAlert("Success", "added");			        	
			        	clearForm("book-form");
			        }
			    };
		  xhr.open("POST", "add-book", true);
		  
		  // Create a FormData object from the form element
		  var formElement = document.getElementById("book-form");
		  var formToSend = new FormData(formElement);
		  
		// Set the content type for the request
		  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		  
		// Convert FormData to a URL encoded string
		  var urlEncodedData = new URLSearchParams(formToSend).toString();
		
		  xhr.send(urlEncodedData);
			
		}
		
		// Your viewBook function with the bookId parameter
		function viewBook(bookId) {
		  var xhr = getRequestObject();
		  xhr.onreadystatechange = function() {
		    if (this.readyState === 4 && this.status === 200) {
		    	var book = JSON.parse(this.responseText);
		    	createBookDetailsSection(book);
		    }
		  };

		  // Include the bookId in the URL as a query parameter
		  xhr.open("GET", "view?id="+bookId, true);
		  xhr.send();
		}
		
		function showAddForm(){
			var xhr = getRequestObject();
			xhr.onreadystatechange = function(){
			    if (this.readyState === 4 && this.status === 200) {
			    	var tagId = document.getElementById("insert-here1");
					tagId.innerHTML = "";
					tagId.innerHTML = initialInnerHTML;
			    }
			}
			xhr.open("GET", "add", true);
			xhr.send();
			
		}
		
		function deleteBook(bookId){
			var xhr = getRequestObject();
			xhr.onreadystatechange = function(){
				var books = JSON.parse(this.responseText);
	        	handleResponse(books);
		    	showAddForm();
		    	showAlert("Success", "deleted");
			}
			xhr.open("GET", "delete?id="+bookId, true);
			xhr.send();
		}
		
		//<!- All Supporting functions -->
		
		//storing the initial value of insert-here1
		const insertHere1 = document.getElementById("insert-here1");
		const initialInnerHTML = insertHere1.innerHTML;
		
		//function to create the view section
		function createBookDetailsSection(book) {
		  var bookDetailsSection = document.createElement("div");
		  bookDetailsSection.className = "book-details-section";
		
		  var bgWhite = document.createElement("div");
		  bgWhite.className = "bg-white shadow rounded p-4";
		  bookDetailsSection.appendChild(bgWhite);
		
		  var flexContainer = document.createElement("div");
		  flexContainer.className = "flex justify-between items-center";
		  bgWhite.appendChild(flexContainer);
		
		  var title = document.createElement("h2");
		  title.className = "text-xl font-semibold mb-4";
		  title.textContent = "Book Details";
		  flexContainer.appendChild(title);
		
		  var actionLinks = document.createElement("div");
		  flexContainer.appendChild(actionLinks);
		
		  var updateLink = document.createElement("button");
		  updateLink.onclick = () => {
			  updateBook(book.id.toString())
		  };
		  updateLink.className = "bg-blue-500 text-white py-1 px-3 rounded";
		  updateLink.textContent = "Update";
		  actionLinks.appendChild(updateLink);
		
		  var deleteLink = document.createElement("button");
		  deleteLink.onclick = () => {
			  console.log(book.id.toString())
			  deleteBook(book.id.toString())
		  }
		  deleteLink.className = "bg-red-500 text-white py-1 px-3 rounded";
		  deleteLink.textContent = "Delete";
		  actionLinks.appendChild(deleteLink);
		
		  var flexWrap = document.createElement("div");
		  flexWrap.className = "flex flex-wrap";
		  bgWhite.appendChild(flexWrap);
		
		  var img = document.createElement("img");
		  img.src = "https://i.ibb.co/tH2rkC8/two-new-books-wrappers.jpg";
		  img.alt = "Book cover";
		  img.className = "w-48 h-72 md:w-64 md:h-96 lg:w-72 lg:h-108 xl:w-80 xl:h-120 mr-4 mb-4";
		  flexWrap.appendChild(img);
		
		  var bookInfo = document.createElement("div");
		  flexWrap.appendChild(bookInfo);
		
		  var bookInfoItems = [
		    { label: "Title", value: book.title },
		    { label: "Author", value: book.author },
		    { label: "Date", value: book.date },
		    { label: "Genres", value: book.genres },
		    { label: "Characters", value: book.characters },
		    { label: "Synopsis", value: book.synopsis },
		  ];
		
		  for (var i = 0; i < bookInfoItems.length; i++) {
		    var item = bookInfoItems[i];
		    var p = document.createElement("p");
		    p.innerHTML = "<span class='font-bold'>" + item.label + ": </span>" + item.value;
		    bookInfo.appendChild(p);
		  }
		
		  // Append the main container to the div with id "insert-here"
		  const targetDiv = document.getElementById("insert-here1");
		  targetDiv.innerHTML = "";
		  targetDiv.appendChild(bookDetailsSection);

		  
		  return bookDetailsSection;
		}

		
		//function to clearForm
		function clearForm(formID) {
		  var formElement = document.getElementById(formID);
		  formElement.reset();
		}
		
		//function to get form data		
		function getFormDeatils(formID){
			var formElements = document.getElementById(formID).elements;

			  var formData = {
			    id: formElements.id.value,
			    title: formElements.title.value,
			    author: formElements.author.value,
			    date: formElements.date.value,
			    genres: formElements.genres.value,
			    characters: formElements.characters.value,
			    synopsis: formElements.synopsis.value
			  };
			  
			  return formData;
		}
		
		//function to get requestObject
		function getRequestObject(){
        if(window.XMLHttpRequest){
          return(new XMLHttpRequest());
        }else if(window.ActiveXObject){
          return(new ActiveXObject("Microsoft.XMLHTTP"));
        }else{
          return(null);
        }
      }
		//function to handleResponse
		function handleResponse(books){
						
            // Create HTML elements for each book
            createBooksTable(books);
		}
		
		//function for the display book page
		function createBooksTable(books) {
		  // Create main container
		  const container = document.createElement("div");
		  container.className = "data-display-section";
		
		  // Create inner container
		  const innerContainer = document.createElement("div");
		  innerContainer.className = "bg-white shadow rounded p-4";
		  container.appendChild(innerContainer);
		
		  // Create title
		  const title = document.createElement("h2");
		  title.className = "text-xl font-semibold mb-4";
		  title.textContent = "Books List";
		  innerContainer.appendChild(title);
		
		  // Create table container
		  const tableContainer = document.createElement("div");
		  tableContainer.className = "overflow-x-auto";
		  innerContainer.appendChild(tableContainer);
		
		  // Create table
		  const table = document.createElement("table");
		  table.className = "table-auto min-w-max";
		  tableContainer.appendChild(table);
		
		  // Create table head
		  const thead = document.createElement("thead");
		  table.appendChild(thead);
		
		  const headers = ["ID", "Title", "Author", "Date", "Genres", "Characters", "Synopsis", "Actions"];
		  const headerRow = document.createElement("tr");
		  headerRow.className = "text-left font-semibold";
		  thead.appendChild(headerRow);
		
		  headers.forEach((header) => {
		    const th = document.createElement("th");
		    th.className = "border-b p-2";
		    th.textContent = header;
		    headerRow.appendChild(th);
		  });
		
		  // Create table body
		  const tbody = document.createElement("tbody");
		  table.appendChild(tbody);
		
		  // Iterate over books and create table rows
		  books.forEach((book) => {
		    const row = document.createElement("tr");
		
		    const properties = ["id", "title", "author", "date", "genres", "characters", "synopsis"];
		    properties.forEach((property) => {
		      const td = document.createElement("td");
		      td.className = "border-b p-2 truncate";
		      td.textContent = book[property];
		      row.appendChild(td);
		    });
		
		    // Create actions cell
		    const actionsCell = document.createElement("td");
		    actionsCell.className = "border-b p-2";
		    row.appendChild(actionsCell);
		
		 	// Create view button
		    const viewBtn = document.createElement("button");
		    viewBtn.type = "button";
		    viewBtn.className = "bg-blue-500 text-white py-1 px-3 rounded";
		    viewBtn.textContent = "View";
		    viewBtn.onclick = () => {
		    	viewBook(book.id.toString())
		    };
		    actionsCell.appendChild(viewBtn);

		
		    // Append the row to the table body
		    tbody.appendChild(row);
		  });
		
			//Append the main container to the div with id "insert-here"
			  const targetDiv = document.getElementById("insert-here");
			  targetDiv.innerHTML = "";
			  targetDiv.appendChild(container);
			}
	
	
		//function to show alert and create message div
		function showAlert(message, action) {
			const alertDiv = document.getElementById('alert');
			const messageDiv = createMessageDiv(message, action);
			alertDiv.appendChild(messageDiv);
			
			 alertDiv.style.opacity = '1';
			  alertDiv.style.transform = 'translateY(100%)';

			  setTimeout(() => {
			    alertDiv.style.opacity = '0';
			    alertDiv.style.transform = 'translateY(0)';
			    messageDiv.remove();
			  }, 3000);
		}
		
		function createMessageDiv(message, action) {
			const messageDiv = document.createElement('div');
			messageDiv.textContent = message + ', book has been ' + action;
			return messageDiv;
		}




		</script>
	</body>
</html>