import .love

global t : f64

love.update =
    fn (dt)
        t = (love.timer.getTime)

love.draw =
    fn ()
        love.graphics.rectangle "fill" 400 300 (100 + (50 * (sin t))) 100