<?php
header("Content-Type: application/json");

// Conexión a la base de datos
$conn = new mysqli("localhost", "root", "", "gimnasio");

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Obtener el ID del usuario de la solicitud (por ejemplo, como parámetro GET)
$id = isset($_GET['id']) ? intval($_GET['id']) : 0;

if ($id > 0) {
    // Consultar detalles del usuario
    $sql = "SELECT u.id, u.nombre, u.correo, u.fecha_nacimiento, u.fecha_registro, u.membresia_id, 
                   m.tipo AS membresia, e.nombre AS entrenador 
            FROM usuarios u 
            LEFT JOIN membresias m ON u.membresia_id = m.id 
            LEFT JOIN entrenadores e ON u.id = e.id 
            WHERE u.id = ?";

    // Preparar la consulta
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("i", $id);
    $stmt->execute();
    $result = $stmt->get_result();

    $usuario = $result->fetch_assoc();

    if ($usuario) {
        echo json_encode($usuario);
    } else {
        echo json_encode(["error" => "Usuario no encontrado."]);
    }

    $stmt->close();
} else {
    echo json_encode(["error" => "ID de usuario inválido."]);
}

$conn->close();
?>
