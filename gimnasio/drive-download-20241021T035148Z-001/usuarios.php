<?php
header("Content-Type: application/json");
header("Access-Control-Allow-Origin: *");

// Conexión a la base de datos
$conn = new mysqli("localhost", "root", "", "gimnasio");

// Verificar conexión
if ($conn->connect_error) {
    die(json_encode(["error" => "Connection failed: " . $conn->connect_error]));
}

// Consultar usuarios
$sql = "SELECT id, nombre, correo FROM usuarios"; // Selecciona solo los campos que necesitas
$result = $conn->query($sql);

$usuarios = [];

if ($result) {
    while ($row = $result->fetch_assoc()) {
        $usuarios[] = $row;
    }
}

// Enviar respuesta JSON
echo json_encode($usuarios);

$conn->close();
?>
