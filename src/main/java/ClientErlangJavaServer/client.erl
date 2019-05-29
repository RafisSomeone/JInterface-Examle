%%%-------------------------------------------------------------------
%%% @author rafal
%%% @copyright (C) 2019, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 27. maj 2019 22:46
%%%-------------------------------------------------------------------
-module(client).

-export([count/0]).

count() ->

  {counterserver, 'server@rafal-GL553VD'} ! {self(), "count"},

  receive

    {ok, Counter} ->

      io:format("Counter is at value: ~p~n", [Counter])

  end.