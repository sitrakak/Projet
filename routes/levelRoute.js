const express = require('express');
const router = express.Router();
const levelController = require('../controllers/levelController');


router.get(`/`, levelController.getLevel);
router.get('/:id', levelController.getLevelById);
router.post('/addLevel', levelController.addLevel);



module.exports = router;