// Generated by CoffeeScript 1.8.0
(function() {
  var scoreboard;

  scoreboard = angular.module('scoreboard', []);

  scoreboard.controller('scoreboardCtrl', function($scope) {
    $scope.teams = {};
    $scope.message = "dasdsa";
    $scope.init = function(teams) {
      return $scope.$apply(function() {
        return $scope.teams = teams;
      });
    };
    $scope.isFirstSolvedSubmission = function(team, problemId) {
      if (team.teamProblems[problemId] == null) {
        return false;
      }
      return team.teamProblems[problemId].judged === true && team.teamProblems[problemId].solved === true && team.teamProblems[problemId].first === true;
    };
    $scope.isSolvedSubmission = function(team, problemId) {
      if (team.teamProblems[problemId] == null) {
        return false;
      }
      return team.teamProblems[problemId].judged === true && team.teamProblems[problemId].solved === true && !team.teamProblems[problemId].first;
    };
    $scope.isFailedSubmission = function(team, problemId) {
      if (team.teamProblems[problemId] == null) {
        return false;
      }
      return team.teamProblems[problemId].judged === true && team.teamProblems[problemId].solved === false;
    };
    return $scope.isPendingSubmission = function(team, problemId) {
      if (team.teamProblems[problemId] == null) {
        return false;
      }
      return team.teamProblems[problemId].judged === false && !team.teamProblems[problemId].solved;
    };
  });

}).call(this);
