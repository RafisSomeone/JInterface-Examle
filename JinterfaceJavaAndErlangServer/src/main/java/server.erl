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

  register(server4,spawn(?MODULE, reply, [])).


reply() ->
  receive
    {Pid, question} -> Pid ! answer, reply();
    {Pid,_} -> Pid ! chybaTy, reply()
  end.
