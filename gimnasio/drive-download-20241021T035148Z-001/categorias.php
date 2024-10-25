<?php
header("Content-Type: application/json");

// Conexión a la base de datos
$conn = new mysqli("localhost", "root", "", "gimnasio");

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Consultar categorías
$sql = "SELECT * FROM categorias";
$result = $conn->query($sql);

$categorias = [];

while ($row = $result->fetch_assoc()) {
    $categorias[] = $row;
}

echo json_encode($categorias);

$conn->close();
?>
