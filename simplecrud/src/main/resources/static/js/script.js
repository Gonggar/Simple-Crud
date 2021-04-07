$(document).ready(function(){
    var currentid = '';
    $("#update").hide();

    assignDataToTable();

    $('table').on('click', 'button[id="delete"]', function(e){
       var id = $(this).closest('tr').children('td:first').text();
       
       $.ajax({
            type:"DELETE",
            url:"http://localhost:8080/api/contacts/" + id,
            success: function(data){
                alertUsing("delete.", true);
                assignDataToTable();
            },
            error: function(err) {  
                console.log(err);
                alert(err.responseText);
            }
        });

    })

    $('table').on('click', 'button[id="edit"]', function(e){
       var id = $(this).closest('tr').children('td:first').text();
       currentid = id;
       $.ajax({    
          type:"GET",
          contentType: "application/json",
          url:"http://localhost:8080/api/contacts/"+id,
          success: function(data) {
            var contact = JSON.parse(JSON.stringify(data));
            $("#name").val(contact.name);
	        $("#phone").val(contact.phone);
	        $("#email").val(contact.email);
            $("#address1").val(contact.address1);
	        $("#address2").val(contact.address2);
	        $("#address3").val(contact.address3);
            $("#postcode").val(contact.postalCode);
	        $("#notes").val(contact.note);

	        $("#update").show();
	        $("#save").hide();
            
           },
          error: function(data) { 
            console.log(data);
            }
        });
     })

    $("#update").click(function() {
            var jsonVar = {
                name: $("#name").val(),
                phone:$("#phone").val(),
                email:$("#email").val(),
                address1:$("#address1").val(),
                address2:$("#address2").val(),
                address3:$("#address3").val(),
                postalCode:$("#postcode").val(),
                note:$("#notes").val()
            };

            $.ajax({
                type:"PUT",
                data: JSON.stringify(jsonVar),
                contentType: "application/json",
                url:"http://localhost:8080/api/contacts/" + currentid,
                success: function(data){
                    alertUsing("Update.", true);
                    $("#update").hide();
                    $("#save").show();
                    
                    $("#name").val("");
	                $("#phone").val("");
	                $("#email").val("");
	                $("#address1").val("");
	                $("#address2").val("");
	                $("#address3").val("");
	                $("#postcode").val("");
	                $("#notes").val("");
                    
                    assignDataToTable();
                },
                error: function(err) {  
                    console.log(err);
                    alert(err.responseText);
                }
           });

     });

 
    $("#save").click(function() {
        var jsonVar = {
                name: $("#name").val(),
                phone:$("#phone").val(),
                email:$("#email").val(),
                address1:$("#address1").val(),
                address2:$("#address2").val(),
                address3:$("#address3").val(),
                postalCode:$("#postcode").val(),
                note:$("#notes").val()
         };

        $.ajax({
            type:"POST",
            url:"http://localhost:8080/api/contacts/add",
            data: JSON.stringify(jsonVar),
            contentType: "application/json",
            success: function(data){
                assignDataToTable();
            },
            error: function(err) {
                console.log(err);
                alert(err.responseText);
            }
        });

    });

    function assignDataToTable() {
        $("tbody").empty();
        $.ajax({    
          type:"GET",
          contentType: "application/json",
          url:"http://localhost:8080/api/contacts",
          success: function(data) {
            var contacts = JSON.parse(JSON.stringify(data));
            for (var i in contacts) {
                $("tbody").
                append("<tr> \
                            <td>" +  contacts[i].id + "</td> \
                            <td>" +  contacts[i].name + "</td> \
                            <td>" +  contacts[i].phone + "</td> \
                            <td>" +  contacts[i].email + "</td> \
                            <td> \ <button id='delete' class='btn btn-danger'>Delete</button> \
                           <button id='edit' class='btn btn-warning'>Edit</button> \ </td> \
                        </tr>");
            }
          },
          error: function(data) { 
            console.log(data);
            }
        });
       
    }

	function alertUsing(text, flag) {

		var alert = $(".alert");

		if(flag){
			alert.removeClass("alert-danger").addClass("alert-success");
		}else{
			alert.removeClass("alert-success").addClass("alert-danger");
			
		}
		
		alert.fadeIn(400);
		alert.css("display", "block");
		alert.text(text);
		setTimeout(
			function(){
				alert.fadeOut();
				},
			2000);

	  }

});