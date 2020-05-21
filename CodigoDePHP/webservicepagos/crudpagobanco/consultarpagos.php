<?php
    include '../bd/conexion.php';
    
    $consulta = "SELECT * FROM pagobanco;";
    $resultado = mysqli_query($conexion, $consulta);
    $json = array();
    if(!$resultado){
        $json['pagob'][] = array(
            'error' => 'consulta fallida'
        );
        die("Error: ".mysqli_error($conexion));
    }else{
        while($valores = mysqli_fetch_array($resultado)){
            $json['pago'][] = array(
                'id' => $valores['IDPAGOBANCO'],
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