const express = require('express');
const router = express.Router();
const courseController = require('../controllers/courseController')

router.get(`/`, courseController.getCourse);
router.get('/:id', courseController.getCourseById);
router.post('/gallery-videos', courseController.uploadOptions.array('videos', 10), courseController.createCourse),

    module.exports = router;