const { User } = require('../models/userModel');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');

exports.getUsers = async(req, res) => {
    const userList = await User.find().select('-passwordHash');

    if (!userList) {
        res.status(500).json({ success: false })
    }
    res.send(userList);
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

        res.status(200).send({ user: user.email, token: token }) //
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
        console.log(err);
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
        console.log(err);
    });
};