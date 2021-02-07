local api = require "love-api.love_api"
local ext = require "love-api.extra"
local data = ext(api)

local fh = io.open ("api/enums.sc", "w")
fh:write("import ..love-enum\n")
for k,v in ipairs(data.enums) do
  fh:write("let ", v.name, " =\n")
  fh:write("    love-enum.gen-enum\n")
  for k,v in ipairs(v.constants) do
    local name = v.name == '"' and [[\"]] or v.name
    name = name == "\\" and [[\\]] or name
    print (name)
    fh:write("        \"", name,"\"\n")
  end
end
fh:close()
