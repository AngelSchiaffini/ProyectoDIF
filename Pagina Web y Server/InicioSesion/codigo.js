function iniciarSesion() {
    console.log("Hola")
    const curpUsuario = document.getElementById('curpUsuario').value;

    if (curpUsuario) {
        // Realiza una solicitud GET al servidor con el valor de 'curpUsuario'
        fetch(`http://34.197.187.131:8080/inicioSesion/${curpUsuario}`)
            .then(response => {
                if (response.status === 200) {
                    // Redirige a la página "datosUsuario.html" cuando se encuentra el usuario
                    window.location.href = '/datosUsuario.html';
                } else {
                    // Maneja el caso en el que el usuario no se encuentra
                    alert('Usuario no encontrado');
                }
            })
            .catch(error => {
                console.error('Error en la solicitud:', error);
            });
    } else {
        alert('Por favor ingresa un valor en el campo Curp / No. Usuario');
    }5
}