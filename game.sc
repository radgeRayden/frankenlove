import .love
import .love-object

global itime : f64
global t : f64
global img : love-object.Image

love.load =
    fn ()
        itime = (love.timer.getTime)
        img = (love.graphics.newImage "scopes_logo_big.png")
        ;

love.update =
    fn (dt)
        t = (love.timer.getTime) - itime
        if (t > 1)
            img = (love.graphics.newImage "scopes_logo_big.png")

love.draw =
    fn ()
        love.graphics.draw img
        love.graphics.rectangle "fill" 400 300 (100 + (50 * (sin t))) 100