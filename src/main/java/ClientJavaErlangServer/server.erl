%%%-------------------------------------------------------------------
%%% @author rafal
%%% @copyright (C) 2019, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 29. maj 2019 17:23
%%%-------------------------------------------------------------------
-module(server).
-author("rafal").

%% API
-export([start/0, reply/0]).
start() ->

  register(server5,spawn(?MODULE, reply, [])).



getday("Wed") ->
  "wednesday";

getday("Sun") ->
  "sunday";

getday("Mon") ->
  "monady";

getday("Tue") ->
  "tuesday";

getday("Thr") ->
  "thursday";

getday("Fri") ->
  "friday";

getday("Sat") ->
  "saturday".

reply() ->
  receive
    {Name,ClientNode,Date} ->
      [Day| _] = string:lexemes(Date," "),
      WeekDay = getday(Day),
      Communicate = "Today is " ++ WeekDay,
      {Name,ClientNode} ! Communicate,
      reply()

  end.
