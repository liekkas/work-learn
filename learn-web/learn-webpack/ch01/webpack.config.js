/**
 * Created by liekkas on 15/9/17.
 */
var path = require('path');

module.exports = {
    entry: ['webpack/hot/dev-server',path.resolve(__dirname,'./app/main.js')],
    output:{
        path: path.resolve(__dirname,'./build'),
        filename: 'bundle.js'
    }
};