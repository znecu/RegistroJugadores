package edu.ucne.RegistroJugadores.domain.logros.usecase

data class LogroValidations(
    val isValid: Boolean,
    val error: String? = null
)

fun validateTitulo(value: String): LogroValidations{
    if(value.isBlank()) return LogroValidations(false, "El titulo es requerido")
    if(value.length < 10) return LogroValidations(false, "Minimo 10 caracteres")
    return LogroValidations(true)
}
fun validateDescripcion(value: String): LogroValidations{
    if(value.isBlank()) return LogroValidations(false, "La descripcion es requerida")
    if(value.length < 20) return LogroValidations(false, "Minimo 20 caracteres")
    return LogroValidations(true)

}