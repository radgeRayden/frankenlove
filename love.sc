import .lua
import .love-object
let L = ((import .state) . L)

global draw : (pointer (function void))
draw =
    fn ()
        ;
global update : (pointer (function void f64))
update =
    fn (dt)
        ;

global _load : (pointer (function void))
_load =
    fn ()
        ;

let LCFunction = (pointer (function i32 (mutable@ lua.lua_State)))
vvv bind graphics
do
    fn newImage (filename)
        using lua

        global id : i32
        if (id == 0)
            let stackpos = (lua_gettop L)
            lua_getglobal L "love"
            lua_getfield L -1 "graphics"
            lua_getfield L -1 "newImage"

            id = (luaL_ref L LUA_REGISTRYINDEX)
            lua_pop L ((lua_gettop L) - stackpos)

        lua_rawgeti L LUA_REGISTRYINDEX id
        lua_pushstring L (filename as rawstring)
        lua_call L 1 1
        let handle = (luaL_ref L LUA_REGISTRYINDEX)
        love-object.Image handle

    fn draw (image)
        using lua
        global id : i32
        if (id == 0)
            let stackpos = (lua_gettop L)
            lua_getglobal L "love"
            lua_getfield L -1 "graphics"
            lua_getfield L -1 "draw"

            id = (luaL_ref L LUA_REGISTRYINDEX)
            lua_pop L ((lua_gettop L) - stackpos)

        lua_rawgeti L LUA_REGISTRYINDEX id
        lua_rawgeti L LUA_REGISTRYINDEX image
        lua_call L 1 0
        ;

    fn rectangle (mode x y w h)
        using lua

        global id : i32
        if (id == 0)
            let stackpos = (lua_gettop L)
            lua_getglobal L "love"
            lua_getfield L -1 "graphics"
            lua_getfield L -1 "rectangle"

            id = (luaL_ref L LUA_REGISTRYINDEX)
            lua_pop L ((lua_gettop L) - stackpos)

        lua_rawgeti L LUA_REGISTRYINDEX id
        lua_pushstring L (mode as rawstring)
        lua_pushnumber L (x as f64)
        lua_pushnumber L (y as f64)
        lua_pushnumber L (w as f64)
        lua_pushnumber L (h as f64)
        lua_call L 5 0
        ;
    locals;

vvv bind timer
do
    fn getTime ()
        using lua

        global id : i32
        if (id == 0)
            let stackpos = (lua_gettop L)
            lua_getglobal L "love"
            lua_getfield L -1 "timer"
            lua_getfield L -1 "getTime"

            id = (luaL_ref L LUA_REGISTRYINDEX)
            lua_pop L ((lua_gettop L) - stackpos)

        lua_rawgeti L LUA_REGISTRYINDEX id
        lua_call L 0 1
        let result = (lua_tonumber L -1)
        lua_pop L 1
        result
    locals;

do
    let
        L
        update
        draw
        graphics
        timer

    let load = _load
    locals;
