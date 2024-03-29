const { Course } = require('../models/courseModel');
const { Level } = require('../models/levelModel');
const express = require('express');
const router = express.Router();
const mongoose = require('mongoose');
const multer = require('multer');

const FILE_TYPE_MAP = {
    'video/mp4': 'mp4',
    'video/x-msvideo' : 'avi'
};


const storage = multer.diskStorage({
    destination: function(req, file, cb) {
        const isValid = FILE_TYPE_MAP[file.mimetype];
        let uploadError = new Error('invalid type');

        if (isValid) {
            uploadError = null;
        }
        cb(uploadError, 'public/uploads');
    },
    filename: function(req, file, cb) {
        const fileName = file.originalname.split(' ').join('-');
        const extension = FILE_TYPE_MAP[file.mimetype];
        cb(null, `${fileName}-${Date.now()}.${extension}`);
    }
});

exports.uploadOptions = multer({ storage: storage });


exports.getCourse = async(req, res) => {
    let filter = {};
    if (req.query.levels) {
        filter = { level: req.query.levels.split(',') };
    }

    Course.find(filter).populate('level')
    .then(result => {
        if (!result){
            return res.status(404).send({message:'no course found'});
        }
        else{
            res.status(200).send({data: result});
        }
    })
    .catch((err) => {
        res.sendStatus(404);
    });
}

exports.getCourseById = async(req, res) => {
    const course = await Course.findById(req.params.id).populate('level');

    if (!course) {
        res.status(500).json({ success: false })
    }
    res.send(course);
}

exports.createCourse = async(req, res) => {
    const level = await Level.findById(req.body.level);
    if (!level) return res.status(400).send('Invalid level');


    const files = req.files;
    let videosPaths = [];
    const basePath = `${req.protocol}://${req.get('host')}/public/uploads/`;

    if (files) {
        files.map((file) => {
            videosPaths.push(`${basePath}${file.filename}`);
        });
    }


    let course = new Course({
        description: req.body.description,
        richDescription: req.body.richDescription,
        videos: videosPaths,
        level: req.body.level
    })

    course = await course.save();

    if (!course)
        return res.status(500).send('The course cannot be created')

    res.send(course);
}

exports.recherche = (req, res) => {
    let description = req.body.description;
    let query = {
      "$or": [{"description": {"$regex": description, "$options": "i"}}]
    };
  
    Course.find(query).limit(6)
    .then(result => {
        if (!result){
            return res.status(404).send({message:'no course found'});
        }
        else{
            res.status(200).send({data: result});
        }
    })
    .catch((err) => {
        res.sendStatus(404);
    });
  
  };
