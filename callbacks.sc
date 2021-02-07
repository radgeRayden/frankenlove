using import FunctionChain

import .lua
import .love
let LCFunction = (pointer (function i32 (mutable@ lua.lua_State)))

fn draw (L)
    using lua
    love.draw;
    0

fn update (L)
    using lua
    assert (lua_isnumber L -1)
    let dt = (lua_tonumber L -1)
    lua_pop L 1
    love.update dt
    0

fn load (L)
    love.load;
    0

do
    let draw update load
    locals;