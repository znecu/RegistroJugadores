package edu.ucne.RegistroJugadores.domain.jugadores.usecase

data class JugadorValidations(
    val isValid: Boolean,
    val error: String? = null
)
fun validateNombres(value: String): JugadorValidations{
    if(value.isBlank()) return JugadorValidations(false, "El nombre es requerido")
    if(value.length < 3) return JugadorValidations(false, "Minimo 3 caracteres")
    return JugadorValidations(true)
}

fun validatePartidas(value: String): JugadorValidations{
    if(value.isBlank()) return JugadorValidations(false, "El numero de partidas son requeridas")
    val number = value.toIntOrNull()?: return JugadorValidations(false, "Debe ser un numero entero")
    if(number < 0) return JugadorValidations(false,"Debe ser mayor o igual a 0")
    return JugadorValidations(true)
}