const { Level } = require('../models/levelModel');
const express = require('express');
const router = express.Router();
const mongoose = require('mongoose');

exports.getLevel = async(req, res) => {
    Level.find()
    .then(result => {
        if (!result){
            return res.status(404).send({message:'no level found'});
        }
        else{
            res.status(200).send({data: result});
        }
    })
    .catch((err) => {
        res.sendStatus(404);
    });
}

exports.getLevelById = async(req, res) => {
    const level = await Level.findById(req.params.id);

    if (!level) {
        res.status(500).json({ message: 'The level with the given ID was not found.' })
    }
    res.status(200).send(level);
}


exports.addLevel = async(req, res) => {
    let level = new Level({
        level: req.body.level,
        icon: req.body.icon,
    })
    level = await level.save();

    if (!level)
        return res.status(400).send('the level cannot be created!')

    res.send(level);
}