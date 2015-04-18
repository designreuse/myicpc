// Generated by CoffeeScript 1.8.0
var AtmosphereRequest, compileHandlebarsTemplate, convertSecondsToHHMMSS, convertSecondsToMinutes, datePickerOptions, formatContestTime, getSubscribeAddress, isElementVisible, startSubscribe;

AtmosphereRequest = (function() {
  function AtmosphereRequest(url, onMessage) {
    this.url = url;
    this.onMessage = onMessage;
    this.contentType = "application/json";
    this.logLevel = 'debug';
    this.transport = 'websocket';
    this.trackMessageLength = true;
    this.reconnectInterval = 5000;
    this.fallbackTransport = 'long-polling';
    this.onOpen = function(response) {
      return console.log("Atmosphere onOpen: Atmosphere connected using " + response.transport);
    };
    this.onReopen = function(response) {
      return console.log("Atmosphere re-connected using " + response.transport);
    };
    this.onClose = function(response) {
      return console.log('Atmosphere onClose executed');
    };
    this.onError = function(response) {
      return console.log('Atmosphere onError: Sorry, but there is some problem with your socket or the server is down!');
    };
  }

  return AtmosphereRequest;

})();

startSubscribe = function(contextPath, contestCode, channel, processMethod, ngController) {
  var connectedSocket, request, socket;
  socket = $.atmosphere;
  request = new AtmosphereRequest(getSubscribeAddress(contextPath) + contestCode + "/" + channel, function(response) {
    var error, result;
    try {
      result = $.parseJSON(response.responseBody);
      console.log(result);
      return processMethod(result, ngController);
    } catch (_error) {
      error = _error;
      return console.log("An error occurred while parsing the JSON Data: " + response.responseBody + "; Error: " + error);
    }
  });
  return connectedSocket = socket.subscribe(request);
};

getSubscribeAddress = function(contextPath) {
  contextPath = contextPath !== "" ? contextPath + '/' : '/';
  return window.location.protocol + "//" + window.location.hostname + ':' + window.location.port + contextPath + 'pubsub/';
};

compileHandlebarsTemplate = function(id) {
  var elem;
  elem = $("#" + id);
  if (elem.length !== 0) {
    return Handlebars.compile(elem.html());
  }
  return null;
};

datePickerOptions = {
  dateFormat: "yy-mm-dd",
  clockType: 24
};

convertSecondsToMinutes = function(seconds) {
  return Math.floor(seconds / 60);
};

formatContestTime = function(seconds) {
  var divisor_for_minutes, hours, minus, minutes;
  minus = "";
  if (seconds < 0) {
    seconds *= -1;
    minus = "-";
  }
  hours = Math.floor(seconds / (60 * 60));
  divisor_for_minutes = seconds % (60 * 60);
  minutes = Math.floor(divisor_for_minutes / 60);
  if (hours < 10) {
    hours = "0" + hours;
  }
  if (minutes < 10) {
    minutes = "0" + minutes;
  }
  return minus + hours + ":" + minutes;
};


/*
 Convert seconds into hours and minutes and seconds
 @param seconds number of seconds
 @returns time in format HH:MM:SS
 */

convertSecondsToHHMMSS = function(seconds) {
  var hours, minutes, s;
  hours = Math.floor(seconds / 3600);
  seconds %= 3600;
  minutes = Math.floor(seconds / 60);
  seconds %= 60;
  s = "";
  if (hours) {
    if (hours < 10) {
      hours = "0" + hours;
    }
    s = hours + ":";
  }
  if (minutes < 10) {
    minutes = "0" + minutes;
  }
  s += minutes + ":";
  if (seconds < 10) {
    seconds = "0" + seconds;
  }
  return s + seconds;
};

isElementVisible = function(elem, offset) {
  var $e;
  if (offset == null) {
    offset = 0;
  }
  $e = $(elem);
  return $(window).scrollTop() + window.innerHeight + offset > $e.offset().top && $(window).scrollTop() + offset < $e.offset().top + $e.height();
};
