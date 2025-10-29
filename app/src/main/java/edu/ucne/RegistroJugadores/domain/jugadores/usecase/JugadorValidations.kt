package edu.ucne.RegistroJugadores.domain.jugadores.usecase

data class JugadorValidations(
    val isValid: Boolean,
    val error: String? = null
)

fun validateNombres(value: String): JugadorValidations {
    if (value.isBlank()) return JugadorValidations(false, "Este campo es obligatorio")
    if (value.length < 3) return JugadorValidations(false, "El nombre debe tener al menos 3 caracteres")
    if (value.length > 50) return JugadorValidations(false, "El nombre no puede tener más de 50 caracteres")
    return JugadorValidations(true)
}

fun validateEmail(value: String): JugadorValidations {
    if (value.isBlank()) return JugadorValidations(false, "Este campo es obligatorio")
    val emailRegex = "^[A-Za-z](.*)([@]{1})(.+)(\\.)(.+)"
    if (!value.matches(emailRegex.toRegex())) return JugadorValidations(false, "El email no es válido")
    return JugadorValidations(true)
}

//fun validatePartidas(value: String): JugadorValidations {
//    if (value.isBlank()) return JugadorValidations(false, "Este campo es obligatorio")
//    val number = value.toIntOrNull() ?: return JugadorValidations(false, "Debe ser un número entero")
//    if (number < 0) return JugadorValidations(false, "Debe ser mayor o igual a 0")
//    return JugadorValidations(true)
//}