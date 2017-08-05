package com.maven8919.dfgenerator.draftkings.baseball

data class DKBaseballPlayer(val position : String = "", val name : String = "", val salary : Int = Int.MAX_VALUE,
                            val gameInfo : String = "", val avgPointsPerGame : Double = 0.0, val teamAbbrev : String = "") {
}