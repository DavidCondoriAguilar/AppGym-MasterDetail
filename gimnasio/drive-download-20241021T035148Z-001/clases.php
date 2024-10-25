<?php
header("Content-Type: application/json");

// Conexión a la base de datos
$conn = new mysqli("localhost", "root", "", "gimnasio");

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Consultar clases
$sql = "SELECT * FROM clases";
$result = $conn->query($sql);

$clases = [];

while ($row = $result->fetch_assoc()) {
    $clases[] = $row;
}

echo json_encode($clases);

$conn->close();
?>
