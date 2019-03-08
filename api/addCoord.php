<?php
	if($_SERVER['REQUEST_METHOD']=='POST'){
		date_default_timezone_set('America/Maceio');

		//Getting values
		$lat = $_POST['lat'];
		$lng = $_POST['lng'];
		$idap = "Type the name of device";
		$datadia = date("d/m/Y");
		$horadia = date("H:i:s");

		//Creating an sql query
		$sql = "INSERT INTO coordenadas (lat,lng, idaparelho, data, hora)
		        VALUES ('$lat','$lng','$idap', '$datadia', '$horadia')";

		//Importing our db connection script
		require_once('dbConnect.php');

		//Executing query to database
		if(mysqli_query($con,$sql)){
			$coord = 1;
		}else{
			$coord = 2;
		}

		//Closing the database
		mysqli_close($con);
	}