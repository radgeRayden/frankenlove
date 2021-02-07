import .lua
global L : (mutable@ lua.lua_State)

do
    let L
    locals;