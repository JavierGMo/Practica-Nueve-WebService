<?php
    include '../bd/conexion.php';
    print_r($_POST);
    if(!empty($_POST)){

        $cuenta = isset($_POST['cuenta'])?$_POST['cuenta']:'null';
        $nombre = isset($_POST['nombre'])?$_POST['nombre']:'null';
        $monto = isset($_POST['monto'])?$_POST['monto']:'null';

        echo 'Codigo: '.$cuenta.'<br>';
        var_dump($_POST);
        $consulta = "INSERT INTO `pagobanco` VALUES('NULL', '$cuenta', '$nombre', '$monto');";
        mysqli_query($conexion, $consulta) or die(mysqli_error($conexion));
    }
    mysqli_close($conexion);

?>