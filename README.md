# Panini Support — PoC Móvil

Prueba de concepto Android para la gestión interna de tickets de soporte de la empresa **Panini**,
operación del álbum oficial **Copa Mundial FIFA 2026**.

El sistema centraliza reportes de incidencias con proveedores, distribución, inventario y
logística que actualmente se manejan por correo electrónico, hojas de cálculo y mensajes
informales, generando pérdida de seguimiento y retrasos en la resolución.

---

## Descripción de la solución

La aplicación cubre el flujo completo de gestión de tickets de soporte interno:

- **Login simulado** con validación de credenciales y manejo de estados de carga y error.
- **Lista de tickets** ordenada automáticamente por prioridad (mayor primero), mostrando
  título, prioridad, estado, proveedor, fecha de creación y categoría de incidente.
- **Detalle del ticket** con información completa del caso.
- **Creación de tickets** con formulario de título, descripción, proveedor, categoría y prioridad.
- **Actualización de estado y prioridad** desde la pantalla de detalle.
- **UI reactiva:** al crear un ticket o modificar su prioridad, la lista se actualiza
  automáticamente sin recargar la pantalla manualmente.
- **Feature Flags** que permiten habilitar o deshabilitar funcionalidades durante pruebas
  internas con un cambio en un solo archivo.

La PoC funciona completamente con datos simulados. La capa de red (Retrofit, DTOs, contratos
YAML) está implementada y lista para conectarse a un backend real sin reorganizar el proyecto.

---

## Tecnologías utilizadas

| Tecnología | Rol en el proyecto |
|---|---|
| Jetpack Compose + Material 3 | UI declarativa, tema oscuro, componentes reutilizables |
| MVVM + Repository | Separación de lógica de negocio y presentación |
| Navigation Compose | Navegación entre pantallas con rutas tipadas |
| Kotlin Coroutines + StateFlow | Asincronismo y comunicación reactiva basada en eventos |
| Retrofit 2 + Gson + OkHttp | Capa de networking preparada para integración futura |
| DI manual (AppContainer) | Inyección de dependencias simple y transparente |
| JUnit + kotlinx-coroutines-test | Pruebas unitarias del ordenamiento y flujo reactivo |

**Versiones:** Kotlin 2.2.10 · AGP 9.2.1 · Compose BOM 2026.02.01 · Min SDK 24 · Target SDK 36

---

## Instrucciones de ejecución

**Desde Android Studio:**
1. Abrir la carpeta `Examen 2` como proyecto en Android Studio.
2. Dejar que Gradle sincronice las dependencias.
3. Ejecutar en un emulador o dispositivo físico con Android 7.0+ (API 24).

**Desde línea de comandos:**
```
./gradlew assembleDebug        # compila el APK
./gradlew testDebugUnitTest    # ejecuta las pruebas unitarias
```

**Credenciales de acceso demo:**
```
Correo:     support@panini.com
Contraseña: Panini2026
```
*Las credenciales también se muestran en la pantalla de login.*

---

## Estructura del repositorio

```
/app            Proyecto Android (Jetpack Compose + MVVM)
/contracts      Contrato API en OpenAPI 3.0 (tickets-api.yaml)
/docs           Documentación técnica para el equipo de ingeniería
/video          Enlace al video demo
README.md
```

Estructura interna del módulo `/app`:
```
core/           AppConstants, FeatureFlags, UserMessages
data/
  model/        Ticket, TicketPriority, TicketStatus, TicketCategory
  remote/       Retrofit services, DTOs, mappers, RetrofitClient, mock data
  repository/   ApiResult, TicketRepository, AuthRepository
  AppContainer, AuthSession
navigation/     AppDestinations, AppNavHost
ui/
  screens/      login/, ticketlist/, ticketdetail/, createticket/
  components/   Componentes reutilizables (AppButton, TicketCard, Chips, etc.)
  theme/        Color, Type, Theme (dark mode)
```

---

## Consideraciones técnicas para el equipo

**Arquitectura reactiva (comunicación basada en eventos)**
`TicketRepository` expone un `StateFlow<List<Ticket>>` como fuente única de verdad.
Todas las pantallas observan el mismo flujo a través de `AppContainer` (singleton).
Cualquier mutación emite automáticamente la lista actualizada. `TicketListViewModel`
la reordena por `priority.weight` descendente. Resultado: crear un ticket o cambiar
su prioridad actualiza la lista en tiempo real sin recargar nada.
Ver `docs/comunicacion-eventos.txt` para el flujo detallado.

**Feature Flags**
Centralizados en `core/FeatureFlags.kt`. Un solo archivo controla qué funcionalidades
están activas. Para esta PoC son constantes de compilación (cambiar y recompilar).
En una fase posterior se reemplaza el objeto por una implementación de remote config
(Firebase Remote Config u otro) sin modificar ningún consumidor.
Ver `docs/feature-flags.txt`.

**Conectar el backend real (fase siguiente)**
1. Actualizar `AppConstants.Api.BASE_URL` con la URL del servidor.
2. En `TicketRepository` y `AuthRepository`, reemplazar los bloques mock con llamadas
   a `RetrofitClient.ticketApiService` / `RetrofitClient.authApiService`.
3. Los DTOs, mappers y servicios Retrofit ya existen y coinciden con `/contracts/tickets-api.yaml`.
4. Ninguna pantalla ni ViewModel requiere cambios.

**Sin Room ni Firebase — por diseño**
Para una PoC sin backend real, Room agregaría entidades, DAOs, migrations y KSP sin
justificación. El `StateFlow` en memoria cumple el mismo rol reactivo. Migrar a Room
después es reemplazar la fuente del flujo en el repositorio; las pantallas no cambian.
Ver `docs/arquitectura.txt`.
