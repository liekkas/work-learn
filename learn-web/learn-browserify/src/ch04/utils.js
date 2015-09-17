/**
 * Created by liekkas on 15/9/9.
 */

var _ = require('lodash');
function add(n,m){
    return _.add(n+m);
}

module.exports = function (vars) {
    return {
        add: add
    }
};