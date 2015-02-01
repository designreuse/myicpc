// Generated by CoffeeScript 1.8.0
var insightApp;

insightApp = angular.module('insight', ['nvd3ChartDirectives']);

insightApp.controller('problemInsightCtrl', function($scope) {
  $scope.problems = [];
  $scope.init = function(data) {
    return $scope.$apply(function() {
      return $scope.problems = data;
    });
  };
  $scope.xFunction = function() {
    return function(d) {
      return d.key;
    };
  };
  $scope.yFunction = function() {
    return function(d) {
      return d.value;
    };
  };
  return $scope.areaColor = function() {
    return function(d, i) {
      return d.data.color;
    };
  };
});