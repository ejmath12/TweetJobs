
case class HBaseConfig(
                        col0: String,
                        col2: String)

object HBaseConfig
{
    def apply(i: Int, t: String): HBaseConfig = {
        val s = s"""row${"%03d".format(i)}"""
        HBaseConfig(s,
            s)
    }
}

