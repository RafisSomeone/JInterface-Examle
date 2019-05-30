%%%-------------------------------------------------------------------
%%% @author rafal
%%% @copyright (C) 2019, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 19. kwi 2019 22:58
%%%-------------------------------------------------------------------
-module(pollution_server).
-author("rafal").
%% API
-export([start/0, stop/0,crash/0, init/0,addStation/2, addValue/4, removeValue/3, getOneValue/3, getStationMean/2, getDailyMean/2, getOverLimit/3, print/0,getNorms/0]).


start() ->
  register(pollutionServer, spawn_link(pollution_server, init, [])).

init() ->
  loop(pollution:createMonitor()).

stop() ->
  pollutionServer ! {request, self(), stop},
  receive
    {reply, Reply} -> Reply
  end.

loop(Monitor) ->
  receive

    {request, Name,Node, getNorms,_} ->
      {Name,Node} ! {reply, pollution:createNorms()},
      loop(Monitor);

    {request, Name,Node, addStation, {Name1, Location}} ->
      Result = pollution:addStation(Name1, Location,Monitor),
      process(Result, Name,Node, Monitor);

    {request, Name,Node, addValue, {Searched,Date,Type,Value}} ->
      Result = pollution:addValue(Searched,Date,Type,Value,Monitor),
      process(Result, Name,Node, Monitor);

    {request, Name,Node, removeValue, {Searched,Date,Type}} ->
      Result = pollution:removeValue(Monitor,Searched,Date,Type),
      process(Result, Name,Node, Monitor);

    {request,  Name,Node, getOneValue, {Searched,Type,Date}} ->
      {Name,Node} ! {reply, pollution:getOneValue(Monitor,Searched,Type,Date)},
      loop(Monitor);


    {request,  Name,Node, getStationMean, {Searched,Type}} ->
      {Name,Node} ! {reply, pollution:getStationMean(Monitor,Searched,Type)},
      loop(Monitor);

    {request,  Name,Node, getDailyMean, {Date,Type}} ->
      {Name,Node} ! {reply, pollution:getDailyMean(Monitor,Date,Type)},
      loop(Monitor);

    {request,  Name,Node, getOverLimit, {Hour,Type,Norms}} ->
      {Name,Node} ! {reply, pollution:getOverLimit(Monitor,Hour,Type,Norms)},
      loop(Monitor);

    {request,  Name,Node, print} ->

      {Name,Node} ! {reply, Monitor},
      loop(Monitor);

    {request,  Name,Node, stop} ->
      {Name,Node} ! {reply, ok};
    {request,  Name,Node ,crash} ->
      {Name,Node} ! {reply,1/0},
      loop(Monitor)
  end.

process({error, Text}, Name,Node, Monitor) ->
  {Name,Node} ! {reply, Text},
  loop(Monitor);
process(Result, Name,Node, _) ->
  {Name,Node} ! {reply, ok},
  loop(Result).

call(Command, Args) ->
  pollutionServer ! {request, self(), Command, Args},
  receive
    {reply, Reply} -> Reply
  end.


print() ->
  pollutionServer ! {request, self(), print},
  receive
    {reply, Reply} -> Reply
  end.



addStation(Name, Location) -> call(addStation, {Name, Location}).
addValue(Searched,Date,Type,Value) -> call(addValue, {Searched,Date,Type,Value}).
removeValue(Searched,Date,Type) -> call(removeValue, {Searched,Date,Type}).
getOneValue(Searched,Type,Date) -> call(getOneValue, {Searched,Type,Date}).
getStationMean(Searched,Type) -> call(getStationMean, {Searched,Type}).
getDailyMean(Date,Type) -> call(getDailyMean, {Date,Type}).
getOverLimit(Hour,Type,Norms) -> call(getOverLimit, {Hour,Type,Norms}).
getNorms() -> call(getNorms,0).
crash() -> pollutionServer ! {request,self(),crash}.