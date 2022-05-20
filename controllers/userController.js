const { User } = require('../models/userModel');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');

exports.getUsers = async(req, res) => {
    User.find().select('-passwordHash')
    .then(result => {
        if (!result){
            return res.status(404).send({message:'no user found'});
        }
        else{
            res.status(200).send({data: result});
        }
    })
    .catch((err) => {
        res.sendStatus(404);
    });
}

exports.getUsersById = async(req, res) => {
    const user = await User.findById(req.params.id).select('-passwordHash');

    if (!user) {
        res.status(500).json({ message: 'The user with the given ID was not found.' })
    }
    res.status(200).send(user);
}

exports.register = async(req, res) => {
    let user = new User({
        name: req.body.name,
        email: req.body.email,
        passwordHash: bcrypt.hashSync(req.body.password, 10),
        phone: req.body.phone,
        isAdmin: req.body.isAdmin,
    })
    user = await user.save();

    if (!user)
        return res.status(400).send('the user cannot be created!')

    res.send(user);
}

exports.login = async(req, res) => {
    const user = await User.findOne({ email: req.body.email })
    const secret = process.env.secret;
    if (!user) {
        return res.status(400).send('The user not found');
    }

    if (user && bcrypt.compareSync(req.body.password, user.passwordHash)) {
        const token = jwt.sign({
                userId: user.id,
                isAdmin: user.isAdmin
            },
            secret, { expiresIn: '1d' }
        )

        res.status(200).send({ user: user.email, token: token, id: user.id, admin: user.isAdmin}) //
    } else {
        res.status(400).send('password is wrong!');
    }
}

exports.modifier = async(req, res) => {
    if (!req.body){
        return res.status(400).send({message:'Data to update cannot be empty'})
    }

    const id=req.params.id;
    User.findByIdAndUpdate(id, req.body,{useFindAndModify:false}).select('-passwordHash')
    .then(result => {
        if (!result){
            return res.status(404).send({message:'User not found'});
        }
        else{
            res.send(result);
        }
    })
    .catch((err) => {
        res.sendStatus(404);
    });
}

exports.supprimer = async(req, res) => {
    User.findByIdAndDelete(req.params.id).select('-passwordHash')
    .then(result => {
        if (!result){
            return res.status(404).send({message:'User not found'});
        }
        else{
            res.send({message:'user was deleted succesfully'});
        }
    })
    .catch((err) => {
        res.sendStatus(404);
    });
};

exports.recherche = (req, res) => {
    let name = req.body.name;
    let query = {
      "$or": [{"name": {"$regex": name, "$options": "i"}}]
    };
    let output = [];
  
    User.find(query).limit(6).then( usrs => {
        if(usrs && usrs.length && usrs.length > 0) {
            usrs.forEach(user => {
              let obj = {
                  id: user.id,
                  name: user.name
              };
              output.push(obj);
            });
        }
        res.json(output);
    }).catch(err => {
      res.send(err);
    });
  
  };