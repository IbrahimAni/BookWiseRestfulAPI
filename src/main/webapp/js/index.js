alert("kdsahbfhbda");

var id = document.getElementById("insert-here");

function sendRequest(){
	var xhr = new XMLHttpRequest();
	console.log(xhr);
	xhr.onreadystatechange = function(){
		if(this.readyState === 4 && this.status === 200){
			var books = JSON.parse(this.responseText);
			console.log()
			var bookList = "";
			for(var i = 0; i < books.length; i++){
				bookList += "<div>" + books[i].title + " by " + books[i].author + "</div>";
			}
			id.innerHTML = bookList;
		}
	}
	xhr.open("GET", "allbooks", true);
	xhr.send();
	//id.innerHTML = "Message to be displayed";
}

function sendRequest1(){
	id.innerHTML = "Message to be displayed1";
}

function callFunction(){
	alert("Hello World");
}