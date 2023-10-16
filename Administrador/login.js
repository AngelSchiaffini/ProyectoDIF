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


app.post('/verificar-credenciales', async (req, res) => {
    const { usuario, contrasena } = req.body;
    const query = `SELECT * FROM Administrador WHERE nombre = '${usuario}' AND ContrasenaAdmin = '${contrasena}'`;
    
    try {
      const result = await mssql.query(query);
      
      if (result.recordset.length > 0) {
        // Las credenciales son correctas
        res.redirect('/moduloadmin.html'); // Redirige a la página siguiente
      } else {
        // Las credenciales son incorrectas
        res.status(401).send('Credenciales incorrectas');
      }
    } catch (err) {
      console.error(err);
      res.status(500).send('Error en la base de datos');
    }
  });


// custom 404 page
app.use((req, res) => {
    res.type('text/plain').status(404).send('404 - Not Found');
  });
  
  app.listen(port, () => console.log(
    `Express started on http://${ipAddr}:${port}`
    + '\nPress Ctrl-C to terminate.'));
  