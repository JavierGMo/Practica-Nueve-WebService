<?php
    include '../bd/conexion.php';
    
    $consulta = "SELECT * FROM pagopaypal;";
    $resultado = mysqli_query($conexion, $consulta);
    $json = array();
    if(!$resultado){
        $json['pagopp'][] = array(
            'error' => 'consulta fallida'
        );
        die("Error: ".mysqli_error($conexion));
    }else{
        while($valores = mysqli_fetch_array($resultado)){
            $json['pago'][] = array(
                'id' => $valores['IDPAGOPP'],
                'cuenta' => $valores['CUENTA'],
                'nombre' => $valores['NOMBRE'],
                'monto' => $valores['MONTO']
            );
        }   
    }
    mysqli_close($conexion);
    $json = json_encode($json);
    echo $json;
?>