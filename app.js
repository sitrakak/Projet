const express = require('express');
const app = express();
const mongoose = require('mongoose');

const bodyParser = require('body-parser');
const morgan = require('morgan');
const cors = require('cors');
require('dotenv/config');

const authJwt = require('./helpers/jwt');
const errorHandler = require('./helpers/error-handler');


app.use(cors());
app.options('*', cors())

//middleware
app.use(bodyParser.json());
app.use(morgan('tiny'));
app.use(authJwt());
app.use(errorHandler);


//Routes
const usersRoutes = require('./routes/userRoute');
const levelsRoutes = require('./routes/levelRoute');
const coursesRoutes = require('./routes/courseRoute');

const api = process.env.API_URL;

app.use(`${api}/users`, usersRoutes);
app.use(`${api}/levels`, levelsRoutes);
app.use(`${api}/courses`, coursesRoutes);

//Database
mongoose.connect(process.env.CONNECTION_STRING, {
        useNewUrlParser: true,
        useUnifiedTopology: true,
        dbName: 'CoolLearning'
    })
    .then(() => {
        console.log('Database Connection is ready...')
    })
    .catch((err) => {
        console.log(err);
    })

//Server
app.listen(process.env.PORT || 3000, () => {

    console.log('server is running http://localhost:3000');
})