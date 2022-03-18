package com.sergiotorres.grazer.domain.model

import org.joda.time.LocalDate


data class User(
    val name: String,
    val dateOfBirth: LocalDate,
    val profileImage: String
)
