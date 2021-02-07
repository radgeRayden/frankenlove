load-library "liblove-11.3.so"
run-stage;

import .lua
import .love
import .state

import .callbacks
import .game

fn love-handoff (L coro)
    using lua
    lua_rawgeti L LUA_REGISTRYINDEX coro
    let stackpos = (lua_gettop L)
    let result = ((lua_resume L 0) == LUA_YIELD)
    lua_pop L ((lua_gettop L) - stackpos)
    result

fn main (argc argv)
    using lua
    local L : (mutable@ lua_State)

    L = (luaL_newstate)
    state.L = L
    assert (L != null) "luajit failed to initialize"

    luaL_openlibs L

    # code 'inspired' by love/src/love.cpp runlove
    let luaopen_love =
        extern 'luaopen_love (function i32 (mutable@ lua_State))
    lua_getglobal L "package"
    lua_getfield L -1 "preload"
    lua_pushcfunction L luaopen_love
    lua_setfield L -2 "love"
    lua_pop L 2

    # populate the global arg table
    do
        lua_newtable L

        if (argc > 0)
            lua_pushstring L (argv @ 0)
            lua_rawseti L -2 -2

        lua_pushstring L "custom boot.lua"
        lua_rawseti L -2 -1

        # inject a fake "." argument to make boot.lua happy
        lua_pushliteral L "."
        lua_rawseti L -2 1

        lua_setglobal L "arg"


    # require "love"
    lua_getglobal L "require"
    lua_pushstring L "love"
    lua_call L 1 1

    # TODO: change this to an embedded boot file
    lua_getglobal L "require"
    lua_pushstring L "boot"
    lua_call L 1 1

    # Turn the returned boot function into a coroutine and call it until done.
    lua_newthread L
    lua_pushvalue L -2
    let love-coroutine = (luaL_ref L LUA_REGISTRYINDEX)

    # set callbacks
    lua_getglobal L "love"
    lua_pushstring L "draw"
    lua_pushcfunction L callbacks.draw
    lua_settable L -3
    lua_pushstring L "update"
    lua_pushcfunction L callbacks.update
    lua_settable L -3
    lua_pushstring L "load"
    lua_pushcfunction L callbacks.load
    lua_settable L -3
    print (lua_gettop L)

    # TODO: implement restarting
    while (love-handoff L love-coroutine)
        ;

    lua_close L
    0

main (launch-args)