inline gen-enum (values...)
    let s = (Scope)
    va-lfold s
        inline (__ next s)
            'bind s (Symbol next) next
        values...

spice check-enum (v escope)
    v as:= string
    try
        '@ (escope as Scope) (Symbol v)
        `true
    else
        `false

locals;
# example usage:
# let DrawMode = (gen-enum "fill" "line")

# run-stage;

# fn _rectangle (...)
#     ;

# sugar check-arg (v T)
#     let name = (tostring T)
#     qq
#         do
#             let valid? = (check-enum [v] [T])
#             static-if (not valid?)
#                 if true
#                     hide-traceback;
#                     error@ ('anchor `[v]) "while checking enum parameter" (.. "unknown " [name] ": " [v])

# run-stage;

# inline rectangle (drawmode x y w h)
#     check-arg drawmode DrawMode
#     _rectangle drawmode x y w h

# rectangle "fi" 0 0 10 10
# ;
