## Stack

**Язык:** Kotlin Multiplatform

**Архитектура:** Clean Architecture (Presentation → Domain → Data)

| Слой | Технологии |
|------|------------|
| **UI** | Compose Multiplatform, Material 3, Voyager Navigation, Compose ImageLoader |
| **State** | MVVM ViewModel, Kotlinx Coroutines, Flow, StateFlow |
| **DI** | Koin |
| **Network** | Ktor, Kotlinx Serialization |
| **Backend** | Firebase (Auth, Firestore) |
| **Build** | Gradle KTS, KSP |
| **Logging** | Napier |
| **Resources** | Libres |
| **Date/Time** | Kotlinx DateTime |

## Ключевые решения

- **Multiplatform** — единая кодовая база для Android, Desktop, iOS, и Web
- **Clean Architecture** — четкое разделение на Presentation, Domain, и Data слои
- **Reactive UI** — Flow и StateFlow для реактивного обновления интерфейса
- **Firebase Integration** — аутентификация и база данных Firestore
- **Koin DI** — легковесная инъекция зависимостей без генерации кода
- **Voyager Navigation** — простая и мощная навигация для Compose Multiplatform
- **MVVM Pattern** — разделение логики и UI с ViewModel
- **Type-safe Networking** — Ktor с Kotlinx Serialization для типобезопасных API запросов

## Преимущества

- **Кроссплатформенность** — один код для 4 платформ (Android, Desktop, iOS, Web)
- **Современный UI** — Compose Multiplatform с Material 3
- **Чистая архитектура** — тестируемость и поддерживаемость кода
- **Реактивность** — автоматические обновления UI при изменении данных
- **Минимум платформенного кода** — максимальный общий код в commonMain

<div style="display: flex; justify-content: center; gap: 10px;">
  <img src="https://github.com/user-attachments/assets/ca00b9e8-6058-4936-b3db-5e1b7bbed614" width="40%" />
  <img src="https://github.com/user-attachments/assets/95d0db6b-f29d-4082-8968-1a0675e2f883" width="40%" />
</div>
