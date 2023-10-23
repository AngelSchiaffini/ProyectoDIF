// Conexion a base de datos

const express = require('express');
const mssql = require('mssql');
const port = 8080;
const ipAddr = '34.197.187.131';

const app = express();
app.use(express.static(__dirname + '/public'));
app.use(express.json());

const dbConfig = {
  user: process.env.MSSQL_USER,
  password: process.env.MSSQL_PASSWORD,
  database: 'ComedorBD',
  server: 'localhost',
  pool: { max: 10, min: 0, idleTimeoutMillis: 30000 },
  options: { trustServerCertificate: true }
};

async function connectDb() {
  try {
    await mssql.connect(dbConfig);
    console.log('Connected to the database.');
  } catch (err) {
    console.error('Unable to connect to the database.');
    throw err;
  }
}

connectDb();


//Conexion a Pagina WEB
app.use(express.static(__dirname + '/public'));

app.get('/', (req, res) => {
  res.type('text/plain');
  res.status(200);
  res.send('hola mundo');
});


// Obtienes los datos de los administradores
app.get('/Administrador', async (req, res) => {
  try {
    const rows = (await mssql.query`
      SELECT IDAdmin, Nombre, Apellido1, Apellido2, ContrasenaAdmin FROM Administrador
    `).recordset;
    const result = [];
    for (let row of rows) {
      result.push({
        IDAdmin: row.IDAdmin,
        Nombre: row.Nombre,
        Apellido1: row.Apellido1,
        Apellido2: row.Apellido2,
        ContrasenaAdmin: row.ContrasenaAdmin,
      });
    }
    res.json(result);
  } catch (err) {
    res.status(500).json(err);
  }
});

//Datos de un admin especifico por ID
app.get('/Administrador/:IDAdmin', async (req, res) => {
  try {
    const IDAdmin = req.params.IDAdmin;
    const rows = (await mssql.query`
        SELECT IDAdmin, Nombre, Apellido1, Apellido2, ContrasenaAdmin FROM Administrador 
        WHERE IDAdmin=${IDAdmin}
    `).recordset;
    const row = rows[0];
    if (row) {
      res.json({
        IDAdmin: row.IDAdmin,
        Nombre: row.Nombre,
        Apellido1: row.Apellido1,
        Apellido2: row.Apellido2,
        ContrasenaAdmin: row.ContrasenaAdmin,
      });
    } else {
      res.type('text').status(404).send(
        `Resource with ID = ${IDAdmin} not found.\n`);
    }
  } catch (err) {
    res.status(500).json(err);
  }
});

//Agregar un nuevo admin
app.post('/Administrador', async (req, res) => {
  try {
    const {IDAdmin, Nombre, Apellido1, Apellido2, ContrasenaAdmin} = req.body;
    const result = await mssql.query`
      INSERT INTO Administrador (Nombre, Apellido1, Apellido2, ContrasenaAdmin) 
      VALUES (${Nombre}, ${Apellido1}, ${Apellido2}, ${ContrasenaAdmin}); 
      SELECT SCOPE_IDENTITY() AS [NewID]
    `;
    const newID = result.recordset[0]['NewID'];
    res.type('text').status(201).send(
      `Resource created with IDAdmin = ${newID}.\n`);
  } catch (err) {
    res.status(500).json(err);
  }
});


//Borrar un admin por ID
app.delete('/Administrador/:IDAdmin', async (req, res) => {
  try {
    const IDAdmin = req.params.IDAdmin;
    const result = await mssql.query`
      DELETE FROM Administrador 
      WHERE IDAdmin=${IDAdmin}
    `;
    if (result.rowsAffected[0] === 1) {
      res.type('text').send(`Resource with IDAdmin = ${IDAdmin} deleted.\n`);
    } else {
      res.type('text').status(404).send(
        `Resource with IDAdmin = ${IDAdmin} not found. No resources deleted.\n`);
    }
  } catch (err) {
    res.status(500).json(err);
  }
});

//Borrar admins
app.delete('/Administrador', async (req, res) => {
  try {
    const result = await mssql.query`
      DELETE FROM Administrador
    `;
    res.type('text').send(`${result.rowsAffected[0]} resource(s) deleted.\n`);
  } catch (err) {
    res.status(500).json(err);
  }
});

//Modificar datos de un admin por ID
app.put('/Administrador/:IDAdmin', async (req, res) => {
  try {
    const IDAdmin = req.params.IDAdmin;
    const {Nombre, Apellido1, Apellido2, ContrasenaAdmin } = req.body;
    const result = await mssql.query`
        UPDATE Administrador 
        SET Nombre=${Nombre}, Apellido1=${Apellido1}, Apellido2=${Apellido2}, ContrasenaAdmin=${ContrasenaAdmin} 
        WHERE IDAdmin=${IDAdmin}
    `;
    if (result.rowsAffected[0] === 1) {
      res.type('text').send(
        `Resource with IDAdmin = ${IDAdmin} updated.\n`);
    } else {
      res.type('text').status(404).send(
        `Resource with IDAdmin = ${IDAdmin} not found. No resources updated.\n`);
    }
  } catch (err) {
    res.status(500).json(err);
  }
});

//Recibe todos los datos de los usuarios
app.get('/Usuario', async (req, res) => {
  try {
    const rows = (await mssql.query`
      SELECT IDUsuario, Nombre, Apellido1, Apellido2, CURP, Nacionalidad, Sexo, FechaNac, Condicion, Cel, Correo  FROM Usuario
    `).recordset;
    const result = [];
    for (let row of rows) {
      result.push({
        IDUsuario: row.IDUsuario,
        Nombre: row.Nombre,
        Apellido1: row.Apellido1,
        Apellido2: row.Apellido2,
        CURP: row.CURP,
        Nacionalidad: row.Nacionalidad,
        Sexo: row.Sexo,
        FechaNac: row.FechaNac,
        Condicion: row.Condicion,
        Cel: row.Cel,
        Correo: row.Correo,
      });
    }
    res.json(result);
  } catch (err) {
    res.status(500).json(err);
  }
});

//Datos de un usuario especifico por ID
app.get('/Usuario/:IDUsuario', async (req, res) => {
  try {
    const IDUsuario = req.params.IDUsuario;
    const rows = (await mssql.query`
        SELECT IDUsuario, Nombre, Apellido1, Apellido2, CURP, Nacionalidad, Sexo, FechaNac, Condicion, Cel, Correo  FROM Usuario 
        WHERE IDUsuario=${IDUsuario}
    `).recordset;
    const row = rows[0];
    if (row) {
      res.json({
        IDUsuario: row.IDUsuario,
        Nombre: row.Nombre,
        Apellido1: row.Apellido1,
        Apellido2: row.Apellido2,
        CURP: row.CURP,
        Nacionalidad: row.Nacionalidad,
        Sexo: row.Sexo,
        FechaNac: row.FechaNac,
        Condicion: row.Condicion,
        Cel: row.Cel,
        Correo: row.Correo,
      });
    } else {
      res.type('text').status(404).send(
        `Resource with IDUsuario = ${IDUsuario} not found.\n`);
    }
  } catch (err) {
    res.status(500).json(err);
  }
});


//Agregar un nuevo usuario
app.post('/Usuario', async (req, res) => {
  try {
    const {Nombre, Apellido1, Apellido2, CURP, Nacionalidad, Sexo, FechaNac, Condicion, Cel, Correo} = req.body;
    const result = await mssql.query`
      INSERT INTO Usuario (Nombre, Apellido1, Apellido2, CURP, Nacionalidad, Sexo, FechaNac, Condicion, Cel, Correo) 
      VALUES (${Nombre}, ${Apellido1}, ${Apellido2}, ${CURP}, ${Nacionalidad}, ${Sexo}, ${FechaNac}, ${Condicion}, ${Cel}, ${Correo}); 
      SELECT SCOPE_IDENTITY() AS [NewID]
    `;
    const newID = result.recordset[0]['NewID'];
    res.type('text').status(201).send(
      `Resource created with IDUsuario = ${newID}.\n`);
  } catch (err) {
    res.status(500).json(err);
  }
});

//Modificar un usuario por ID
app.put('/Usuario/:IDUsuario', async (req, res) => {
  try {
    const IDUsuario = req.params.IDUsuario;
    const {Nombre, Apellido1, Apellido2, CURP, Nacionalidad, Sexo, FechaNac, Condicion, Cel, Correo } = req.body;
    const result = await mssql.query`
        UPDATE Usuario 
        SET Nombre=${Nombre}, Apellido1=${Apellido1}, Apellido2=${Apellido2}, CURP=${CURP}, Nacionalidad=${Nacionalidad},
        Sexo=${Sexo}, FechaNac=${FechaNac}, Condicion=${Condicion}, Cel=${Cel}, Correo=${Correo} 
        WHERE IDUsuario=${IDUsuario}
    `;
    if (result.rowsAffected[0] === 1) {
      res.type('text').send(
        `Resource with IDUsuario = ${IDUsuario} updated.\n`);
    } else {
      res.type('text').status(404).send(
        `Resource with IDUsuario = ${IDUsuario} not found. No resources updated.\n`);
    }
  } catch (err) {
    res.status(500).json(err);
  }
});

//Regresar toda la tabla asistencia
app.get('/Asistencia', async (req, res) => {
  try {
    const rows = (await mssql.query`
      SELECT Fecha, Donacion, IDUsuario, FolioComedor FROM Asistencia
    `).recordset;
    const result = [];
    for (let row of rows) {
      result.push({
        Fecha: row.Fecha,
        Donacion: row.Donacion,
        IDUsuario: row.IDUsuario,
        FolioComedor: row.FolioComedor,
      });
    }
    res.json(result);
  } catch (err) {
    res.status(500).json(err);
  }
});

//Regresan todo la tabla Inventario
app.get('/Inventario', async (req, res) => {
  try {
    const rows = (await mssql.query`
      SELECT FechaCad, Nombre, Cantidad, Presentacion, FolioComedor FROM Inventario
    `).recordset;
    const result = [];
    for (let row of rows) {
      result.push({
        FechaCad: row.FechaCad,
        Nombre: row.Nombre,
        Cantidad: row.Cantidad,
        Presentacion: row.Presentacion,
        FolioComedor: row.FolioComedor,
      });
    }
    res.json(result);
  } catch (err) {
    res.status(500).json(err);
  }
});

//Regresa toda la tabla Estado
app.get('/Estado', async (req, res) => {
  try {
    const rows = (await mssql.query`
      SELECT IDEstado, Estado FROM Estado
    `).recordset;
    const result = [];
    for (let row of rows) {
      result.push({
        IDEstado: row.IDEstado,
        Estado: row.Estado,
      });
    }
    res.json(result);
  } catch (err) {
    res.status(500).json(err);
  }
});

//Regresa toda la tabla Condicion
app.get('/Condicion', async (req, res) => {
  try {
    const rows = (await mssql.query`
      SELECT IDCondicion, Cond FROM Condicion
    `).recordset;
    const result = [];
    for (let row of rows) {
      result.push({
        IDCondicion: row.IDCondicion,
        Cond: row.Cond,
      });
    }
    res.json(result);
  } catch (err) {
    res.status(500).json(err);
  }
});

//Regresa toda la tabla nacionalidad
app.get('/Nacionalidad', async (req, res) => {
  try {
    const rows = (await mssql.query`
      SELECT IDNacionalidad, Nac FROM Nacionalidad
    `).recordset;
    const result = [];
    for (let row of rows) {
      result.push({
        IDNacionalidad: row.IDNacionalidad,
        Nac: row.Nac,
      });
    }
    res.json(result);
  } catch (err) {
    res.status(500).json(err);
  }
});

//Regresa toda la tabla calificaiones
app.get('/Calificaciones', async (req, res) => {
  try {
    const rows = (await mssql.query`
      SELECT IDUsuario, FolioComedor, Fecha, CalLimpieza, CalComida, CalAtencion FROM Calificaciones
    `).recordset;
    const result = [];
    for (let row of rows) {
      result.push({
        IDUsuario: row.IDUsuario,
        FolioComedor: row.FolioComedor,
        Fecha: row.Fecha,
        CalLimpieza: row.CalLimpieza,
        CalComida: row.CalComida,
        CalAtencion: row.CalAtencion,
      });
    }
    res.json(result);
  } catch (err) {
    res.status(500).json(err);
  }
});


//Regresa toda la tabla
app.get('/Comedor', async (req, res) => {
  try {
    const rows = (await mssql.query`
      SELECT FolioComedor, Nombre, Ubicacion, Apertura, Usuario, ContraComedor, Estado FROM Comedor
    `).recordset;
    const result = [];
    for (let row of rows) {
      result.push({
        FolioComedor: row.FolioComedor,
        Nombre: row.Nombre,
        Ubicacion: row.Ubicacion,
        Apertura: row.Apertura,
        Usuario: row.Usuario,
        ContraComedor: row.ContraComedor,
        Estado: row.Estado,
      });
    }
    res.json(result);
  } catch (err) {
    res.status(500).json(err);
  }
});


//Regresa toda la tabla listaComedores
app.get('/ListaComedores', async(req, res) => {
  try{
    const request = new mssql.Request();
    let result = await request.execute('dbo.PROC_listaComedores');
    let listaCom = result.recordset;
    
    mssql.close();
    
    res.json(listaCom);
  } catch (err) {
    res.status(500).json(err);
  }
});

//Modifica la informacion del comedor que se ingrese su folio
app.put('/Comedor/:FolioComedor', async (req, res) => {
  try {
    const FolioComedor = req.params.FolioComedor;
    const {Nombre, Ubicacion, Apertura, Usuario, ContraComedor, Estado} = req.body;
    const result = await mssql.query`
        UPDATE Comedor 
        SET Nombre=${Nombre}, Ubicacion=${Ubicacion}, Apertura=${Apertura}, Usuario=${Usuario}, ContraComedor=${ContraComedor}, Estado=${Estado}
        WHERE FolioComedor=${FolioComedor}
    `;
    if (result.rowsAffected[0] === 1) {
      res.type('text').send(
        `Resource with FolioComedor = ${FolioComedor} updated.\n`);
    } else {
      res.type('text').status(404).send(
        `Resource with FolioComedor = ${FolioComedor} not found. No resources updated.\n`);
    }
  } catch (err) {
    res.status(500).json(err);
  }
});

//Agregar un nuevo comedor a la tabla comedor
app.post('/Comedor', async (req, res) => {
  try {
    const {FolioComedor, Nombre, Ubicacion, Apertura, Usuario, ContraComedor, Estado} = req.body;
   const result = await mssql.query`
  INSERT INTO Comedor(Nombre, Ubicacion, Apertura, Usuario, ContraComedor, Estado)
  VALUES (${Nombre}, ${Ubicacion}, ${Apertura}, ${Usuario}, ${ContraComedor}, ${Estado});
`;

  } catch (err) {
    res.status(500).json(err);
  }
});


//Utiliza el procedur loginUsuario para revisar si el usuario existe por ID
app.get('/inicioSesion/:id', async (req, res) => {
    try{
        
        //Variables
        let id = req.params.id;
        await mssql.connect(dbConfig);
        const request = new mssql.Request();
        request.input('IDUsuario', mssql.Int, id);
        request.output('Success',mssql.Bit);
        
        //Ejecuta el procedimiento almacenado
        let result = await request.execute('dbo.PROC_loginUsuarioID');
        let usuario = result.recordset;
        
        console.log(usuario)
        mssql.close();
        
        // Verifica el valor del parámetro de salida Success para determinar si la operación fue exitosa
        if (usuario){
            res.status(200).json(usuario)
        } else{
            res.status(200).json({mensaje: 'Usuario no encontrado'});
        }
    }catch (err){
            res.status(500).json({ error: 'Error interno del servidor' });
    }
})




//Utiliza el procedur loginUsuario para revisar si el usuario existe por CURP
app.get('/inicioSesion2/:curp', async(req, res) => {
    try{
        let curp = req.params.curp;
        const request = new mssql.Request();
        request.input('CURP', mssql.Char(18), curp)
        request.output('Success',mssql.Bit);
        
         //Ejecuta el procedimiento almacenado
        let result = await request.execute('dbo.PROC_loginUsuarioCURP');
        let usuario = result.recordset;
        console.log(usuario)
        
        // Verifica el valor del parámetro de salida Success para determinar si la operación fue exitosa
        if (usuario){
            res.status(200).json(usuario)
        } else{
            res.status(200).json({mensaje: 'Usuario no encontrado'});
        }
        
    } catch(err){
        res.status(500).json({ error: 'Error interno del servidor' })
    }
})


//Utiliza el procedur loginUsuario para revisar si el usuario existe por Celular
app.get('/inicioSesion3/:celular', async (req, res) => {
  try {
    let celular = req.params.celular;
    const request = new mssql.Request();
    request.input('Celular', mssql.VarChar(15), celular); // Ajusta el tipo de datos y la longitud
    request.output('Success', mssql.Bit);

    // Ejecuta el procedimiento almacenado
    let result = await request.execute('dbo.PROC_loginUsuarioCelular');
    let usuario = result.recordset;

    // Verifica el valor del parámetro de salida Success para determinar si la operación fue exitosa
    if (result.output.Success === true) {
      res.status(200).json(usuario);
    } else {
      res.status(200).json({ mensaje: 'Usuario no encontrado' });
    }
  } catch (err) {
    res.status(500).json({ error: 'Error interno del servidor' });
  }
});


//Utiliza el procedur loginUsuario para revisar si el usuario existe por correo
app.get('/inicioSesion4/:correo', async (req, res) => {
  try {
    let correo = req.params.correo;
    const request = new mssql.Request();
    request.input('Correo', mssql.VarChar(30), correo); // Ajusta el tipo de datos y la longitud
    request.output('Success', mssql.Bit);

    // Ejecuta el procedimiento almacenado
    let result = await request.execute('dbo.PROC_loginUsuarioCorreo');
    let usuario = result.recordset;

    // Verifica el valor del parámetro de salida Success para determinar si la operación fue exitosa
    if (result.output.Success === true) {
      res.status(200).json(usuario);
    } else {
      res.status(200).json({ mensaje: 'Usuario no encontrado' });
    }
  } catch (err) {
    res.status(500).json({ error: 'Error interno del servidor' });
  }
});

//Da de alta un nuevo comedor
app.post('/api/altaComedor', async (req, res) => {
  try {
    const { FolioComedor, Nombre, Ubicacion, Apertura, Usuario, ContraComedor, Estado } = req.body;
    await mssql.connect(dbConfig);
    const request = new mssql.Request();
    request.input('FolioComedor', mssql.Int, FolioComedor);
    request.input('Nombre', mssql.VarChar(50), Nombre);
    request.input('Ubicacion', mssql.VarChar(80), Ubicacion);
    request.input('Apertura', mssql.Date, Apertura);
    request.input('Usuario', mssql.VarChar(10), Usuario);
    request.input('ContraComedor', mssql.VarChar(15), ContraComedor);
    request.input('Estado', mssql.Int, Estado);
    request.output('Success', mssql.Bit);

    let result = await request.execute('dbo.PROC_altaComedor');

    let success = result.returnValue;

    if (success === 1) {
      // Alta exitosa
      res.status(200).json({ mensaje: 'Alta de comedor exitosa' });
    } else {
      // Alta fallida
      res.status(400).json({ error: 'Error en la alta de comedor' });
    }
  } catch (err) {
    console.error('Error en alta de comedor:', err);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
});

//Revisa las credenciales de un Admin
app.post('/loginAdmin', async (req, res) => {
  try {
    const { IDAdmin, Contrasena } = req.body;

    await mssql.connect(dbConfig);
    const request = new mssql.Request();
    request.input('IDAdmin', mssql.Int, IDAdmin);
    request.input('Contrasena', mssql.VarChar(64), Contrasena);
    request.output('Success', mssql.Bit);

    let result = await request.execute('dbo.PROC_logInAdmin');

    let success = result.returnValue;
    mssql.close();

    if (success == 1) {
      // Autenticación exitosa
      res.status(200).json({ mensaje: 'Autenticación exitosa' });
    } else {
      // Autenticación fallida
      res.status(401).json({ error: 'Credenciales inválidas' });
    }
  } catch (err) {
    console.error('Error al autenticar administrador:', err);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
});

//Da de baja un comedor
app.post('/bajaComedor', async (req, res) => {
  
  try {
    const { FolioComedor } = req.body;
    
    await mssql.connect(dbConfig);
    const request = new mssql.Request();
    request.input('FolioComedor', mssql.Int, FolioComedor);
    request.output('Success', mssql.Bit);

    let result = await request.execute('dbo.PROC_bajaComedor');

    let success = result.returnValue;
    //mssql.close();


    if (success === 1) {
      res.status(200).json({ mensaje: 'Comedor dado de baja exitosamente' });
    } else {
      res.status(400).json({ error: 'No se pudo dar de baja el comedor' });
    }
  } catch (err) {
    console.error('Error al dar de baja el comedor:', err);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
});

//Da de alta a un admin
app.post('/altaAdmin', async (req, res) => {
  const { Nombre, Apellido1, Apellido2, ContrasenaAdmin } = req.body;

  try {
    const request = new mssql.Request();
    request.input('Nombre', mssql.VarChar(50), Nombre);
    request.input('Apellido1', mssql.VarChar(50), Apellido1);
    request.input('Apellido2', mssql.VarChar(50), Apellido2);
    request.input('ContrasenaAdmin', mssql.VarChar(15), ContrasenaAdmin);
    request.output('Success', mssql.Bit);
    
    let result = await request.execute('dbo.PROC_altaAdmin');
    let success = result.returnValue;


    if (success === 1) {
      res.status(200).json({ mensaje: 'Administrador dado de alta exitosamente' });
    } else {
      res.status(400).json({ error: 'No se pudo dar de alta al administrador' });
    }
  } catch (err) {
    console.error('Error al dar de alta al administrador:', err);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
});

//Da de alta a un usuario
app.post('/altaUsuario', async (req, res) => {
  try {
    const {
      Nombre,
      Apellido1,
      Apellido2,
      CURP,
      Nacionalidad,
      Sexo,
      FechaNac,
      Condicion,
      Cel,
      Correo,
    } = req.body;

    const request = new mssql.Request();
    request.input('Nombre', mssql.VarChar(50), Nombre);
    request.input('Apellido1', mssql.VarChar(50), Apellido1);
    request.input('Apellido2', mssql.VarChar(50), Apellido2);
    request.input('CURP', mssql.Char(18), CURP);
    request.input('Nacionalidad', mssql.VarChar(30), Nacionalidad);
    request.input('Sexo', mssql.Char(1), Sexo);
    request.input('FechaNac', mssql.Date, FechaNac);
    request.input('Condicion', mssql.VarChar(50), Condicion);
    request.input('Cel', mssql.VarChar(15), Cel);
    request.input('Correo', mssql.VarChar(30), Correo);
    request.output('Success', mssql.Bit);
    request.output('NuevoIDUsuario', mssql.Int);
    

    let result = await request.execute('dbo.PROC_altaUsuario');
    let success = result.returnValue;

    if (success === 1) {
      res.status(200).json({ mensaje: 'Usuario dado de alta con éxito' });
    } else {
      res.status(500).json({ error: 'Error al dar de alta al usuario' });
    }
  } catch (err) {
    res.status(500).json({ error: 'Error interno del servidor' });
  }
});

///////. COMEDOR

app.post('/iniciarSesionComedor', async(req,res) =>{
    try{
        
        const { Usuario, Contrasena} = req.body;
        
        const request = new mssql.Request();
        request.input('Usuario', mssql.VarChar(10), Usuario);
        request.input('Contrasena',mssql.VarChar(80), Contrasena);
        request.output('Success',mssql.Bit);
        
        // Ejecuta el procedimiento almacenado
        let result = await request.execute('dbo.PROC_logInComedor');
        let bit = result.returnValue;
        
        if (bit > 0){
            res.status(200).json({mensaje: "Ingreso Correcto"});
        } else{
            res.status(200).json({mensaje: "Usuario o contraseñas incorrectos"});
        } 
        
    } catch(err){
        res.status(500).json({Error: 'Error interno del servidor'});   
    }
});

//Regresa el comedor del mes
app.get('/comedorDelMes', async (req, res) => {
  const { mes, anio } = req.query;

  try {
    const request = new mssql.Request();
    request.input('mes', mssql.Int, mes);
    request.input('anio', mssql.Int, anio);

    const result = await request.execute('dbo.PROC_comedorDelMes');
    const comedorDelMes = result.recordset[0];

    res.json(comedorDelMes);
  } catch (err) {
    console.error('Error al ejecutar el procedimiento almacenado:', err);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
});


//Busca los familiares asociados al pariente indicado
app.get('/buscarFamiliares/:pariente1', async (req, res) => {
  try {
    let pariente1 = req.params.pariente1;
    
    await mssql.connect(dbConfig);

    const request = new mssql.Request();
    request.input('Pariente1', mssql.Int, pariente1);

    const result = await request.execute('dbo.PROC_buscarFamiliares');
    const familiares = result.recordset;

    mssql.close();

    res.json(familiares);
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
});

//Regresa el id del usuario relacionado con el Curp dado
app.get('/obtenerIDcCu/:CURP', async(req, res) => {
  try{
    let CURP = req.params.CURP;
    
    await mssql.connect(dbConfig);
    
    const request = new mssql.Request();
    request.input('CURP', mssql.Char(18), CURP);
    
    const result = await request.execute('dbo.PROC_obtenerIDcCURP');
    const ID = result.recordset;
    
    mssql.close();
    
    res.json(ID);
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
});

//Regresa el id del usuario relacionado con el CEl dado
app.get('/obtenerIDcCe/:Cel', async(req, res) => {
  try{
    let Cel = req.params.Cel;
    
    await mssql.connect(dbConfig);
    
    const request = new mssql.Request();
    request.input('Cel', mssql.VarChar(15), Cel);
    
    const result = await request.execute('dbo.PROC_obtenerIDcCel');
    const ID = result.recordset;
    
    mssql.close();
    
    res.json(ID);
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
});

//Regresa el id del usuario relacionado con el Correo dado

app.get('/obtenerIDcCo/:Correo', async(req, res) => {
  try{
    let Correo = req.params.Correo;
    
    await mssql.connect(dbConfig);
    
    const request = new mssql.Request();
    request.input('Correo', mssql.VarChar(30), Correo);
    
    const result = await request.execute('dbo.PROC_obtenerIDcCorreo');
    const ID = result.recordset;
    
    mssql.close();
    
    res.json(ID);
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
});

//Da de baja a un pariente
app.post('/bajaPariente', async (req, res) => {
  try {
    const { Pariente1, Pariente2 } = req.body;

    await mssql.connect(dbConfig);

    const request = new mssql.Request();
    request.input('Pariente1', mssql.Int, Pariente1);
    request.input('Pariente2', mssql.Int, Pariente2);
    request.output('Success', mssql.Bit);

    let result = await request.execute('dbo.PROC_bajaPariente');
    let success = result.returnValue;

    mssql.close();

    if (success) {
      res.status(200).json({ mensaje: 'Pariente dado de baja con éxito' });
    } else {
      res.status(500).json({ error: 'Error al dar de baja al pariente' });
    }
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
});

//Da de alta a un pariente
app.post('/altaPariente', async (req, res) => {
  try {
    const { Pariente1, Pariente2 } = req.body;
    await mssql.connect(dbConfig);
    
    const request = new mssql.Request();
    request.input('Pariente1', mssql.Int, Pariente1);
    request.input('Pariente2', mssql.Int, Pariente2);
    request.output('Success', mssql.Bit);
    
    let result = await request.execute('dbo.PROC_altaPariente');
    let success = result.returnValue;

    mssql.close();
    if (success) {
        res.status(200).json({ mensaje: 'Pariente dado de alta con éxito' });
    } else {
        res.status(500).json({ error: 'Error al dar de alta al pariente' });
    }
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Error en la solicitud.' });
  }
});

//Regresa todos los reportes enviados
app.get('/api/reportes', async (req, res) => {
  try {
    const pool = await mssql.connect(dbConfig);

    const result = await pool.request().query('SELECT * FROM Reportes');

    res.json(result.recordset); // Devuelve los datos como respuesta JSON
  } catch (error) {
    console.error('Error al obtener los datos de la tabla Reportes:', error);
    res.status(500).json({ error: 'Error al obtener los datos' });
  }
});

//Da de alta a un nuevo usuario
app.post('/registrarUsuario', async (req, res) => {
  try {
    const { NOMBRE, APELLIDO1, APELLIDO2, CURP, NACIONALIDAD, SEXO, FECHANAC, CONDICION, CEL, CORREO } = req.body;

    // Llama al procedimiento almacenado usando el método request.input
    const request = new mssql.Request();
    
    request.input('Nombre', mssql.VarChar(50), NOMBRE);
    request.input('Apellido1', mssql.VarChar(50), APELLIDO1);
    request.input('Apellido2', mssql.VarChar(50), APELLIDO2);
    request.input('CURP', mssql.Char(18), CURP);
    request.input('Nacionalidad', mssql.VarChar(30), NACIONALIDAD);
    request.input('Sexo', mssql.Char, SEXO);
    request.input('FechaNac', mssql.Date, FECHANAC);
    request.input('Condicion', mssql.VarChar(50), CONDICION);
    request.input('Cel', mssql.VarChar(15), CEL);
    request.input('Correo', mssql.VarChar(30), CORREO);

    request.output('Success',mssql.Int);
    // Parámetro de salida para indicar el éxito o fracaso del procedimiento almacenado
    request.output('NuevoIDUsuario',mssql.Int);
  
    // Ejecuta el procedimiento almacenado
    let result = await request.execute('dbo.PROC_altaUsuario');
    let recordset = result.recordset;
    
     // Verifica si el conjunto de registros no está vacío
    if (recordset && recordset.length > 0) {
      // Accede al primer registro del conjunto de registros
      let idNuevo = recordset[0];
      
      res.status(200).json(idNuevo);
      
    }
 else {
      res.status(500).json({mensaje: 'Usuario no creado'});
    }
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
});



//Regresa toda la lista de comedores que cumplan con el procedure PROC_listaComedores
app.get('/obtenerComedores', async(req,res) => {
    try {
        const request = new mssql.Request();
        
        //Ejecuta el procedimiento almacenado
        let result = await request.execute('dbo.PROC_listaComedores');
        let comedores = result.recordset;
        console.log(comedores)
        
        if(comedores){
           res.status(200).json(comedores) 
        } else{
            res.status(200).json({mensaje: 'Ningun comedor activo'});
        }
    } catch(err) {
       res.status(500).json({ error: 'Error interno del servidor' }) 
    }
});


//Agrega una nueva calificacion  al comedor 
app.post('/calificarComedor', async(req,res) =>{
    try{
        
        const { IDUsuario, FolioComedor, Fecha, CalLimpieza, CalComida, CalAtencion, Comentario} = req.body;
        
        const request = new mssql.Request();
        request.input('IDUsuario', mssql.Int, IDUsuario);
        request.input('FolioComedor', mssql.Int, FolioComedor);
        request.input('Fecha', mssql.Date, Fecha);
        request.input('CalLimpieza', mssql.Int, CalLimpieza);
        request.input('CalComida', mssql.Int, CalComida);
        request.input('CalAtencion', mssql.Int, CalAtencion);
        request.input('Comentario', mssql.VarChar(150), Comentario)
        request.output('Success', mssql.Bit); 
        
        // Ejecuta el procedimiento almacenado
        let result = await request.execute('dbo.PROC_calificar');
        let bit = result.returnValue;
        
        if (bit > 0){
            res.status(200).json({mensaje: "Comedor calificado correctamente"});
        } else{
            res.status(200).json({mensaje: "Comedor no existente"});
        } 
    } catch(err){
            res.status(500).json({ error: 'Error interno del servidor' });
        }
});

//Añade a un nuevo pariente
app.post('/agregarPariente', async(req, res) =>{
    
    try{
        
        const {Pariente1, Pariente2} = req.body;
        
        const request = new mssql.Request();
        request.input('Pariente1', mssql.Int, Pariente1);
        request.input('Pariente2', mssql.Int, Pariente2);
        request.output('Success', mssql.Bit);
        
        // Ejecuta el procedimiento almacenado
        let result = await request.execute('dbo.PROC_altaPariente');
        let bit = result.returnValue;
        
        if (bit > 0){
            res.status(200).json({mensaje: "Pariente agregado correctamente"});
        } else{
            res.status(200).json({mensaje: "Pariente no agregado"});
        } 
        
    } catch(err){
            res.status(500).json({ error: 'Error interno del servidor' }); 
    }
});

//Elimina a un pariente especifico de otra persona
app.delete('/eliminarPariente/:Pariente1/:Pariente2', async(req, res) =>{
    
    try{
        
        let pariente1 = req.params.Pariente1;
        let pariente2 = req.params.Pariente2;
        
        const request = new mssql.Request();
        request.input('Pariente1', mssql.Int, pariente1);
        request.input('Pariente2', mssql.Int, pariente2);
        request.output('Success', mssql.Bit);
        
        // Ejecuta el procedimiento almacenado
        let result = await request.execute('dbo.PROC_altaPariente');
        let bit = result.returnValue;
        
        if (bit > 0){
            res.status(200).json({mensaje: "Pariente eliminado correctamente"});
        } else{
            res.status(200).json({mensaje: "Pariente no agregado"});
        } 
        
        
    } catch(err){
        res.status(500).json({ error: 'Error interno del servidor' });
    }
})


//Regresa a los parientes de alguien en especifico segun su ID
app.get('/obtenerFamilia/:id', async(req, res) =>{
    try{
        let id = req.params.id;
        const request = new mssql.Request();
        request.input('Pariente1', mssql.Int, id)
        
        //Ejecuta el procedimiento almacenado
        let result = await request.execute('dbo.PROC_buscarFamiliares');
        let familiares = result.recordset;
        
        if(familiares){
            res.status(200).json(familiares);
        } else {
            res.status(200).json({mensaje: "No hay familiares"});
        }
    } catch(err){
        res.status(500).json({ error: 'Error interno del servidor' });
    }
});

//Regresa la lista de usuarios
app.get('/obtenerUsuarios', async (req, res) => {
    try {
      const result = await mssql.query `
      SELECT * FROM USUARIO `;
      res.status(200).json(result.recordset[0]);
    } catch (err) {
      res.status(500).json({ error: 'Error interno del servidor' });
    }
});

//Elimina a un usuario por nombres y apellido
app.delete('/eliminarUsuario/:nombre/:apellido', async (req, res) => {
    try{
        let nombre = req.params.nombre;
        let apellido = req.params.apellido;
        const result = await mssql.query `
        DELETE FROM USUARIO WHERE NOMBRE = ${nombre} AND APELLIDO = ${apellido}`
         // Verificamos si algún registro fue afectado por la eliminación
        if (result.rowsAffected[0] > 0) {
        // La eliminación fue exitosa
            res.status(200).json({ message: 'Usuario eliminado correctamente' });
        } else {
        // No se encontró un usuario con esos datos
            res.status(404).json({ error: 'Usuario no encontrado' });
        }
    } catch(err){
        res.status(500).json({ error: 'Error interno del servidor' });
    }
});

//Actualiza informacion de un usuario segun su nombre y apellido
app.put('/actualizarUsuario/:NOMBRE/:APELLIDO', async (req,res) => {
    try{
        let NOMBRE = req.params.NOMBRE;
        let APELLIDO = req.params.APELLIDO;
        const {EDAD} = req.body;
        const result = await mssql.query `
        UPDATE USUARIO SET EDAD = ${EDAD} 
        WHERE NOMBRE = ${NOMBRE} AND APELLIDO = ${APELLIDO}`;
        // Verificamos si algún registro fue afectado por la eliminación
        if (result.rowsAffected[0] > 0) {
        // La eliminación fue exitosa
            res.status(200).json({ message: 'Usuario actualizado correctamente' });
        } else {
        // No se encontró un usuario con esos datos
            res.status(404).json({ error: 'Usuario no encontrado' });
        }
    } catch(err){
        res.status(500).json({Error: 'Error interno del servidor'});
    }
});



///////. COMEDOR

app.post('/iniciarSesionComedor', async(req,res) =>{
    try{
        
        const { Usuario, Contrasena} = req.body;
        
        const request = new mssql.Request();
        request.input('Usuario', mssql.VarChar(10), Usuario);
        request.input('Contrasena',mssql.VarChar(80), Contrasena);
        request.output('Success',mssql.Bit);
        
        // Ejecuta el procedimiento almacenado
        let result = await request.execute('dbo.PROC_logInComedor');
        let bit = result.returnValue;
        let comedor = result.recordset;
        
        
        if (bit > 0){
            res.status(200).json(comedor);
        } else{
            res.status(200).json({mensaje: "Usuario o contraseñas incorrectos"});
        } 
        
    } catch(err){
        res.status(500).json({Error: 'Error interno del servidor'});   
    }
});

//Regresa el promedio de calificaciones semanales de un comedor
app.get('/calificacionesSemanales/:id/:inicio/:fin', async(req,res) => {
    try{
        
        let folioComedor = req.params.id;
        let inicio = req.params.inicio;
        let fin = req.params.fin;
        
        const request = new mssql.Request();
        request.input('FolioComedor', mssql.Int, folioComedor);
        request.input('FechaInicio', mssql.Date, inicio);
        request.input('FechaFin', mssql.Date, fin);
        
        //Ejecuta el procedimeinto almacenado
        let result = await request.execute('dbo.PROC_promedioCalSemanal');
        let calificaciones = result.recordset;
        
        if (calificaciones){
            res.status(200).json(calificaciones);
        } else {
            res.status(200).json({mensaje: "Fecha mal ingresada"});
        } 
    } catch(err){
           res.status(500).json({Error: 'Error interno del servidor'});    
        }
});

//Regresa las ganancias del dia de un comedor
app.get('/gananciaDia/:id/:dia', async(req, res) =>{
    try{
        
        let folioComedor = req.params.id;
        let dia = req.params.dia;
        
        const request = new mssql.Request();
        request.input('FolioComedor', mssql.Int, folioComedor)
        request.input('Fecha', mssql.Date, dia )
        request.output('TotalGanancias',mssql.Int)
        
        //Ejecuta el procedimeinto almacenado
        let result = await request.execute('dbo.PROC_gananciasHoy');
        let ganancias = result.recordset;
        let gananciaDia = parseInt(ganancias[0].GananciasHoy);
    
        
        if (ganancias){
            res.status(200).send(gananciaDia.toString());
        } else {
            res.status(200).json({mensaje: "No hay comedor"});
        }
    } catch (err) {
       res.status(500).json({Error: 'Error interno del servidor'});   
    }
});

//Registra la asistencia de una persona 
app.post('/registrarAsistencia', async(req,res) =>{
    
    try{
        
        const { Fecha, Donacion, IDUsuario, FolioComedor} = req.body;
        
        const request = new mssql.Request();
        request.input('Fecha', mssql.Date, Fecha);
        request.input('Donacion', mssql.Int, Donacion);
        request.input('IDUsuario', mssql.Int, IDUsuario);
        request.input('FolioComedor', mssql.Int, FolioComedor);
        request.output('Success', mssql.Bit);
        
        //Ejecuta el procedimeinto almacenado
        let result = await request.execute('dbo.PROC_registrarAsistencia');
        let bit = result.returnValue;
        
        if(bit>0){
            res.status(200).json({mensaje: "Registro exitoso"});
        } else{
            res.status(200).json({mensaje: "Registro no exitoso"});
        }

    } catch(err) {
        res.status(500).json({Error: 'Error interno del servidor'}); 
    }
});

//Regresa el inventario de un comedor en especifico
app.get('/obtenerInventario/:id', async(req,res) =>{
    try{
        
        let FolioComedor = req.params.id;
        
        const request = new mssql.Request();
        request.input('FolioComedor', mssql.Int, FolioComedor);
        
        //Ejecuta el procedimeinto almacenado
        let result = await request.execute('dbo.PROC_inventarioCom');
        let inventario = result.recordset;
        
        if(inventario){
            res.status(200).json(inventario);
        } else{
            res.status(200).json({mensaje: "No se encontro el comedor"})
        }
    } catch(err){
        res.status(500).json({Error: 'Error interno del servidor'}); 
    }
});


//Envia el reporte de la app a la pagina web
app.post('/enviarReporte', async(req, res) =>{
    
    try{
        
        const { FolioComedor, Fecha, Tipo, Descripcion, Urgencia, Estado} = req.body;
    
        const request = new mssql.Request();
        request.input('FolioComedor', mssql.Int, FolioComedor);
        request.input('Fecha', mssql.Date, Fecha);
        request.input('Tipo', mssql.VarChar(30), Tipo);
        request.input('Descripcion', mssql.VarChar(250), Descripcion);
        request.input('Urgencia', mssql.VarChar(25), Urgencia);
        request.input('Estado', mssql.Int, Estado);
        request.output('Success', mssql.Bit);
        
        //Ejecuta el procedimeinto almacenado
        let result = await request.execute('dbo.PROC_generarReporte');
        let bit = result.returnValue;
     
        if(bit>0){
            res.status(200).json({mensaje: "Registro exitoso"});
        } else{
            res.status(200).json({mensaje: "Registro no exitoso"});
        }   
    
    } catch(err) {
       res.status(500).json({Error: 'Error interno del servidor'});  
    }
    
});

//Modifica el correo de alguien especificamente
app.put('/actualizarCorreo/:id', async(req,res) =>{
    
    try{
        
        let id = req.params.id;
        const {correoActualizado} = req.body;
        
        const request = new mssql.Request();
        request.input('IDUsuario', mssql.Int, id);
        request.input('NuevoCorreo', mssql.VarChar(30), correoActualizado);
        request.output('Success', mssql.Bit);
        
        let result = await request.execute('dbo.PROC_actualizarCorreo');
        let bit = result.returnValue;
        
        if (bit > 0){
            res.status(200).json({mensaje: "Correo actualizado correctamente"});
        } else{
            res.status(200).json({mensaje: "Correo no actualizado"});
        } 
        
    }catch(err){
            res.status(500).json({ error: 'Error interno del servidor' });
    }
} );

// Actualiza el numero de alguien en especifico
app.put('/actualizarNumeroTelefonico/:id', async(req, res) =>{
   
   try{
       
        let id = req.params.id;
        const {numeroActualizado} = req.body; 
        
        const request = new mssql.Request();
        request.input('IDUsuario', mssql.Int, id);
        request.input('NuevoCelular', mssql.VarChar(15), numeroActualizado);
        request.output('Success', mssql.Bit);
        
        let result = await request.execute('dbo.PROC_actualizarCelular');
        let bit = result.returnValue;
        
        if (bit > 0){
            res.status(200).json({mensaje: "Celular actualizado correctamente"});
        } else{
            res.status(200).json({mensaje: "Celular no actualizado"});
        } 
        
   } catch(err){
        res.status(500).json({ error: 'Error interno del servidor' }); 
   }
});


//Actualiza la condicion de alguien en especifico
app.put('/actualizarCondicion/:id', async(req, res) =>{
    
    try{
        
        let id = req.params.id;
        const {condicionActualizada} = req.body; 
        
        
        const request = new mssql.Request();
        request.input('FolioUsuario', mssql.Int, id);
        request.input('NuevaCond', mssql.VarChar(50), condicionActualizada);
        request.output('Success', mssql.Bit);
        
        let result = await request.execute('dbo.PROC_cambioCond');
        let bit = result.returnValue;
        
        if (bit > 0){
            res.status(200).json({mensaje: "Condicion actualizada correctamente"});
        } else{
            res.status(200).json({mensaje: "Condicion no actualizada"});
        } 
        
    } catch(err){
            res.status(500).json({ error: 'Error interno del servidor' }); 
    }
});

// custom 404 page
app.use((req, res) => {
  res.type('text/plain').status(404).send('404 - Not Found');
});

app.listen(port, () => console.log(
  `Express started on http://${ipAddr}:${port}`
  + '\nPress Ctrl-C to terminate.'));
