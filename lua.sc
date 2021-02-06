load-library "libluajit-5.1.so"
vvv bind lua
do
    let header =
        include
            """"#include <luajit-2.0/lua.h>
                #include <luajit-2.0/lualib.h>
                #include <luajit-2.0/lauxlib.h>
                #include <luajit-2.0/luajit.h>
    using header.extern filter "lua"
    using header.typedef filter "lua"
    using header.define filter "LUA"

    # macros and constants
    let LUA_REGISTRYINDEX = -10000
    let LUA_ENVIRONINDEX  = -10001
    let LUA_GLOBALSINDEX  = -10002
    let LUA_TNONE = -1

    inline lua_upvalueindex(i)
        LUA_GLOBALSINDEX - i

    inline lua_pop (L n)
        lua_settop L (-n - 1)

    inline lua_newtable (L)
        lua_createtable L 0 0

    inline lua_pushcfunction (L f)
        lua_pushcclosure L f 0

    inline lua_setglobal (L s)
        lua_setfield L LUA_GLOBALSINDEX s

    inline lua_getglobal (L s)
        lua_getfield L LUA_GLOBALSINDEX s

    inline lua_register(L n f)
        lua_pushcfunction L f
        lua_setglobal L n

    inline lua_strlen (L i)
        lua_objlen L i

    inline lua_isfunction (L n)
        (lua_type L n) == LUA_TFUNCTION

    inline lua_istable (L n)
        (lua_type L n) == LUA_TTABLE

    inline lua_islightuserdata (L n)
        (lua_type L n) == LUA_TLIGHTUSERDATA

    inline lua_isnil (L n)
        (lua_type L n) == LUA_TNIL

    inline lua_isboolean (L n)
        (lua_type L n) == LUA_TBOOLEAN

    inline lua_isthread (L n)
        (lua_type L n) == LUA_TTHREAD

    inline lua_isnone (L n)
        (lua_type L n) == LUA_TNONE

    inline lua_isnoneornil (L n)
        (lua_type L n) <= 0

    inline lua_pushliteral (L s)
        lua_pushlstring L (s as rawstring) (countof s)

    inline lua_tostring (L i)
        lua_tolstring L i null

    locals;

lua