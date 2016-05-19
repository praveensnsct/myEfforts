'use strict';

angular.module('petShopApp', ['angularPayments', 'mm.foundation', 'ngAnimate', 'angularSpinner', 'ngRoute'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/welcome', {
            controller: 'PetShopCtrl',
            templateUrl: 'views/products.htm'
        }).when('/login', {
            controller: 'loginController',
            templateUrl: 'views/login.htm'
        }).otherwise({
            redirectTo: '/login'
        })
    }])

    .factory('user', function ($http) {
        return {
            checkUser: function (user) {
                return $http.post('http://localhost:8080/user/login', user, {}).success(function (response) {
                    return response;
                }).error(function (err) {
                    return err;
                });
            }
        }
    })

    .controller('loginController', ['$scope', 'user','$location', function ($scope, user, $location) {
        $scope.username = "admin";
        $scope.password = "admin";
        $scope.validateLogin = function () {
            user.checkUser({
                userName: $scope.username,
                password: $scope.password
            }).then(function(res){
                console.log(res.data);
                if(!res.data.err)
                $location.path('/welcome');
            })
        }

    }])

    .controller('PetShopCtrl', function ($scope, $http, $modal) {
        $scope.cart = [];
        var url = "http://localhost:8080/cart/getItems";
        // Load products from server
        $http.get(url).success(function (response) {
            $scope.products = response;
        });

        $scope.addToCart = function (product) {
            var found = false;
            $scope.cart.forEach(function (item) {
                if (item.id === product.id) {
                    item.quantity++;
                    found = true;
                }
            });
            if (!found) {
                $scope.cart.push(angular.extend({quantity: 1}, product));
            }
        };

        $scope.removeFromCart = function (product, index) {
            if (product.quantity > 1) {
                $scope.cart[index].quantity--;
            } else {
                $scope.cart.splice(index, 1);
            }
        };


        $scope.getCartPrice = function () {
            var total = 0;
            $scope.cart.forEach(function (product) {
                total += product.price * product.quantity;
            });
            return total;
        };

        $scope.checkout = function () {
            $modal.open({
                templateUrl: 'views/checkout.html',
                controller: 'CheckoutCtrl',
                resolve: {
                    totalAmount: $scope.getCartPrice
                }
            });
        };
    })

    .controller('CheckoutCtrl', function ($scope, totalAmount) {
        $scope.totalAmount = totalAmount;

        $scope.onSubmit = function () {
            $scope.processing = true;
        };

        $scope.stripeCallback = function (code, result) {
            $scope.processing = false;
            $scope.hideAlerts();
            if (result.error) {
                $scope.stripeError = result.error.message;
            } else {
                $scope.stripeToken = result.id;
            }
        };

        $scope.hideAlerts = function () {
            $scope.stripeError = null;
            $scope.stripeToken = null;
        };
    });
