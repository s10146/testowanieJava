$(document).ready(function() {
	$(function() {
		// link pod ktorym mozna zobaczyc dzialanie: http://localhost:8080/restservicedemo/
		var tablePersons = $('.tablePersons');
		var tableCars = $('.tableCars');

		function getPersons() {

			$
					.ajax({
						method : "GET",
						url : "http://localhost:8080/restservicedemo/api/person/all",
						success : function(data) {
							console.log(data);
							tablePersons
									.append('<table border = "1"><tr><th>ID</th><th>Name</th><th>Yob</th></tr></table>');
							var html = "";
							if (typeof (data.person.length) !== 'number') {
								html += '<tr>' + '<td>' + data.person.id
										+ '</td>' + '<td>'
										+ data.person.firstName + '</td>'
										+ '<td>' + data.person.yob + '</td>'
										+ '</tr>';
							}
							for (var i = 0; i < data.person.length; i++) {
								html += '<tr>' + '<td>' + data.person[i].id
										+ '</td>' + '<td>'
										+ data.person[i].firstName + '</td>'
										+ '<td>' + data.person[i].yob + '</td>'
										+ '</tr>';
							}
							$(html).insertAfter(tablePersons.find('tr'));
						}
					});

		}

		function getCars() {
			$
					.ajax({
						method : "GET",
						url : "http://localhost:8080/restservicedemo/api/car/allCars",
						success : function(data) {
							console.log(data);
							tableCars
									.append('<table border = "1"><tr><th>ID</th><th>Model</th><th>YOP</th><th>Owner</th></tr></table>');
							var html = "";
							if(data !== null) {
								if (typeof (data.car.length) !== 'number') {
									html += '<tr>' + '<td>' + data.car.id + '</td>'
											+ '<td>' + data.car.model + '</td>'
											+ '<td>' + data.car.yop + '</td>'
											+ '<td>' + data.car.owner.id + '</td>'
											+ '</tr>';
								}
							

							for (var i = 0; i < data.car.length; i++) {
								html += '<tr>' + '<td>' + data.car[i].id
										+ '</td>' + '<td>' + data.car[i].model
										+ '</td>' + '<td>' + data.car[i].yop
										+ '</td>' + '<td>'
										+ data.car[i].owner.id + '</td>'
										+ '</tr>';
							}
							}
							$(html).insertAfter(tableCars.find('tr'));
						}
					});

		}

		getPersons();
		getCars();
		
		$("#btnSave").click(function() {
		    $.ajax({
		    	method: 'POST',
		        url: 'http://localhost:8080/restservicedemo/api/person/',
		        contentType: 'application/json',
		        dataType: 'json',
		        data: JSON.stringify({
		        	id: $("#idPerson").val(), 
		        	firstName: $("#name").val(), 
		        	yob: $("#yob").val()
		        }),
		        success: function (data) {
		        	console.log("Imię" + data)
		        }
		    });
		});


	});
});
	