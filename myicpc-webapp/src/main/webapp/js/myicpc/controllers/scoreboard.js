// Generated by CoffeeScript 1.8.0
var scoreboard, updateScoreboard;

scoreboard = angular.module('scoreboard', []);

scoreboard.controller('scoreboardCtrl', function($scope) {
  $scope.teams = {};
  $scope.filterBy = null;
  $scope.filterValue = null;
  $scope.init = function(teams) {
    return $scope.$apply(function() {
      return $scope.teams = teams;
    });
  };
  $scope.updateRank = function(teamId, rank) {
    var obj;
    obj = _.find($scope.teams, function(obj) {
      return obj.teamId === teamId;
    });
    if (obj != null) {
      return obj.teamRank = rank;
    }
  };
  $scope.updateNumSolvedAndTotalTime = function(teamId, numSolved, totalTime) {
    var obj;
    obj = _.find($scope.teams, function(obj) {
      return obj.teamId === teamId;
    });
    if (obj != null) {
      obj.nSolved = numSolved;
      return obj.totalTime = totalTime;
    }
  };
  $scope.updateTeamProblem = function(teamId, problemId, judged, solved, attempts, time, first) {
    var team;
    team = _.find($scope.teams, function(obj) {
      return obj.teamId === teamId;
    });
    if (typeof team !== "undefined" && typeof team.teamProblems !== "undefined") {
      if (typeof team.teamProblems[problemId] === "undefined") {
        team.teamProblems[problemId] = {};
      }
      team.teamProblems[problemId].judged = judged;
      team.teamProblems[problemId].solved = solved;
      team.teamProblems[problemId].attempts = attempts;
      team.teamProblems[problemId].time = time;
      return team.teamProblems[problemId].first = first;
    }
  };
  $scope.filterTeam = function(team) {
    if ($scope.filterBy == null) {
      return true;
    }
    return team[$scope.filterBy] === $scope.filterValue;
  };
  $scope.clearFilter = function() {
    $scope.filterBy = null;
    return $scope.filterValue = null;
  };
  $scope.isFilteredBy = function(filtredBy) {
    return $scope.filterBy === filtredBy;
  };
  $scope.filterByNationality = function(nationality) {
    $scope.filterBy = 'nationality';
    return $scope.filterValue = nationality;
  };
  $scope.filterByUniversity = function(university) {
    $scope.filterBy = 'universityName';
    return $scope.filterValue = university;
  };
  $scope.filterByRegion = function(region) {
    $scope.filterBy = 'regionId';
    return $scope.filterValue = region;
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
  $scope.isPendingSubmission = function(team, problemId) {
    if (team.teamProblems[problemId] == null) {
      return false;
    }
    return team.teamProblems[problemId].judged === false && !team.teamProblems[problemId].solved;
  };
  return $scope.formatTime = function(time) {
    return convertSecondsToMinutes(time);
  };
});

updateScoreboard = function(data, ngController) {
  var colorBg;
  if (ngController == null) {
    ngController = null;
  }
  console.log(data);
  if (data.type === 'submission') {
    if (ngController !== null) {
      colorBg = "#ffff99";
      if (data.judged) {
        colorBg = data.solved ? "#66FF33" : "#FF5C33";
      }
      $(".team_" + data.teamId).effect("highlight", {
        color: colorBg
      }, 3000);
      return ngController.$apply(function() {
        var key;
        for (key in data.teams) {
          ngController.updateRank(data.teams[key].teamId, data.teams[key].teamRank);
        }
        if (data.solved) {
          ngController.updateNumSolvedAndTotalTime(data.teamId, data.numSolved, data.total);
        }
        return ngController.updateTeamProblem(data.teamId, data.problemId, data.judged, data.solved, data.attempts, data.time, data.first);
      });
    }
  }
};