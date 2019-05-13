$(document).ready(function () {
	if($("#computerName").val().trim() == ""){
		  $('#submit').prop('disabled', true);
	  }
	
	if($("#introduced").val() == "") {
	  $('#discontinued').prop('disabled', true);
  }
  
  $("#introduced").on('change', function () {
	  if(event.currentTarget.value == "") {
		  $('#discontinued').val("");
	  } else if(new Date(event.currentTarget.value).getTime() > new Date($('#discontinued').val())) {
		  $('#discontinued').val(event.currentTarget.value);
	  }
	  
	  $('#discontinued').prop('disabled', event.currentTarget.value == "");
	  $('#discontinued').attr('min', event.currentTarget.value);
	  
  });
  
  $("#computerName").on('input propertychange', function () {
	  if($("#computerName").val().trim() != ""){
		  $('#submit').prop('disabled', false);
	  } else {
		  $('#submit').prop('disabled', true);
	  }
  });
});