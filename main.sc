import .lua
import .love

let program =
    """"arg = {...}
        arg[-1] = "love"

        require ("love")
        require ("love.filesystem")
        require ("love.window")
        require ("love.graphics")

        local c = coroutine.create(require("boot"))
        local og = love.arg.options.game
        og.set = true
        og.arg = {"."}

        function frame ()
            if coroutine.status(c) == "dead" then
                os.exit()
            end
            coroutine.resume(c)
        end

run-stage;

import .callbacks
import .game

fn main (argc argv)
    using lua
    local L : (mutable@ lua_State)
    L = (luaL_newstate)
    love.L = L
    assert (L != null) "luajit failed to initialize"

    luaL_openlibs L
    let status = (luaL_loadstring L (program as rawstring))
    let ret = (lua_pcall L 0 0 0)
    if (ret != 0)
        print (string (lua_tolstring L -1 null))
        return 1

    lua_getglobal L "love"
    lua_pushstring L "draw"
    lua_pushcfunction L callbacks.draw
    lua_settable L -3
    lua_pushstring L "update"
    lua_pushcfunction L callbacks.update
    lua_settable L -3

    while true
        lua_getglobal L "frame"
        lua_call L 0 0

    lua_close L
    0

main (launch-args)