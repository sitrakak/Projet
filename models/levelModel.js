const mongoose = require('mongoose');

const LevelSchema = mongoose.Schema({
    level: {
        type: String,
        required: true,
    },
    icon: {
        type: String,
    },
})


LevelSchema.virtual('id').get(function() {
    return this._id.toHexString();
});


LevelSchema.set('toJSON', {
    virtuals: true,
});

exports.Level = mongoose.model('Level', LevelSchema);