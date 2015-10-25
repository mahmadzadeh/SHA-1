package com


object SHA1Variables {

    def apply(h0: Int = 0x67452301,
              h1: Int = 0xEFCDAB89,
              h2: Int = 0x98BADCFE,
              h3: Int = 0x10325476,
              h4: Int = 0xC3D2E1F0) : SHA1Variables = new SHA1Variables(h0, h1, h2,h3, h4)
}

class SHA1Variables(val h0: Int, val h1: Int , val h2: Int , val h3: Int , val h4:Int )
