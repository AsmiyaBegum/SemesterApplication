package com.ab.semesterapplication.model

data class Subject(
    var id : Int,
    var resourceId : Int,
    var subjectName : String
)

data class Exams(
    var semester : String,
    var score : Int,
    var icons : Int,
    var time : String = "3:00",
    var totalScore : Int = 5
    )

data class Questions(
    val id : Int ,
    var question : String,
    var options : Array<String>,
    var answer : String

)