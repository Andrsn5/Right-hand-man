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

<div style="display: flex; justify-content: center; gap: 10px;">
  <img src="https://github.com/user-attachments/assets/83b0dcfa-2683-43a2-b2d9-d1e431262cc3" width="40%" />
  <img src="https://github.com/user-attachments/assets/c301451a-928a-4e0c-844b-ad188ad7e99b" width="40%" />
  <img src="https://github.com/user-attachments/assets/5d2aea25-b58a-4a80-a010-edca36b2b916" width="40%" />
  <img src="https://github.com/user-attachments/assets/0448951a-9ec2-4e2b-89d3-634f1b5fff47" width="40%" />
</div>
