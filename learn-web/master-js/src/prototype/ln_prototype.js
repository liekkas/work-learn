/**
 * Created by liekkas on 15/7/16.
 */

var person = function (name) {
    this.name = name;
};

person.prototype.getName = function () {
    return this.name;
};

var animal = function () {

};

var dog = function () {

};

animal.price = 2000;
dog.prototype = animal;
