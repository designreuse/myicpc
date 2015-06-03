class AtmosphereRequest
  constructor: (@url, @onMessage) ->
    @contentType = "application/json"
    @logLevel = 'debug'
    @transport = 'sse' #websocket or sse
    @trackMessageLength = true
    @reconnectInterval = 5000
    @fallbackTransport = 'long-polling'

    @onOpen = (response) ->
      console.log("Atmosphere onOpen: Atmosphere connected using #{response.transport}");

    @onReopen = (response) ->
      console.log("Atmosphere re-connected using #{response.transport}");

    @onClose = (response) ->
      console.log('Atmosphere onClose executed');

    @onError = (response) ->
      console.log('Atmosphere onError: Sorry, but there is some problem with your socket or the server is down!');

startSubscribe = (contextPath, contestCode, channel, processMethod, ngController) ->
  socket = $.atmosphere
  request = new AtmosphereRequest(getSubscribeAddress(contextPath) + contestCode + "/" + channel, (response) ->
    try
      result = $.parseJSON(response.responseBody);
      console.log result
      processMethod(result, ngController)
    catch error
      console.log("An error occurred while parsing the JSON Data: #{response.responseBody}; Error: #{error}");
  )
  connectedSocket = socket.subscribe(request)

getSubscribeAddress = (contextPath) ->
  contextPath = if contextPath != "" then contextPath + '/' else '/'
  window.location.protocol + "//" + window.location.hostname + ':' + window.location.port + contextPath + 'pubsub/'

compileHandlebarsTemplate = (id) ->
  elem = $("#" + id);
  if elem.length != 0
    return Handlebars.compile(elem.html())
  return null

datePickerOptions = {
  dateFormat: "yy-mm-dd",
  clockType: 24
}

convertSecondsToMinutes = (seconds) ->
  seconds // 60

formatContestTime = (seconds) ->
  minus = ""
  if (seconds < 0)
    seconds *= -1
    minus = "-"

  hours = seconds // (60 * 60)
  divisor_for_minutes = seconds % (60 * 60)
  minutes = divisor_for_minutes // 60

  hours = "0" + hours if hours < 10
  minutes = "0" + minutes if minutes < 10

  return minus + hours + ":" + minutes;

###
 Convert seconds into hours and minutes and seconds
 @param seconds number of seconds
 @returns time in format HH:MM:SS
###
convertSecondsToHHMMSS = (seconds) ->
  hours = Math.floor(seconds / 3600)
  seconds %= 3600
  minutes = Math.floor(seconds / 60)
  seconds %= 60
  s = ""
  if (hours)
    if (hours < 10)
      hours = "0" + hours
    s = hours + ":"

  if (minutes < 10)
    minutes = "0"+minutes
  s += minutes+":"
  if (seconds < 10)
    seconds = "0"+seconds
  return s + seconds

isElementVisible = (elem, offset = 0) ->
  $e = $(elem);
  win = $(window)
  win.scrollTop()+window.innerHeight + offset >$e.offset().top && win.scrollTop() + offset <$e.offset().top+$e.height();

escapePlusSign = (string) ->
  string.replace(new RegExp( "\\+", "g" ), "%2B")

setFixedSubmenuHeight = () ->
  footerHeight = $("#footer").outerHeight(true)
  viewportHeight = $(window).height()
  pageTitle = $('#pageTitle')
  pageTitleBottom = pageTitle.position().top + pageTitle.outerHeight() + pageTitle.offset().top

  height = viewportHeight - pageTitleBottom - footerHeight
  bodyHeight = $("#fixedContent").outerHeight()
  fixedSidebar = $("#fixedSidebar");
  fixedSidebar.height(Math.max(height, bodyHeight))