// Generated by CoffeeScript 1.8.0
var scoreboard, updateScoreboard,
  __indexOf = [].indexOf || function(item) { for (var i = 0, l = this.length; i < l; i++) { if (i in this && this[i] === item) return i; } return -1; };

scoreboard = angular.module('scoreboard', []);

scoreboard.controller('scoreboardCtrl', function($scope) {
  $scope.teams = [];
  $scope.filterBy = null;
  $scope.filterValue = null;
  $scope.followedTeamIds = getCookieAsIntArray('followedTeams');
  $scope.init = function(teams) {
    return $scope.$apply(function() {
      var i, _i, _ref, _ref1;
      for (i = _i = 0, _ref = teams.length - 1; _i <= _ref; i = _i += 1) {
        teams[i].followed = (_ref1 = teams[i].teamId, __indexOf.call($scope.followedTeamIds, _ref1) >= 0);
      }
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
  $scope.toogleTeamProblems = function(team) {
    var _ref;
    if (team.showProblems == null) {
      team.showProblems = false;
    }
    return team.showProblems = (_ref = team.showProblems === false) != null ? _ref : {
      "true": false
    };
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
  $scope.formatTime = function(time) {
    return convertSecondsToMinutes(time);
  };
  return $scope.toggleFollowTeam = function(team, cookiePath) {
    var teamId;
    teamId = team.teamId;
    if (__indexOf.call($scope.followedTeamIds, teamId) >= 0) {
      removeIdFromCookieArray('followedTeams', teamId, cookiePath);
      $scope.followedTeamIds = _.filter($scope.followedTeamIds, function(id) {
        return id !== teamId;
      });
      team.followed = false;
    } else {
      appendIdToCookieArray('followedTeams', teamId, cookiePath);
      $scope.followedTeamIds.push(teamId);
      team.followed = true;
    }
    return false;
  };
});

updateScoreboard = function(data, ngController) {
  var colorBg;
  if (ngController == null) {
    ngController = null;
  }
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
        var i, _i, _ref;
        if (data.teams.length > 0) {
          for (i = _i = 0, _ref = data.teams.length - 1; _i <= _ref; i = _i += 1) {
            ngController.updateRank(data.teams[i][0], data.teams[i][1]);
          }
        }
        if (data.solved) {
          ngController.updateNumSolvedAndTotalTime(data.teamId, data.numSolved, data.total);
        }
        return ngController.updateTeamProblem(data.teamId, data.problemId, data.judged, data.solved, data.attempts, data.time, data.first);
      });
    }
  } else if (data.type === 'refresh') {
    return location.reload();
  }
};
