const mongoose = require('mongoose');

const CourseSchema = mongoose.Schema({
    description: {
        type: String,
        required: true
    },
    richDescription: {
        type: String,
        default: ''
    },
    videos: [{
        type: String
    }],
    level: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Level',
        required: true
    },
    dateCreated: {
        type: Date,
        default: Date.now,
    },
})

CourseSchema.virtual('id').get(function() {
    return this._id.toHexString();
});

CourseSchema.set('toJSON', {
    virtuals: true,
});


exports.Course = mongoose.model('Course', CourseSchema);