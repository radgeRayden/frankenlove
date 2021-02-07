import .lua
let L = ((import .state) . L)

type Object < integer
    inline __typecall (cls handle)
        let handle =
            (not (none? handle)) and handle or 0
        bitcast handle cls

    inline __drop (self)
        if (self == 0)
            # invalid / default handle
            return;

        using lua
        # retrieve reference
        lua_rawgeti L LUA_REGISTRYINDEX self
        lua_getfield L -1 "release"
        lua_pushvalue L -2
        lua_call L 1 0

        luaL_unref L LUA_REGISTRYINDEX self
        ;

    inline __tobool (self)
        self != 0

type Texture < Object
type Drawable < Texture

unlet Image
type Image < Object :: i32

locals;