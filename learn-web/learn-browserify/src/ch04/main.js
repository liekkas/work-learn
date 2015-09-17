/**
 * Created by liekkas on 15/9/9.
 */

var _ = require('lodash');
var my = require('./utils.js')();

 var  names = ['Bruce Wayne', 'Wally West', 'John Jones', 'Kyle Rayner', 'Arthur Curry', 'Clark Kent'],
    otherNames = ['Barry Allen', 'Hal Jordan', 'Kara Kent', 'Diana Prince', 'Ray Palmer', 'Oliver Queen'];

_.each([names, otherNames], function(nameGroup) {
    findSuperman(nameGroup);
});

function findSuperman(values) {
    _.find(values, function(name) {
        if (name === 'Clark Kent') {
            console.log('It\'s Superman!');
        } else {
            console.log('... No superman!');
        }
    });
}

var n = my.add(3,5);
console.log(n);